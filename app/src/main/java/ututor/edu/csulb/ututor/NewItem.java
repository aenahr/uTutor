package ututor.edu.csulb.ututor;

/**
 * Created by Nishant on 2/17/2018.
 */

public class NewItem
{
    private int mImage;
    private String mText1;
    private String mText2;
    private String mText3;
    public NewItem(int image, String text1, String text2) {
        mImage = image;
        mText1 = text1;
        mText2 = text2;
    }
    public int getImage() {
        return mImage;
    }

    public String getfirstname() {
        return mText1;
    }
    public String getlastname() {
        return mText2;
    }
    public String getsubject() {
        return mText3;
    }
}
