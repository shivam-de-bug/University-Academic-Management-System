
public class Feedback<T> {
    private T feedbackData;

    public Feedback(T feedbackData) {
        this.feedbackData = feedbackData;
    }

    public T getFeedback() {
        return feedbackData;
    }
}
