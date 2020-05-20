import java.util.Comparator;

public class burstTimeComparator implements Comparator<process> {
    @Override
    public int compare(process p1, process p2) {
        return (p1.calculate_remaining() - p2.calculate_remaining());
    }
}
