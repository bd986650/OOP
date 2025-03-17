package ru.nsu.belov;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Class for a pizzeria.
 */
public class Pizzeria {
    private final SynchronizedQueue<Order> forDelivery;
    private final SynchronizedQueue<Order> forBaking;
    private final AtomicBoolean open;
    private final AtomicInteger deliveredOrders;
    private final AtomicInteger bakedOrders;
    private final List<Baker> bakers;
    private final List<Courier> couriers;
    private final Integer workingDayTime;
    private Integer idNumber = 1;
    private static final Logger log;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT:%1$tL] [%4$-7s] %5$s %n");
        log = Logger.getLogger(Pizzeria.class.getName());
    }

    /**
     * Constructor.
     *
     * @param bakers list of bakers
     * @param couriers list of couriers
     * @param storageCapacity the number of pizzas that can be in storage at the same time
     * @param workingDayTime working day length
     */
    public Pizzeria(List<Baker> bakers, List<Courier> couriers, int storageCapacity, int workingDayTime) {
        this.bakers = bakers;
        this.couriers = couriers;
        this.workingDayTime = workingDayTime;
        this.forDelivery = new SynchronizedQueue<>(storageCapacity);
        this.forBaking = new SynchronizedQueue<>(bakers.size());
        this.open = new AtomicBoolean(false);
        this.deliveredOrders = new AtomicInteger(0);
        this.bakedOrders = new AtomicInteger(0);
    }

    /**
     * The working day time includes the starting of the day, its length and the ending.
     */
    public void work() throws InterruptedException {
        startWorkingDay();
        log.info("Рабочий день начался!");
        Thread.sleep(workingDayTime);
        finishWorkingDay();
        log.info("Рабочий день закончен!");
    }

    /**
     * Processing the received order:
     * assigning a unique ID,
     * adding to the queue if the pizzeria is still open
     * and rejecting the order if the pizzeria is closed.
     *
     * @param order received order
     * @return order confirmation or rejection
     */
    public boolean sendOrder(Order order) throws InterruptedException {
        if (open.get()) {
            order.setId(idNumber);
            log.info("Заказ в " + order.getAddress() + " получил номер: " + order.getId());
            idNumber++;
            forBaking.add(order);
            log.info("[" + order.getId() + "] [был добавлен в очередь]");
            return true;
        } else {
            log.info("Заказ в " + order.getAddress() + " был отменен");
            return false;
        }
    }

    public Order getOrder() throws InterruptedException {
        return forBaking.get();
    }

    public void sendForDelivery(Order order) throws InterruptedException {
        forDelivery.add(order);
    }

    public void increaseOrderCount() {
        bakedOrders.getAndIncrement();
    }

    public int getOrderCountForDelivery() {
        return forDelivery.size();
    }

    public boolean isOpen() {
        return open.get();
    }

    public Order getOrderForDelivery() throws InterruptedException {
        return forDelivery.get();
    }

    /**
     * At the beginning of the working day, we start the threads of all bakers and couriers.
     */
    private void startWorkingDay() {
        open.set(true);
        for (Baker baker : bakers) {
            baker.setPizzeria(this);
            baker.start();
        }
        for (Courier courier : couriers) {
            courier.setPizzeria(this);
            courier.start();
        }
    }

    /**
     * End of the working day: bakers and couriers complete all remaining orders.
     * Completing the threads of bakers and couriers.
     */
    private void finishWorkingDay() {
        open.set(false);
        log.info("Пиццерия закрыта!");
        for (Baker baker : bakers) {
            baker.interrupt();
        }
        for (Courier courier : couriers) {
            courier.interrupt();
        }
    }

    public void increaseDeliveredOrders(int size) {
        deliveredOrders.getAndAdd(size);
    }
}
