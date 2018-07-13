package comint28h.github.marvelheroes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import comint28h.github.marvelheroes.hero.APIResponse;
import comint28h.github.marvelheroes.hero.Hero;
import comint28h.github.marvelheroes.utils.MD5Calc;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://gateway.marvel.com/";
    public static final String PUBLIC_API_KEY = "c91320537ce6d3be8c6ac1b579d52e3e";

    private List<Hero> heroesList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //подсчет хеша для авторизации
        String forHash = ("1" + APIService.API_KEY + PUBLIC_API_KEY);
        String hash = new MD5Calc(forHash).getHash();

        //взаимодействие с удаленным сервером + логирование
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);

        //адаптер
        mAdapter = new MyAdapter(heroesList, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);

        //обращение к серверу
        Call<APIResponse> call = apiService.getHeroesList("1", "c91320537ce6d3be8c6ac1b579d52e3e", hash);
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                heroesList.clear();
                mAdapter.notifyDataSetChanged();
                heroesList.addAll(response.body().getData().getResults());
                mAdapter.addHeroesToHeroesList(heroesList);
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {

            }
        });
    }
}
