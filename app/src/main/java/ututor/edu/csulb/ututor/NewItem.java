package ututor.edu.csulb.ututor;

import java.util.Comparator;

/**
 * Created by Nishant on 2/17/2018.
 */

public class NewItem
{
    private int mImage;
    private String fname, lname;
    private String status, university;
    private String email;
    private Float rating;

    public NewItem(int image, String firstname, String lastname, String email1, String status1, String uni, Float ratingg) {
        mImage = image;
        fname = firstname;
        lname = lastname;
        email = email1;
        status = status1;
        university = uni;
        rating = ratingg;
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
    public String getuniversity() { return university; }

    public Float getrating() { return rating;   }


//    public int compareRating(NewItem jc1, NewItem jc2) {
//        return (jc2.getrating() < jc1.getrating() ? -1 :
//                (jc2.getrating() == jc1.getrating() ? 0 : 1));
//    }
//
//    public int compareUni(NewItem jc1, NewItem jc2) {
//        return (int) (jc1.getuniversity().compareTo(jc2.getuniversity()));
//    }

}
