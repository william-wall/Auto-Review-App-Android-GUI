package ie.williamwall.autoreview;

/**
 * Created by william on 05/02/2018.
 */

public class Reviews {

    int avatar;
    String reviewTitle;
    String reviewDesc;

    public Reviews() {
    }

    public Reviews(int avatar, String reviewTitle, String reviewDesc) {
        this.avatar = avatar;
        this.reviewTitle = reviewTitle;
        this.reviewDesc = reviewDesc;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }
}
