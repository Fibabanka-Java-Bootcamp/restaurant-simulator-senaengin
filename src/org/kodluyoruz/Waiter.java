package org.kodluyoruz;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter implements Runnable {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    Restaurant restaurant;

    Waiter(Restaurant r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {

                lock.lock();
                try {
                    condition.await();
                    System.out.println("Garson temizlik yaptı:");

                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
        }
    }

}
