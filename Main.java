
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //variables
        int customerServedCount = 0;

        //Start Simulation 
        System.out.println("-----------START SIMULATION ENVIRONMENT ---------------- ");

        //Scanner for input 
        Scanner input = new Scanner(System.in);

        //For initial user inputs 
        System.out.print("Input simulation length (min): ");
        int simulationMinutes;

        // Throw exceptions
        try {
            simulationMinutes = input.nextInt();
        } catch (InputMismatchException e) {
            throw new InvalidInputException();
        }

        if (simulationMinutes <= 0) {
            throw new InvalidSimulationMinutesException();
        }

        System.out.print("Input number of counter: ");
        int maxTellers = input.nextInt();

        System.out.println();
        System.out.println("-----------START SIMULATION ---------------- ");

        //Array for tellers 
        Teller[] tellers = new Teller[maxTellers];
        for (int i = 0; i < maxTellers; i++) {
            tellers[i] = new Teller();
        }

        //Queue for customers
        ListQueue waitLine = new ListQueue();

        // Max queue length during the simulation
        int maxQueueLength = 0;

        // Total number of customers in the queue over the simulation time
        int totalQueueOverTime = 0;

        //Run the simulation for length of simulation minutes
        for (int currentMinute = 1; currentMinute <= simulationMinutes; currentMinute++) {
            System.out.println();
            System.out.println("At the beginning of iteration " + currentMinute + " ...");

            // Serving time for new customer
            System.out.print("Input serving time for a new customer: ");
            int servingTime;

            // Throw exceptions
            try {
                servingTime = input.nextInt();
            } catch (InputMismatchException e) {
                throw new InvalidInputException();
            }

            // If there is a customer, always put them in the queue first
            if (servingTime > 0) {
                waitLine.enqueue(servingTime);
            }

            // Check if there is a free teller
            Teller freeTeller = null;

            for (int i = 0; i < tellers.length; i++) {
                // If teller is free, keep track of it
                if (tellers[i].isCurrentlyFree(currentMinute)) {
                    // Set this free teller to be used
                    freeTeller = tellers[i];

                    // Stop checking now that we have found a free teller
                    break;
                }
            }

            // If there is no free teller, move to the next iteration
            if (freeTeller == null) {
                // Print out this current iteration
                printCurrentIteration(tellers, currentMinute, waitLine);

                // Calcuate max queue length
                maxQueueLength = Math.max(maxQueueLength, waitLine.count());

                // Add total queue length here for calculating average queue length later
                totalQueueOverTime += waitLine.count();

                // Go to the next iteration of the for loop
                continue;
            }

            // If there is no one in the queue, then there is nothing to, move to the next iteraton
            if (waitLine.empty()) {
                // Print out this current iteration
                printCurrentIteration(tellers, currentMinute, waitLine);

                // Calcuate max queue length
                maxQueueLength = Math.max(maxQueueLength, waitLine.count());

                // Add total queue length here for calculating average queue length later
                totalQueueOverTime += waitLine.count();

                // Go to the next iteration of the for loop
                continue;
            }

            // If there is a free teller and there is someone in the queue
            int currentServingTime = (int) waitLine.dequeue();
            int endServingTime = currentServingTime + currentMinute;
            freeTeller.setEndServingTime(endServingTime);

            // increase the number of customers served now that they are at a teller
            customerServedCount++;

            // Print out this current iteration
            printCurrentIteration(tellers, currentMinute, waitLine);

            // Calcuate max queue length
            maxQueueLength = Math.max(maxQueueLength, waitLine.count());

            // Add total queue length here for calculating average queue length later
            totalQueueOverTime += waitLine.count();
        }

        //End the simulation and output info 
        System.out.println();
        System.out.println("-----------END OF SIMULATION ---------------- ");
        System.out.println("Total minute simulated: " + simulationMinutes + " minutes");
        System.out.println("Number of Tellers: " + maxTellers);
        System.out.println("Number of customer served: " + customerServedCount);
        System.out.println("Average number of customers in the queue: " + ((float) totalQueueOverTime / simulationMinutes));
        System.out.println("Max queue length: " + maxQueueLength);
    }

    public static void printCurrentIteration(Teller[] tellers, int currentMinute, ListQueue waitLine) {
        // Printing out the current iteration output
        System.out.println("After " + currentMinute + " minute ##");
        System.out.println();
        for (int i = 0; i < tellers.length; i++) {
            System.out.print("    Teller_" + (i + 1) + " [" + tellers[i].getEndServingTime() + "]");
        }
        System.out.print("    Waiting Queue: " + waitLine.toString());
        System.out.println();
    }
}
