package ututor.edu.csulb.ututor;

/**
 * Created by Henry Tran on 3/5/2018.
 */
public class Profile_review_detail {

    private float ratingStar;
    private String name;
    private String user_feedback;

    public Profile_review_detail(int ratingStar, String name, String feedback) {
        this.ratingStar = ratingStar;
        this.name = name;
        this.user_feedback = feedback;
    }

    public float getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(float ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return user_feedback;
    }

    public void setFeedback(String feedback) {
        this.user_feedback = feedback;
    }

}