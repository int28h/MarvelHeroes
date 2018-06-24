package comint28h.github.marvelheroes;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Hero> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ViewHolder(ImageView v){
            super(v);
            imageView = v;
        }
    }

    public MyAdapter(List<Hero> myDataSet){
        mDataSet = myDataSet;
    }

    //Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType){
            //create a new view
            ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_my, parent, false);
            return new ViewHolder(v);
        }

        //Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position){
            holder.imageView.setImageURI(Uri.parse(mDataSet.get(position).getImageURL()));
    }

    //Return the size of dataset
    public int getItemCount(){
        return mDataSet.size();
    }
}
