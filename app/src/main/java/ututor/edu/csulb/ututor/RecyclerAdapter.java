package ututor.edu.csulb.ututor;

import android.content.Context;
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

import static java.sql.Types.NULL;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static ArrayList<NewItem> Data;
    private static User currentUser;
    private static Context searchContext;

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

                    // TODO - LAAAAAANCE: FETCH ME THE USER'S EMAIL
                    // the otherUser's email is found by : Data.get(position).getemail()
                    // DUMMY USER - DELETE AFTER QUEREY IS DONE :)
                    User otherUser = new User();
                    otherUser.setFirstName("Herp");
                    otherUser.setLastName("Derp");
                    otherUser.setEmail("herpderp@gmail.com");
                    otherUser.setUniversity("CSULB");
                    otherUser.setRating(3f);
                    otherUser.setDescription("My name is HerpDerp! I am here to herp your derp.");
                    otherUser.setNumProfilePic(2);
                    // delete up to here ^

                    Intent i = new Intent(searchContext, GenericProfile.class);
                    i.putExtra("currentUser", currentUser);
                    i.putExtra("otherUser", otherUser);
                    searchContext.startActivity(i);

                }
            });
        }
    } //end of class ViewHolder

    public RecyclerAdapter(ArrayList<NewItem> DataList, User cUser, Context c) {
        Data = DataList;
        currentUser = cUser;
        searchContext = c;
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

        int picCode = currentItem.getImage();

        if( picCode == 0 ){ //blue
            holder.itemImage.setImageResource(R.drawable.ututorlogo);
        } else if( picCode == 1 ){ // red
            holder.itemImage.setImageResource( R.drawable.tutorhead_red);
        } else if(picCode == 2) // green
        {
            holder.itemImage.setImageResource( R.drawable.tutorhead_green);
        } else if(picCode == 3) // yellow
        {
            holder.itemImage.setImageResource( R.drawable.tutorhead_yellow);
        } else if( picCode == 4)// purple
        {
            holder.itemImage.setImageResource( R.drawable.tutorhead_purple);
        }

        //holder.itemImage.setImageResource(R.drawable.ututorlogo);

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
