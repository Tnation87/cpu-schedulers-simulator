import java.util.*;

/*
team members:
name: Toka Magdy Ragab -> ID: 20166052
name: Nada Gamal -> ID: 20166042
name: Esraa Saeed -> ID: 20166055
name: Omnia Moustafa -> ID: 20166005
name: Shrouk Moustafa -> ID: 20175008
name: Samah Hassan -> ID: 20166016
*/
public class scheduler {

    public void psjf(process [] arr,int context_switch, Queue<process> order) {
        arrivalTimeComparitor ac = new arrivalTimeComparitor();
        Arrays.sort(arr,ac);
        int min_arrival = arr[0].arrival_time, j = 0;
        List<process> available_processes = new ArrayList<>(),Final = new ArrayList<>();
        int i = min_arrival;
        burstTimeComparator bc = new burstTimeComparator();
        while (Final.size() < arr.length) {
            while (j < arr.length && arr[j].arrival_time <= i) {
                available_processes.add(arr[j]);
                j++;
            }
            if (available_processes.isEmpty()) {
                i++;
                continue;
            }
            Collections.sort(available_processes,bc);
            order.add(available_processes.get(0));
            while (available_processes.get(0).done < available_processes.get(0).burst_time) {
                available_processes.get(0).done++;
                i++;
                while (j < arr.length && arr[j].arrival_time <= i) {
                    available_processes.add(arr[j]);
                    j++;
                }
                process current_process = new process(available_processes.get(0));
                Collections.sort(available_processes,bc);
                if (!current_process.isEqual(available_processes.get(0))) {
                    i += context_switch;
                    order.add(available_processes.get(0));
                }

            }
            available_processes.get(0).complete = true;
            available_processes.get(0).turnaround = i - available_processes.get(0).arrival_time;
            available_processes.get(0).wait_time = available_processes.get(0).turnaround - available_processes.get(0).burst_time;
            Final.add(available_processes.get(0));
            available_processes.remove(0);
            i += context_switch;
        }
    }




    public void RR(process [] arr,int context_switch, Queue<process> order, int quantam) {
        arrivalTimeComparitor ac = new arrivalTimeComparitor();
        Arrays.sort(arr,ac);
        int min_arrival = arr[0].arrival_time, j = 0;
        Queue<process> ready = new LinkedList<>();
        List Final = new ArrayList<>();
        int i = min_arrival;
        while (Final.size() < arr.length) {
            while (j < arr.length && arr[j].arrival_time <= i) {
                ready.add(arr[j]);
                j++;
            }
            if (ready.isEmpty()) {
                i++;
                continue;
            }
            order.add(ready.peek());
            for (int k = 0; k < quantam && ready.peek().done < ready.peek().burst_time; k++) {
                ready.peek().done++;
                i++;
            }
            if (ready.peek().done == ready.peek().burst_time) {
                ready.peek().complete = true;
                ready.peek().turnaround = i - ready.peek().arrival_time;
                ready.peek().wait_time = ready.peek().turnaround - ready.peek().burst_time;
                Final.add(ready.poll());
            }
            else {
                while (j < arr.length && arr[j].arrival_time <= i) {
                    ready.add(arr[j]);
                    j++;
                }
                ready.add(ready.peek());
                ready.poll();
            }
            i += context_switch;
        }
    }




    public void pp(process [] arr, Queue<process> order) {
        arrivalTimeComparitor ac = new arrivalTimeComparitor();
        Arrays.sort(arr,ac);
        int min_arrival = arr[0].arrival_time, j = 0;
        List<process> available_processes = new ArrayList<>(),Final = new ArrayList<>();
        int i = min_arrival;
        priorityComparitor pc = new priorityComparitor();
        while (Final.size() < arr.length) {
            while (j < arr.length && arr[j].arrival_time <= i) {
                available_processes.add(arr[j]);
                j++;
            }
            if (available_processes.isEmpty()) {
                i++;
                continue;
            }
            Collections.sort(available_processes,pc);
            order.add(available_processes.get(0));
            while (available_processes.get(0).done < available_processes.get(0).burst_time) {
                available_processes.get(0).done++;
                i++;
                while (j < arr.length && arr[j].arrival_time <= i) {
                    available_processes.add(arr[j]);
                    j++;
                }
                process current_process = new process(available_processes.get(0));
                Collections.sort(available_processes,pc);
                if (!current_process.isEqual(available_processes.get(0))) {
                    order.add(available_processes.get(0));
                    for (int k = 1; k < available_processes.size(); k++)
                        if (available_processes.get(k).priority >= 0)
                            available_processes.get(k).priority--;
                }
            }
            available_processes.get(0).complete = true;
            available_processes.get(0).turnaround = i - available_processes.get(0).arrival_time;
            available_processes.get(0).wait_time = available_processes.get(0).turnaround - available_processes.get(0).burst_time;
            Final.add(available_processes.get(0));
            available_processes.remove(0);
        }
    }




