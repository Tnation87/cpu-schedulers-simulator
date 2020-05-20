import java.util.Comparator;

public class arrivalTimeComparitor implements Comparator<process> {
    @Override
    public int compare(process p1, process p2) {
        return (p1.arrival_time - p2.arrival_time);
    }
}
