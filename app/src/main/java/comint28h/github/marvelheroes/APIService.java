package comint28h.github.marvelheroes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
	String API_KEY = BuildConfig.apikey;

    @GET("v1/public/characters")
    Call<List<Hero>> getHeroesList(
            @Query("ts") String ts,
            @Query("apikey") String api,
            @Query("hash") String hash
    );
}