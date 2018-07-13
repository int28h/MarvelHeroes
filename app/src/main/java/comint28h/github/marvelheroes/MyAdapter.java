package comint28h.github.marvelheroes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import comint28h.github.marvelheroes.hero.Hero;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Hero> mDataSet = new LinkedList<>();
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView heroName;
        public ImageView imageView;

        public ViewHolder(View v){
            super(v);
            heroName = v.findViewById(R.id.heroName);
            imageView = v.findViewById(R.id.portretsList);
        }
    }

    public MyAdapter(List<Hero> myDataSet, Context context){
        mDataSet = myDataSet;
        this.context = context;
    }

    //Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType){
            //create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_my, parent, false);
            return new ViewHolder(v);
        }

        //Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position){
            Hero hero = mDataSet.get(position);
            holder.heroName.setText(hero.getName());
            final String thumbnail = hero.getThumbnail().getPath().replace("http", "https") + "/standard_xlarge.jpg";
            Picasso.with(context).load(thumbnail).into(holder.imageView);
    }

    //Return the size of dataset
    public int getItemCount(){
        return mDataSet.size();
    }

    public void myDataSetChanges(List<Hero> myDataSet){
        mDataSet.addAll(myDataSet);
        notifyDataSetChanged();
    }
}
