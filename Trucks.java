
import java.util.LinkedList;

public class Trucks {
    public static void main ( String [] args) {

        // arrival and service rate initialized
        double arrival_rate = 3. / 60.;
        double service_rate = 4. / 60.;

        // inter_arrival and current_arrival_time (total elapsed time) declared
        double inter_arrival;
        double current_arrival_time = 0;

        // individual truck service time (time it takes to unload) and time truck finish unloading created
        double truck_service_time;
        double finished_time;
        
        // time truck starts unloading
        double load_time;

        // truck identifier and wait time
        int truck_id = 1;
        double wait_time = 0;

        // LinkedList created for arrival and finish time
        LinkedList<Double> arrival = new LinkedList<Double>();
        
        // Expo random number created for arrival time and service time
        ExpoRandom arrival_time = new ExpoRandom(10302047, arrival_rate);
        ExpoRandom service_time = new ExpoRandom(10302047, service_rate);

        // Header printed for data table
        System.out.println("\nTruck ID \t     Arrival\t\t  Warehouse Arrival   \t      Wait Time  \t Service Duration   \t    Departure\n");

        do {
            // inter arrival time of truck
            inter_arrival = arrival_time.nextValue();

            // inter arrival time added to total time to keep a running clock
            current_arrival_time += inter_arrival;

            //arrival time addid to linked list as long as value less than 600 min (10 hours)
            // linked list created for entire 10 hour span
            arrival.addFirst(current_arrival_time);
        }
        while (current_arrival_time <= 600);

        // 1st truck service time created from expo random number
        truck_service_time = service_time.nextValue();

        // 1st truck service time added to last truck arrival time to get time truck finishes unloading
        finished_time = truck_service_time + arrival.getLast();

        // 1st truck finish time added to linked list
        System.out.println("   " + truck_id + " \t\t " + arrival.getLast() + " \t " + arrival.getLast() + " \t\t " + wait_time + " \t\t" + truck_service_time + " \t" + finished_time);
        arrival.removeLast();
        
        // all other trucks after first truck need to see if they have to wait
        do {
            truck_id++;
            // next truck arrival time in linked list compared to previous trucks finish time
            //if previous truck finish time is after truck arrival then the truck must wait
            if (arrival.getLast() < finished_time) {

                // truck begins unloading when previous truck finishes
                load_time = finished_time;

                // new time it takes for truck to unload determined
                // finished and wait time calculated
                truck_service_time = service_time.nextValue();
                finished_time = truck_service_time + load_time;
                wait_time = load_time - arrival.getLast();
                System.out.println("   " + truck_id + " \t\t " + arrival.getLast() + " \t " + load_time + " \t " + wait_time + " \t" + truck_service_time + " \t" + finished_time);
                arrival.removeLast();
            }
            
            // If truck doesnt have to wait it immediately begins unloading
            else {
                // service time calculated
                truck_service_time = service_time.nextValue();
                finished_time = truck_service_time + arrival.getLast();
                wait_time = 0;
                System.out.println("   " + truck_id + " \t\t " + arrival.getLast() + " \t " + arrival.getLast() + " \t\t " + wait_time + " \t\t" + truck_service_time + " \t" + finished_time);
                arrival.removeLast();
            }
        }
        while (arrival.getLast() < 600);
    }
}
