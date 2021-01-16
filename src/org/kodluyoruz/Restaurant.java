package org.kodluyoruz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {
    Order order;
    ExecutorService exec = Executors.newCachedThreadPool();
    Client client = new Client(this);
    Chef chef = new Chef(this);
    Waiter waiter = new Waiter(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(client);
        exec.execute(waiter);
    }

    public static void main(String[] args) {

        new Restaurant();
    }



}
