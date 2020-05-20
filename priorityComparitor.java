import java.util.Comparator;

public class priorityComparitor implements Comparator<process> {
    @Override
    public int compare(process p1, process p2) {
        return (p1.priority - p2.priority);
    }
}
