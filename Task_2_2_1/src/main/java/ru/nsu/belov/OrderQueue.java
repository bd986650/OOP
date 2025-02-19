package ru.nsu.belov;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private final Queue<Order> orders = new LinkedList<>();
    private boolean acceptingOrders = true;

    public synchronized void addOrder(Order order) {
        if (!acceptingOrders) return;
        orders.add(order);
        notifyAll();
    }

    public synchronized Order getOrder() {
        while (orders.isEmpty()) {
            if (!acceptingOrders) return null;
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return orders.poll();
    }

    public synchronized void stopTakingOrders() {
        acceptingOrders = false;
        notifyAll();
    }
}
