package ututor.edu.csulb.ututor;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static ArrayList<NewItem> Data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public TextView itemfirstName, itemlastName;
        public TextView itemStatus, itemuniversity;
        public RatingBar itemrating;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemfirstName = (TextView) itemView.findViewById(R.id.item_firstname);
            itemlastName = (TextView) itemView.findViewById(R.id.item_lastname);
            itemStatus = (TextView) itemView.findViewById(R.id.WalkinStatus);
            itemuniversity = (TextView) itemView.findViewById(R.id.university);
            itemrating = (RatingBar) itemView.findViewById(R.id.searchrating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    //code for get information for that item

                    Snackbar.make(v, "Hi " + Data.get(position).getfirstname() + "  " + Data.get(position).getlastname(),
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                            //opens a snackbar for individual click
                            //so redirection to profile will happen here
                }
            });
        }
    } //end of class ViewHolder

    public RecyclerAdapter(ArrayList<NewItem> DataList) {
        Data = DataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        NewItem currentItem = Data.get(i);
        holder.itemImage.setImageResource(currentItem.getImage());

        holder.itemfirstName.setText(currentItem.getfirstname());
        holder.itemlastName.setText(currentItem.getlastname());
        holder.itemStatus.setText(currentItem.getstatus());
        holder.itemuniversity.setText(currentItem.getuniversity());

        holder.itemrating.setRating((float) currentItem.getrating());
    }

    @Override
    public int getItemCount()
    {
        return Data.size();
    }

    public void filterList(ArrayList<NewItem> filteredList)
    {
        Data = filteredList;
        notifyDataSetChanged();
    }
}
