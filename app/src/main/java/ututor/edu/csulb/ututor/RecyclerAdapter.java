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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
                    JSONObject response = null;
                    try {
                        response = new ServerRequester().execute("fetchUser.php", "whatever",
                                "email", Data.get(position).getemail()
                                //"subject", searchsubj.getText().toString(),
                                //"university", searchuni.getText().toString(),
                                //"rating",  Float.toString(searchrating.getRating())
                        ).get();
                        if (response == null) {//Something went horribly wrong, JSON failed to be formed meaning something happened in the server requester
                        } else if (!response.isNull("error")) {//Some incorrect information was sent, but the server and requester still processed it
                            //TODO Handle Server Errors
                            switch (response.get("error").toString()) {
                                case "-1": //Email Password Combo not in the Database
                                    break;
                                case "-2":  //Select Query failed due to something dumb
                                    // Print out response.get("errormessage"), it'll have the mysql error with it

                                    break;
                                case "-3": //Update Query Failed Due to New Email is already associated with another account
                                    break;
                                case "-4":  //Update Query Failed Due to Something Else Dumb that I haven't handled yet,
                                    // Print out response.get("errormessage"), it'll have the mysql error with it
                                    break;
                                default:    //Some Error Code was printed from the server that isn't handled above

                                    break;
                            }
                        } else { //Everything Went Well
                            User otherUser = new User();
                            otherUser.setFirstName(response.get("firstName").toString());
                            otherUser.setLastName(response.get("lastName").toString());
                            otherUser.setEmail(response.get("email").toString());
                            otherUser.setUniversity(response.get("university").toString());
                            otherUser.setRating(Float.parseFloat(response.get("averageRating").toString()));
                            otherUser.setDescription(response.get("userDescription").toString());
                            otherUser.setNumProfilePic(Integer.parseInt(response.get("profilePic").toString()));
                            // delete up to here ^

                            Intent i = new Intent(searchContext, GenericProfile.class);
                            i.putExtra("currentUser", currentUser);
                            i.putExtra("otherUser", otherUser);
                            searchContext.startActivity(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
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
