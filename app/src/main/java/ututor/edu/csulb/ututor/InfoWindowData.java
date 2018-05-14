package ututor.edu.csulb.ututor;

/**
 * Created by aenah on 5/13/18.
 */

public class InfoWindowData {
    private float rating;
    private String name;
    private String email;
    private String status;
    private int profilePic;

    public int getProfilePic(){ return profilePic;}
    public String getStatus(){ return status;}
    public String getEmail(){ return email;}
    public String getName(){ return name;}
    public float getRating(){ return rating;}

    public void setRating(float f){ rating = f; }
    public void setName(String s){ name = s;}
    public void setEmail(String s) {email = s;}
    public void setStatus(String s){ status = s;}
    public void setProfilePic(int p){ profilePic = p;}
}
