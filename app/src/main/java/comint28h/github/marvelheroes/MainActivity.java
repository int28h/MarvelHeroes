package comint28h.github.marvelheroes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String[] myDataSet = {"Зачем мы живем?", "Я не доживу до пенсии",
            "Ебись все конем", "Я умру в одиночестве"};

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        //a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //specify an adapter
        mAdapter = new MyAdapter(myDataSet);
        mRecyclerView.setAdapter(mAdapter);
    }
}
