package comint28h.github.marvelheroes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import comint28h.github.marvelheroes.hero.Hero;
import comint28h.github.marvelheroes.hero.Items;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Hero> heroesList;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView heroName;
        public ImageView heroPortrait;

        public ViewHolder(View v){
            super(v);
            heroName = v.findViewById(R.id.heroName);
            heroPortrait = v.findViewById(R.id.heroPortrait);
        }
    }

    public MyAdapter(List<Hero> myDataSet, Context context){
        this.heroesList = new LinkedList<>(myDataSet);
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
        public void onBindViewHolder(ViewHolder holder, final int position){
            Hero hero = heroesList.get(position);
            holder.heroName.setText(hero.getName());
            final String thumbnail = hero.getThumbnail().getPath() + "/standard_xlarge.jpg";
            Picasso.with(context).load(thumbnail)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.heroPortrait);

            holder.heroPortrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HeroInfo.class);
                    intent.putExtra("name", heroesList.get(position).getName());
                    intent.putExtra("description", heroesList.get(position).getDescription());

                    String bigThumbnail = thumbnail.replace("/standard_xlarge.jpg", "/portrait_uncanny.jpg");
                    intent.putExtra("thumbnail", bigThumbnail);

                    String comics = buildList(heroesList.get(position).getComics().getItems());

                    String series = buildList( heroesList.get(position).getSeries().getItems());

                    intent.putExtra("comics", comics);
                    intent.putExtra("series", series);

                    context.startActivity(intent);
                }
            });
    }

    //Return the size of dataset
    public int getItemCount(){
        return heroesList.size();
    }

    public void addHeroesToHeroesList(List<Hero> myDataSet){
        heroesList.addAll(myDataSet);
        notifyDataSetChanged();
    }

    private String buildList(List <Items> items){
        StringBuilder result = new StringBuilder();
        for(Items item : items){
            result.append(item.getName() + "\n");
        }
        return result.toString();
    }
}
