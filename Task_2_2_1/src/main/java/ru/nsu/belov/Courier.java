package ru.nsu.belov;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class for a courier thread.
 */
public class Courier extends Thread {
    private final String name;
    private final Integer bagCapacity;
    private Pizzeria pizzeria;
    private static final Logger log;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tT:%1$tL] [%4$-7s] %5$s %n");
        log = Logger.getLogger(Pizzeria.class.getName());
    }

    /**
     * Constructor.
     *
     * @param name courier's name
     * @param bagCapacity the number of pizzas that the courier can put in his bag
     */
    public Courier(String name, Integer bagCapacity) {
        this.name = name;
        this.bagCapacity = bagCapacity;
    }

    /**
     * Set a pizzeria.
     *
     * @param pizzeria pizzeria object
     */
    public void setPizzeria(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        log.info("Курьер " +  this.name + " начал работу");
        while (!isInterrupted()) {
            int totalDeliveryTime = 0;
            List<Order> pickedOrders = new ArrayList<>();
            try {
                int amountOfPickedPizzas = this.bagCapacity;
                int count = pizzeria.getOrderCountForDelivery();
                while (!pizzeria.isOpen()) {
                    if (count < bagCapacity && (count != 0)) {
                        amountOfPickedPizzas = count;
                        break;
                    }
                    count = pizzeria.getOrderCountForDelivery();
                }

                for (int i = 0; i < amountOfPickedPizzas; i++) {
                    Order order = pizzeria.getOrderForDelivery();
                    pickedOrders.add(order);
                    totalDeliveryTime += order.getDeliveryTime();
                }

                StringBuffer message = new StringBuffer("[");
                for (int i = 0; i < pickedOrders.size(); i++) {
                    message.append(pickedOrders.get(i).getId());
                    if (i < (pickedOrders.size() - 1)) {
                        message.append(" ");
                    }
                }
                message.append("]");
                log.info(message + " [был собран курьером " + this.name + "]");
                Thread.sleep(totalDeliveryTime);
                log.info(message + " [был доставлен курьером " + this.name + "]");
                pizzeria.increaseDeliveredOrders(pickedOrders.size());
            } catch (InterruptedException e) {
                log.info("Курьер " +  this.name + " закончил работу");
            }
        }
    }

}