    public void AG(process [] arr, Queue<process> order) {
        arrivalTimeComparitor ac = new arrivalTimeComparitor();
        Arrays.sort(arr,ac);
        int min_arrival = arr[0].arrival_time, j = 0;
        List<process> available_processes = new ArrayList<>(),Final = new ArrayList<>();
        int i = min_arrival;
        while (Final.size() < arr.length) {
            while (j < arr.length && arr[j].arrival_time <= i) {
                available_processes.add(arr[j]);
                j++;
            }
            //Collections.sort(available_processes,ac);
            order.add(available_processes.get(0));
            double q = available_processes.get(0).quantam_time.get(0), quarter = Math.ceil(q/4);
            for (int k = 0; k < quarter && available_processes.get(0).done < available_processes.get(0).burst_time; k++) {
                available_processes.get(0).done++;
                q--;
                i++;
            }
            while (j < arr.length && arr[j].arrival_time <= i) {
                available_processes.add(arr[j]);
                j++;
            }
            if (available_processes.get(0).done == available_processes.get(0).burst_time) {
                available_processes.get(0).complete = true;
                available_processes.get(0).turnaround = i - available_processes.get(0).arrival_time;
                available_processes.get(0).wait_time = available_processes.get(0).turnaround - available_processes.get(0).burst_time;
                available_processes.get(0).quantam_time.add(0,0.0);
                Final.add(available_processes.get(0));
                available_processes.remove(0);
                Collections.sort(available_processes,ac);
            }
            else {
                priorityComparitor pc = new priorityComparitor();
                boolean change_process = false;
                for (int k = 1; k < available_processes.size(); k++) {
                    if (available_processes.get(k).priority < available_processes.get(0).priority) {
                        change_process = true;
                        available_processes.get(0).quantam_time.add(0, available_processes.get(0).quantam_time.get(0) + Math.ceil(q/2));
                        available_processes.add(available_processes.get(0));
                        available_processes.remove(0);
                        break;
                    }
                }
                Collections.sort(available_processes,pc);
                if (change_process)
                    continue;
                for (int k = 0; k < quarter && available_processes.get(0).done < available_processes.get(0).burst_time; k++) {
                    available_processes.get(0).done++;
                    q--;
                    i++;
                }
                while (j < arr.length && arr[j].arrival_time <= i) {
                    available_processes.add(arr[j]);
                    j++;
                }
                if (available_processes.get(0).done == available_processes.get(0).burst_time) {
                    available_processes.get(0).complete = true;
                    available_processes.get(0).turnaround = i - available_processes.get(0).arrival_time;
                    available_processes.get(0).wait_time = available_processes.get(0).turnaround - available_processes.get(0).burst_time;
                    available_processes.get(0).quantam_time.add(0,0.0);
                    Final.add(available_processes.get(0));
                    available_processes.remove(0);
                    Collections.sort(available_processes,ac);
                }
                else {
                    burstTimeComparator bc = new burstTimeComparator();
                    for (int k = 1; k < available_processes.size(); k++) {
                        if (available_processes.get(k).calculate_remaining() < available_processes.get(0).calculate_remaining()) {
                            change_process = true;
                            available_processes.get(0).quantam_time.add(0, available_processes.get(0).quantam_time.get(0) + q);
                            available_processes.add(available_processes.get(0));
                            available_processes.remove(0);
                            break;
                        }
                    }
                    Collections.sort(available_processes,bc);
                    if (change_process)
                        continue;
                    int counter = 0;
                    while (available_processes.get(0).done < available_processes.get(0).burst_time && counter < q) {
                        available_processes.get(0).done++;
                        i++;
                        while (j < arr.length && arr[j].arrival_time <= i) {
                            available_processes.add(arr[j]);
                            j++;
                        }
                        for (int k = 1; k < available_processes.size(); k++) {
                            if (available_processes.get(k).calculate_remaining() < available_processes.get(0).calculate_remaining()) {
                                change_process = true;
                                available_processes.get(0).quantam_time.add(0, available_processes.get(0).quantam_time.get(0) + q);
                                available_processes.add(available_processes.get(0));
                                available_processes.remove(0);
                                break;
                            }
                        }
                        if (change_process)
                            break;
                    }
                    Collections.sort(available_processes,bc);
                    if (change_process)
                        continue;
                    if (available_processes.get(0).done == available_processes.get(0).burst_time) {
                        available_processes.get(0).complete = true;
                        available_processes.get(0).turnaround = i - available_processes.get(0).arrival_time;
                        available_processes.get(0).wait_time = available_processes.get(0).turnaround - available_processes.get(0).burst_time;
                        available_processes.get(0).quantam_time.add(0,0.0);
                        Final.add(available_processes.get(0));
                        available_processes.remove(0);
                        Collections.sort(available_processes,ac);
                    }
                    else {
                        available_processes.get(0).quantam_time.add(0, available_processes.get(0).quantam_time.get(0) + 2);
                        available_processes.add(available_processes.get(0));
                        available_processes.remove(0);
                        Collections.sort(available_processes,ac);
                    }
                }
            }

        }
    }




