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

    public NewItem(int image, String firstname, String lastname, String ema, String status1,String subj, String uni, Float rat) {
        mImage = image;
        fname = firstname;
        lname = lastname;
        email = ema;
        status = status1;
        subject = subj;
        university = uni;
        rating = rat;
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


//    public int compareRating(NewItem jc1, NewItem jc2) {
//        return (jc2.getrating() < jc1.getrating() ? -1 :
//                (jc2.getrating() == jc1.getrating() ? 0 : 1));
//    }
//
//    public int compareUni(NewItem jc1, NewItem jc2) {
//        return (int) (jc1.getuniversity().compareTo(jc2.getuniversity()));
//    }

}
