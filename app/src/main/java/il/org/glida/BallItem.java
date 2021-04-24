package il.org.glida;

public class BallItem {
    private String mBallName;
    private int mBallImage;

    public BallItem(String mBallName, int mBallImage) {
        this.mBallName = mBallName;
        this.mBallImage = mBallImage;
    }

    public String getBallName() {
        return mBallName;
    }

    public int getBallImage() {
        return mBallImage;
    }

}
