package ututor.edu.csulb.ututor;

import java.util.Comparator;

/**
 * Created by Nishant on 2/17/2018.
 */

public class NewItem
{
    private int mImage;
    private String fname, lname;
    private String status,subject, university;
    private String email;
    private Float rating;
    private double lat, lng;
    boolean isWalkIn;

    public NewItem(int image, String firstname, String lastname, String ema, String status1,String subj, String uni, Float rat,double lattitude, double longitude, boolean walkin) {
        mImage = image;
        fname = firstname;
        lname = lastname;
        email = ema;
        status = status1;
        subject = subj;
        university = uni;
        rating = rat;
        lat = lattitude;
        lng = longitude;
        isWalkIn = walkin;
    }

    public int getImage() {
        return mImage;
    }

    public String getfirstname() {
        return fname;
    }
    public String getlastname() {
        return lname;
    }
    public String getemail() { return email;   }
    public String getstatus() { return status;   }
    public String getSubject() { return subject; }
    public String getuniversity() { return university; }
    public Float getrating() { return rating;   }
    public static Comparator<NewItem> UniComparator = new Comparator<NewItem>() {
        @Override
        public int compare(NewItem t0, NewItem t1) {
            return t0.getuniversity().compareToIgnoreCase(t1.getuniversity());
        }
    };
    public static Comparator<NewItem> RateComparator = new Comparator<NewItem>() {
    @Override
        public int compare(NewItem t0, NewItem t1) {
            return t1.getrating().compareTo(t0.getrating());
        }
    };


}
