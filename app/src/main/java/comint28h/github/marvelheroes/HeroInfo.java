package comint28h.github.marvelheroes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class HeroInfo extends AppCompatActivity {
    ImageView thumbnail;
    TextView name;
    TextView description;
    TextView comics;
    TextView series;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_info);
        
        loadHeroInfo();
    }

    private void loadHeroInfo() {
        Bundle bundle = getIntent().getExtras();

        this.name = findViewById(R.id.name);
        this.name.setText(bundle.getString("name"));

        this.thumbnail = findViewById(R.id.portrait);
        Picasso.with(getApplicationContext())
                .load(bundle.getString("thumbnail"))
                .transform(new RoundedCornersTransformation(10, 10))
                .into(this.thumbnail);

        this.description = findViewById(R.id.description);
        this.description.setText(bundle.getString("description"));

        this.comics = findViewById(R.id.comics);
        this.comics.setText(bundle.getString("comics"));

        this.series = findViewById(R.id.series);
        this.series.setText(bundle.getString("series"));
    }
}
