package ututor.edu.csulb.ututor;

/**
 * Created by Nishant on 2/17/2018.
 */

public class NewItem
{
    private int mImage;
    private String fname, lname, subject;
    private String status, university;
    Float rating;

    public NewItem(int image, String firstname, String lastname, String sub, String status1, String uni, Float ratingg) {
        mImage = image;
        fname = firstname;
        lname = lastname;
        subject = sub;
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
    public String getstatus() { return status;   }
    public String getuniversity() { return university; }

    public Float getrating() { return rating;   }
}
