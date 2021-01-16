package org.kodluyoruz;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable{

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    private Restaurant restaurant;

    public Client(Restaurant r) {

        restaurant = r;
    }

    public void run() {



        try {
            while (!Thread.interrupted()) {
                lock.lock();
                try {
                    while (restaurant.order == null) {
                        condition.await();
                    }
                } finally {
                    lock.unlock();
                }
                System.out.println("Müşteri siparişi verdi.." + restaurant.order);
                TimeUnit.MILLISECONDS.sleep(4000);

                restaurant.chef.lock.lock();
                try {
                    restaurant.order = null;
                    System.out.println("Yemek teslim edildi");
                    restaurant.chef.condition.signalAll();
                    TimeUnit.MILLISECONDS.sleep(5000);
                } finally {
                    restaurant.chef.lock.unlock();
                }

                try {
                    restaurant.waiter.lock.lock();
                    System.out.println("Müşteri yemeğini yedi gitti");
                    restaurant.waiter.condition.signalAll();
                    TimeUnit.MILLISECONDS.sleep(2000);
                } finally {
                    restaurant.waiter.lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }



}
