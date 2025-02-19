package ru.nsu.belov;

import java.util.logging.Logger;

import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for a pizzeria.
 */
public class Pizzeria {
    public SynchronizedQueue<Order> forDelivery;
    public SynchronizedQueue<Order> forBaking;
    public AtomicBoolean isOpen;
    public AtomicInteger deliveredOrders;
    public AtomicInteger bakedOrders;
    private List<Baker> bakers;
    private List<Courier> couriers;
    private Integer workingDayTime;
    private Integer idNumber = 1;
    private static Logger log;

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
        this.forDelivery = new SynchronizedQueue<>(new ArrayDeque<>(), storageCapacity);
        this.forBaking = new SynchronizedQueue<>(new ArrayDeque<>(), bakers.size());
        this.isOpen = new AtomicBoolean(false);
        this.deliveredOrders = new AtomicInteger(0);
        this.bakedOrders = new AtomicInteger(0);
    }

    /**
     * At the beginning of the working day, we start the threads of all bakers and couriers.
     */
    private void startWorkingDay() {
        isOpen.set(true);
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
     * The working day time includes the starting of the day, its length and the ending.
     */
    public void workingDay() throws InterruptedException {
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
        if (isOpen.get()) {
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

    /**
     * End of the working day: bakers and couriers complete all remaining orders.
     * Completing the threads of bakers and couriers.
     */
    private void finishWorkingDay() {
        isOpen.set(false);
        log.info("Пиццерия закрыта!");
        while (this.bakedOrders.get() != (this.idNumber - 1)) {
        }
        for (Baker baker : bakers) {
            baker.interrupt();
        }
        while (this.deliveredOrders.get() != (this.idNumber - 1)) {
        }
        for (Courier courier : couriers) {
            courier.interrupt();
        }
    }
}
