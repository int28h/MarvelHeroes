package comint28h.github.marvelheroes.network;

import comint28h.github.marvelheroes.BuildConfig;
import comint28h.github.marvelheroes.network.APIResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
	String API_KEY = BuildConfig.apikey;

    @GET("v1/public/characters")
    Call<APIResponse> getHeroesList(
            @Query("offset") Integer offset,
            @Query("limit") Integer limit,
            @Query("ts") String ts,
            @Query("apikey") String api,
            @Query("hash") String hash
    );
}