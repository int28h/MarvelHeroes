package comint28h.github.marvelheroes;

import java.util.List;

import comint28h.github.marvelheroes.hero.APIResponse;
import comint28h.github.marvelheroes.hero.Hero;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
	String API_KEY = BuildConfig.apikey;

    @GET("v1/public/characters")
    Call<APIResponse> getHeroesList(
            @Query("ts") String ts,
            @Query("apikey") String api,
            @Query("hash") String hash
    );
}