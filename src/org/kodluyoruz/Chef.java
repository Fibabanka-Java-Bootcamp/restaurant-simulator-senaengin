package org.kodluyoruz;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chef implements Runnable {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private Restaurant restaurant;
    private int count = 0;

    public Chef(Restaurant r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                lock.lock();
                try {

                    while (restaurant.order != null) {
                        condition.await();
                    }
                } finally {
                    lock.unlock();
                }

                if (++count == 5) {

                    return;
                }
                restaurant.client.lock.lock();
                try {
                    restaurant.order = new Order(count);
                    restaurant.client.condition.signalAll();
                } finally {
                    restaurant.client.lock.unlock();
                }
                TimeUnit.MILLISECONDS.sleep(5000);
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }
    }
}
