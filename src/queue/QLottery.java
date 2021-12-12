/**
 * QLottery.java
 * 
 * Queue implementation by using LinkedList, LinkedQueue
 * 
 * @author mustafaHTP
 */

package queue;


public class QLottery {

    private class Node{
        Customer customer;
        Node next;
        Node prev;
    }

    private Node head = null;
    private Node tail = null;

    private int numberOfCustomers = 0;


    public void printQueue(){

        if(head == null){
            System.out.println("Queue is empty...");
            return;
        }

        Node curr = head;
        while(curr != null){
            System.out.println(curr.customer.customerNo + "-> ");
            curr = curr.next;
        }
        System.out.println("NULL");
    }

    /**
     * Adds customer to end of the queue
     * @param customer that is going to be added to queue
     */
    public void add(Customer customer){

        Node newNode = new Node();
        newNode.customer = customer;

        // Queue is empty
        if(head == null){
            newNode.next = null;
            newNode.prev = null;
            head = newNode;
            tail = newNode;
        }
        else{
            tail.next = newNode;
            newNode.next = null;
            newNode.prev = tail;
            tail = newNode;
        }
        ++numberOfCustomers;
    }

    /**
     * In queue, removes customer in given index
     * @param index that holds customer that is going to be removed
     */
    public Customer remove(int index){
        
        if(index >= 0 && index < numberOfCustomers){
            
            Node removed;
            
            //There is only an one customer
            if(index == 0 && head == tail){
                removed = head;
    
                head = tail = null;

                --numberOfCustomers;
                return removed.customer;
            }
            //There are customers more than 1 
            // head must be changed
            else if(index == 0){
                removed = head;

                head = head.next; //move head
                head.prev = null;

                --numberOfCustomers;
                return removed.customer;
            }
            //There are customers more than 1
            // tail must be changed
            else if(index == numberOfCustomers-1){
                removed = tail;
                tail.prev.next = null;
                tail = tail.prev;
                
                --numberOfCustomers;
                return removed.customer;
            }
            //first search customer by index and then removed
            else{ 
                //Search
                removed = head;
                for(int i = 0; i < index; ++i ){
                    removed = removed.next;
                }

                removed.prev.next = removed.next;
                removed.next.prev = removed.prev;

                --numberOfCustomers;
                return removed.customer;
            }
        }
        else{
            //Queue is already empty and index out of bounds
            System.out.println("Wrong index...");
            System.out.println("remove failed :/");
            return null;
        }

    }

    /**
     * 
     * @return number of Customers in current Queue
     */
    public int size(){
        return numberOfCustomers;
    }
    
    public boolean isEmpty(){
        return (numberOfCustomers == 0);
    }

    /**
     * 
     * @return first customer of the current Qlottery by not removing it
     */
    public Customer peek(){

        if( isEmpty() == false ){
            return head.customer;
        }
        else{
            System.out.println("Queue is empty...");
            System.out.println("peek failed :/");
            return null;
        }
    }

}
