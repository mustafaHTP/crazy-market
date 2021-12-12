/**
 * Customer.java
 * 
 * @author mustafaHTP
 */

package queue;


public class Customer {
    private static int numberOfCustomers = 1; //It is used for customerNo

    public int customerNo;
    public double arrivalTime; //Length of the arrival time for coming time to queue
    public double queueStartTime; //The time that customer at queue
    public double startServiceTime; //The time that customer is being served 
    
    public double serviceTime; //total time that customer is served
    public double removalTime; //This is customer dequeue
    public double waitingTime; //The time from the customer's arrival in the queue to the service
    
    public Customer(){
        customerNo = numberOfCustomers;
        ++numberOfCustomers;
    }

    @Override
    public String toString(){
        return String.format("%d | arr t: %.3f | que t: %.3f | stsrv t: %.3f | ser t: %.3f | rm t: %.3f | wait t: %.3f",
        customerNo, arrivalTime, queueStartTime, startServiceTime, serviceTime, removalTime, waitingTime);
    }

}
