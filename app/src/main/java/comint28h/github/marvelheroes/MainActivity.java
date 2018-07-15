package comint28h.github.marvelheroes;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import comint28h.github.marvelheroes.adapter.HeroAdapter;
import comint28h.github.marvelheroes.network.APIResponse;
import comint28h.github.marvelheroes.network.APIService;
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

    private APIService apiService;

    private RecyclerView recyclerView;
    private HeroAdapter heroAdapter;
    private GridLayoutManager mLayoutManager;

    private int visible, previous, count;
    private boolean loading = true;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.my_recycler_view);

        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                heroAdapter.clear();
                loadHeroes(0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        heroAdapter = new HeroAdapter(MainActivity.this);
        recyclerView.setAdapter(heroAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

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
        loadHeroes(heroAdapter.getItemCount());
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
            heroAdapter.addAll(response.body().getData().getResults());
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
                    loadHeroes(heroAdapter.getItemCount());
                }
            }
        }
    };
}
