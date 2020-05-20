import java.util.*;

public class process {
    public int arrival_time;
    public int burst_time;
    public int process_id;
    public List<Double>  quantam_time;
    public int done;
    public boolean complete;
    public int priority;
    public int wait_time;
    public int turnaround;

    process(int process_id, int arrival_time, int burst_time, double quantam_time, int priority) {
        this.process_id = process_id;
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
        this.quantam_time = new ArrayList<>();
        this.quantam_time.add(quantam_time);
        this.priority = priority;
        done = 0;
        complete = false;
        wait_time = 0;
        turnaround = 0;
    }

    process(process p) {
        this.quantam_time = new ArrayList<>();
        this.equals(p);
    }

    process() {
        this.quantam_time = new ArrayList<>();
        done = 0;
        complete = false;
        wait_time = 0;
        turnaround = 0;
    }

    public boolean isEqual(process p) {
        return (this.arrival_time == p.arrival_time && this.burst_time == p.burst_time && this.process_id == p.process_id
                && this.quantam_time.equals(p.quantam_time) && this.done == p.done && this.complete == p.complete
                && this.priority == p.priority && this.wait_time == p.wait_time && this.turnaround == p.turnaround);
    }

    public int calculate_remaining() {
        return burst_time-done;
    }

    public boolean isComplete() {
        return done == burst_time;
    }

    public void equals(process p) {
        this.arrival_time = p.arrival_time;
        this.burst_time = p.burst_time;
        this.process_id = p.process_id;
        for (int i = 0; i < p.quantam_time.size(); i++)
            this.quantam_time.add(p.quantam_time.get(i));
        this.done = p.done;
        this.complete = p.complete;
        this.priority = p.priority;
        this.wait_time = p.wait_time;
        this.turnaround = p.turnaround;
    }

}
