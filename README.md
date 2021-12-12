>You can also look at the [doc](docs/Crazymarket.pdf)

# Crazy Market Simulator

In Crazy Market, there is one checkout and 2 queues named QServer and QLottery. The next customer who to be served is determined as follows:

- In the QServer queue, if the waiting time of the next customer is greater than or equal to the Wthreshold, the customer at beginning of the queue receives service. (customer is removed from queue)


- If it is small, the customer is added to QLottery queue. The next customer is determined by generating random number.

The java program simulates operation of this market.

## Details
### Definitions about MM1 Queue
- **Service Time:** The time spent by customer at the checkout
- **Waiting Time:** The waiting time at the checkout until customer receives service
- **Arrival Time:** The arrival of the customer at the checkout or adding to the queue when the queue is full
    - **Arrival rate:** the number of arrivals per unit of time
    - **Inter-arrival time:** the time between each arrival into the system and the next
### Calculation arrival time of the next customer (arrival time)
For the given customer arrival frequency (λ: lambda: arrival rate), the next customer's arrival time can be calculated as follows:
```
Random random = new Random();

double u = random.NextDouble();

double interArrivalTime = -Math.log(u)/(Double.parseDouble(lambda));

customerNext.arrivalTime = customerLast.arrivalTime + interArrivalTime;
```

### Calculation of the next customer service time (service time)
Service time can be calculated from the exponential distribution using the expected average customer service time as follows:
```
Random random = new Random();

double u = random.NextDouble();

customerX.serviceTime = -Math.log(u)/(Double.parseDouble(mu));
```

### Time Progression in Simulation
- As the simulation time progresses, two different events can occur
    - Customer Arrival
    - Customer Departure