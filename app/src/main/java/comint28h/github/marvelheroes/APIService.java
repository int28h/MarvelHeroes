package comint28h.github.marvelheroes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("v1/public/characters")
    Call<List<Hero>> getHeroesList();
}
