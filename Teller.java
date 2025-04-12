
public class Teller {

    private int endServingTime;

    public Teller() {
        endServingTime = 0;
    }

    // Getter and Setter for serving time
    public void setEndServingTime(int servingTime) {
        endServingTime = servingTime;
    }

    public int getEndServingTime() {
        return endServingTime;
    }

    public boolean isCurrentlyFree(int currentMinute) {
        if (endServingTime <= currentMinute) {
            return true;
        } else {
            return false;
        }
    }
}
