package ututor.edu.csulb.ututor;

/**
 * Created by Nishant on 2/17/2018.
 */

public class newItem
{
    private int mImage;
    private String mText1;
    private String mText2;
    public newItem(int image, String text1,String text2) {
        mImage = image;
        mText1 = text1;
        mText2 = text2;
    }
    public int getImage() {
        return mImage;
    }
    public String getText1() {
        return mText1;
    }
    public String getText2() {
        return mText2;
    }
}
