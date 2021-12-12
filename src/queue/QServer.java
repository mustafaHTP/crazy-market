/**
 * QServer.java
 * maintains a customer queue in a circular array 
 * 
 * @author mustafaHTP
 */

package queue;

public class QServer {

    private Customer[] queue;

    private int front = -1;
    private int back = -1; // head&tail of queue
    private int numberOfCustomers = 0;

    public boolean createQueue(int size){
        if(size > 0){
            queue = new Customer [size];
            return true;
        }
        return false;
    }

    public void printQueue(){

        //queue is empty
        if(size() == 0){
            System.out.println("QServer is empty...");
            return;
        }

        //print, queue in correct order
        if(front <= back){
            for(int i = front; i <= back; ++i){
                System.out.println(queue[i].customerNo + "-> ");
            }
        }
        else{
            for (int i = front; i < queue.length; i++) {
                System.out.println(queue[i].customerNo + "-> ");                
            }
            for (int i = 0; i <= back; i++){
                System.out.println(queue[i].customerNo + "-> ");
            }
        }
    }
    /**
     * This method copies customers to newQueue in correct order
     * @param oldQueue source
     * @param newQueue destination
    */
    void copyCustomerTo(Customer [] oldQueue, Customer [] newQueue){

        if(front <= back){
            for(int i = front; i <= back; ++i)
                newQueue[i] = oldQueue[i];       
        }
        else{
            for(int i = front; i < oldQueue.length; ++i)
                newQueue[front-i] = oldQueue[i];
            for(int i = 0; i <= back; ++i){
                newQueue[i] = oldQueue[i]; 
            }
        }
    }

    /**
     * enqueue a customer to qServer[]
     */
    public boolean enqueue(Customer customer) {

        //queue is full
        if((front == 0 && back == queue.length-1) || back == front-1){
            //allocate x2 memory
            Customer [] biggerQueue = new Customer[2*numberOfCustomers];
            copyCustomerTo(queue, biggerQueue);
            queue = biggerQueue;
            
            return false;
        }

        //queue is empty
        if(back == -1){
           back = 0;
           front = 0;
           queue[back] = customer; 
        }
        else if(front != 0 && back == queue.length-1){
            back = 0;
            queue[back] = customer;
        }
        else{
            ++back;
            queue[back] = customer;
        }
        ++numberOfCustomers; //increment 

        return true;
    }

    /**
     * dequeue a customer from qServer
     * 
     */
    public Customer dequeue() {

        if(front == -1){
            System.out.println("QServer is already empty ()");
            System.out.println("dequeue failed :/");
            throw new ArrayIndexOutOfBoundsException();
        }

        //Get removed customer
        Customer removed = queue[front];
        queue[front] = null;

        //there is only 1 customer
        if(front == back){
            front = back = -1;
        }
        else if(front == queue.length-1){
            front = 0;
        }
        else{
            ++front;
        }
        --numberOfCustomers; // decrement

        return removed;
    }

    /**
     * 
     * @return first customer of queue by not removing
     */
    public Customer peek(){
        if(size() == 0){
            System.out.println("QServer is already empty ()");
            System.out.println("peek failed :/");
            throw new ArrayIndexOutOfBoundsException();    
        }
        return queue[front];
    }

    /**
     * 
     * @return last customer of the current QServer by not removing it
     */
    public Customer peekLast(){

        if( isEmpty() == false ){
            return queue[back];
        }
        else{
            System.out.println("Queue is empty...");
            System.out.println("peek failed :/");
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * 
     * @return number of customers in current queue
     */
    public int size() {
        return numberOfCustomers;
    }

    public boolean isEmpty() {
        return (numberOfCustomers == 0);
    }
}
