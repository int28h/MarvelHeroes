package comint28h.github.marvelheroes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;

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
    private APIService apiService;

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private int visible, previous, count;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(heroesList, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(onScrollListener);

        //взаимодействие с удаленным сервером + логирование
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
        loadHeroes(mAdapter.getItemCount());
    }

    //загрузка списка из n следующих героев
    private void loadHeroes(int offset){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        String forHash = (ts + APIService.API_KEY + PUBLIC_API_KEY);
        String hash = new MD5Calc(forHash).getHash();
        Call<APIResponse> call = apiService.getHeroesList(offset, 20, ts, "c91320537ce6d3be8c6ac1b579d52e3e", hash);
        call.enqueue(callbackGetHeroesList);
    }

    //парсинг отклика
    private Callback<APIResponse> callbackGetHeroesList = new Callback<APIResponse>() {
        @Override
        public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
            heroesList.clear();
            mAdapter.notifyDataSetChanged();
            heroesList.addAll(response.body().getData().getResults());
            mAdapter.addHeroesToHeroesList(heroesList);
            loading = true;
        }

        @Override
        public void onFailure(Call<APIResponse> call, Throwable t) {

        }
    };

    //пагинация
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if(dy > 0) {
                visible = mLayoutManager.getChildCount();
                previous = mLayoutManager.findFirstVisibleItemPosition();
                count = mLayoutManager.getItemCount();

                if (loading && (visible + previous) >= count) {
                    loading = false;
                    loadHeroes(mAdapter.getItemCount());
                }
            }
        }
    };
}
