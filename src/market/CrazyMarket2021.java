/**
 * CrazyMarket2021.java
 * 
 * Market Simulator, take a look at the document for details
 * 
 * @author mustafaHTP
 * 
 */
package market;
import java.util.Random;
import queue.*;

public class CrazyMarket2021 {
    /** parameters for simulations */
    double lambda; /** arrival rate */
    double mu; /** service rate */
    double simTime; /** holds simulation time */
    boolean isBusy; /** holds checkout condition */
    Customer customerAtCheckout; /** holds customers at checkout */

    /**
     * number of customers to be served. (simulation is done after Nth customer
     * served)
     */
    int N;
    int numberOfCustomersServed; /** number of customers served, used for stats */
    QServer qServer = new QServer();
    QLottery qLottery = new QLottery();

    
    /* variables for statistics */
    double meanWaitingTime = 0; // mean waiting time of SERVED customers
    double totalWaitingTime = 0; // total waiting time of SERVED customers
    double meanServiceTime = 0; // mean service time of SERVED customers
    double totalServiceTime = 0; // total service time of SERVED customers

    //for colorful text at terminal
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    public CrazyMarket2021(double lambda, double mu, int n) {
        this.lambda = lambda;
        this.mu = mu;
        N = n;
        numberOfCustomersServed = 0;
        isBusy = false;
        simTime = 0;

        qServer.createQueue(N);
    }
    
    /**
     * Create customer to add QServer by initialize its arrival and service time
     * @return customer
     */
    public Customer getNextCustomer(){

        Customer nextCustomer = new Customer();
        Random random = new Random();
        double u = random.nextDouble();
         
        //Set arrival time
        double interArrivalTime = -Math.log(u) / (Double.parseDouble(Double.toString(lambda)));
        
        //Set service time
        nextCustomer.serviceTime = -Math.log(u) / (Double.parseDouble(Double.toString(mu)));
        
        //qServer is empty, so there is no last customer
        if(qServer.size() == 0){
            nextCustomer.arrivalTime = interArrivalTime;
            nextCustomer.queueStartTime = nextCustomer.arrivalTime + simTime;
        }
        else{
            double lastCustomerArrivalTime = qServer.peekLast().arrivalTime;
            nextCustomer.arrivalTime = lastCustomerArrivalTime + interArrivalTime;    
            nextCustomer.queueStartTime = nextCustomer.arrivalTime + simTime;
        }

        return nextCustomer;
    }

    /**
     * if there is 1 customer in QServer, dequeue it
     * if there are customers more than 1 and waiting time of the next customer (QServer's head) 
     *    is equal or bigger than THRESHOLD, then dequeue QServer's head;
     *    otherwise add next customer to QLottery and pick a random number then dequeue it
     *    
     * @return Customer to checkout
     * 
     */
    public Customer chooseCustomer(){
        final double THRESHOLD = 0.1;
        /**
         * customer to checkout
         */
        Customer custToCheck = qServer.peek();
        
        if(qServer.size() == 1){
            custToCheck.waitingTime = 0;
            custToCheck.startServiceTime = simTime;
            custToCheck.removalTime = custToCheck.serviceTime + simTime;

            return qServer.dequeue();
        }
        
        double waitingTime = simTime - custToCheck.queueStartTime;
        //First customer of QServer is going to be served
        if( waitingTime >= THRESHOLD ){
            custToCheck.waitingTime = waitingTime;
            custToCheck.startServiceTime = simTime;
            custToCheck.removalTime = custToCheck.serviceTime + custToCheck.waitingTime + custToCheck.queueStartTime;

            return qServer.dequeue();
        }
        //Waiting time smaller than THRESHOLD, then add customer to qLotttery
        //and pick a random number, dequeue customer
        else{

            while(qServer.size() > 0){
                qLottery.add(qServer.dequeue());
            }

            //Get a random customer
            Random random = new Random();
            int customerIdx = random.nextInt(qLottery.size());

            custToCheck = qLottery.remove(customerIdx);

            custToCheck.waitingTime = waitingTime;
            custToCheck.startServiceTime = simTime;
            custToCheck.removalTime = custToCheck.serviceTime + custToCheck.waitingTime + custToCheck.queueStartTime;
            
            return custToCheck;
        }

    }


    public void simulateMarket() {
        //Serve customers until N bigger than  0
        while (N > 0) {
            //Arrival event
            if (qServer.size() == 0 && isBusy == false) {
                Customer newCustomer = getNextCustomer();
                qServer.enqueue(newCustomer);
                System.out.println(ANSI_PURPLE + "+" + newCustomer.toString() + ANSI_RESET);

                simTime += newCustomer.arrivalTime;
            }
            //Arrival event
            //if there is a customer at checkout until its service done add customer
            else if(isBusy == true){

                while(simTime <= customerAtCheckout.removalTime){
                    Customer nextCustomer = getNextCustomer();
                    qServer.enqueue(nextCustomer);
                    System.out.println(ANSI_PURPLE + "+" + nextCustomer.toString() + ANSI_RESET);

                    simTime += nextCustomer.arrivalTime;
                }
                isBusy = false;
            }
            //Departure
            else {
                customerAtCheckout = chooseCustomer();
                --N;
                ++numberOfCustomersServed;
                System.out.println(ANSI_GREEN + " $" + customerAtCheckout.toString() + ANSI_RESET);
                
                //Set STATS 
                totalWaitingTime += customerAtCheckout.waitingTime;
                meanWaitingTime = totalServiceTime / numberOfCustomersServed; 
                totalServiceTime += customerAtCheckout.serviceTime;
                meanServiceTime = totalServiceTime / numberOfCustomersServed; 
                isBusy = true;
            }
        }

    }

    public void printStatistics() {
        System.out.println("totalWaitingTime:" + totalWaitingTime);
        System.out.println("meanWaitingTime:" + meanWaitingTime);
        System.out.println("totalServiceTime:" + totalServiceTime);
        System.out.println("meanServiceTime:" + meanServiceTime);
    }

    public static void main(String[] args) {
        double lambda = Double.parseDouble("1");
        double mu = Double.parseDouble("1");
        int N = 10;
        CrazyMarket2021 cm = new CrazyMarket2021(lambda, mu, N);
        cm.simulateMarket();
        cm.printStatistics();

    }

}
