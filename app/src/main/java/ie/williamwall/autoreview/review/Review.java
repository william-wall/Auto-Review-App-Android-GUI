package ie.williamwall.autoreview.review;

// Designed and Developed @ William Wall
// Email @ william@williamwall.ie
// GitHub @ github.com/william-wall

public class Review {

    int avatar;
    String reviewTitle;
    String reviewDesc;

    public Review() {
    }

    public Review(int avatar, String reviewTitle, String reviewDesc) {
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
