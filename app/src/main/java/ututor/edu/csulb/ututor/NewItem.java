package ututor.edu.csulb.ututor;

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
}