    public static void main(String [] args) {

        Scanner sc = new Scanner(System.in);
        int burst_time, arrival_time , size, name, priority, choice, context, q;
        double quantam, sum = 0.0;
        Queue<process> order = new LinkedList<>();
        scheduler current = new scheduler();

        System.out.println("Enter Number Of Processes");
        size = sc.nextInt();
        process p[] = new process[size], temp[] = new process[size];

        for(int i = 0; i < size; i++) {
            System.out.println("Enter Process Name, Burst Time, Arrival Time, quantam time and priority");

            name = sc.nextInt();
            burst_time= sc.nextInt();
            arrival_time = sc.nextInt();
            quantam = sc.nextInt();
            priority = sc.nextInt();

            p[i] = new process(name,arrival_time,burst_time,quantam,priority);
            temp[i] = new process(p[i]);

        }

        while (true) {
            for(int i = 0; i < size; i++)
                p[i].equals(temp[i]);
            System.out.println("1/preemptive Shortest Job First");
            System.out.println("2/Round Robin with context switching");
            System.out.println("3/preemptive Priority Scheduling (with the solving of starvation problem)");
            System.out.println("4/AG Scheduling");
            System.out.println("5/exit");
            choice = sc.nextInt();
            if (choice == 5)
                break;
            if (choice == 1) {
                System.out.println("enter the context switch");
                context = sc.nextInt();
                current.psjf(p,context,order);
            }
            else if (choice == 2) {
                System.out.println("enter the context switch");
                context = sc.nextInt();
                System.out.println("enter the quantam time");
                q = sc.nextInt();
                current.RR(p,context,order,q);
            }
            else if (choice == 3) {
                current.pp(p,order);
            }
            else if (choice == 4) {
                current.AG(p,order);
                System.out.println("history update of quantum time for each process");
                for (int i = 0; i< p.length; i++) {
                    System.out.println("process " + p[i].process_id + " :");
                    int s = p[i].quantam_time.size();
                    for (int j = s-1; j >= 0; j--)
                        System.out.println(p[i].quantam_time.get(j));
                }
                System.out.println("-----------------------------------------------");
            }
            else continue;
            System.out.println("the order in which the processes executed:");
            while (!order.isEmpty())
                System.out.println(order.poll().process_id);
            System.out.println("-----------------------------------------------");
            System.out.println("the waiting time for each process:");
            for (int i = 0; i < p.length; i++) {
                System.out.println(p[i].process_id + ": " + p[i].wait_time);
                sum += p[i].wait_time;
            }
            System.out.println("average waiting time: " + sum/p.length);
            System.out.println("-----------------------------------------------");
            sum = 0;
            System.out.println("the turnaround time for each process:");
            for (int i = 0; i < p.length; i++) {
                System.out.println(p[i].process_id + ": " + p[i].turnaround);
                sum += p[i].turnaround;
            }
            System.out.println("average turnaround time: " + sum/p.length);
            System.out.println("-----------------------------------------------");
        }

    }
}
