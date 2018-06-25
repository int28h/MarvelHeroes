package comint28h.github.marvelheroes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("v1/public/characters)
    Call<List<Hero>> getHeroesList(@Query ("ts") "1", @Query ("apikey") "c91320537ce6d3be8c6ac1b579d52e3e");
}
