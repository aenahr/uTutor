package ututor.edu.csulb.ututor;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<NewItem> Data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage;
        public TextView itemfirstName, itemlastName;
        public TextView itemSubject;
        public TextView itemWalkin;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemfirstName = (TextView) itemView.findViewById(R.id.item_firstname);
            itemlastName = (TextView) itemView.findViewById(R.id.item_lastname);
            itemSubject = (TextView) itemView.findViewById(R.id.item_subject);
            itemWalkin = (TextView) itemView.findViewById(R.id.WalkinStaus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

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
        holder.itemSubject.setText(currentItem.getsubject());
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
