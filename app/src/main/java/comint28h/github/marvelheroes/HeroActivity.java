package comint28h.github.marvelheroes;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class HeroActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    ImageView thumbnail;
    TextView name;
    TextView description;
    TextView comics;
    TextView series;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_activity);

        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh_info);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearViewsValues();
                loadHeroInfo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        loadViews();
        loadHeroInfo();
    }

    private void loadViews(){
        this.name = findViewById(R.id.name);
        this.thumbnail = findViewById(R.id.portrait);
        this.description = findViewById(R.id.description);
        this.comics = findViewById(R.id.comics);
        this.series = findViewById(R.id.series);
    }

    private void clearViewsValues(){
        this.name.setText(null);
        this.thumbnail.setImageURI(null);
        this.description.setText(null);
        this.comics.setText(null);
        this.series.setText(null);
    }

    private void loadHeroInfo() {
        Bundle bundle = getIntent().getExtras();

        this.name.setText(bundle.getString("name"));
        Picasso.with(getApplicationContext())
                .load(bundle.getString("thumbnail"))
                .transform(new RoundedCornersTransformation(10, 10))
                .into(this.thumbnail);
        this.description.setText(bundle.getString("description"));
        this.comics.setText(bundle.getString("comics"));
        this.series.setText(bundle.getString("series"));
    }
}
