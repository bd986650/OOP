package ru.nsu.belov;

import java.util.logging.Logger;

/**
 * Class for a baker thread.
 */
public class Baker extends Thread {
    private final String name;
    private final Integer bakingTime;
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
     * @param name baker's name
     * @param bakingTime baking time for one pizza
     */
    public Baker(String name, Integer bakingTime) {
        this.name = name;
        this.bakingTime = bakingTime;
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
        log.info("Пекарь " +  this.name + " начал работу");
        while (!isInterrupted()) {
            try {
                Order order = pizzeria.getOrder();
                log.info("[" + order.getId() + "]" + " [был взят курьером "
                        + this.name + "]");
                Thread.sleep(this.bakingTime);
                log.info("[" + order.getId() + "]" + " [был закончен пекарем "
                        + this.name + "]");
                pizzeria.sendForDelivery(order);
                log.info("[" + order.getId() + "]" + " [был положен пекарем "
                        + this.name + " на склад]");
                pizzeria.increaseOrderCount();
            } catch (InterruptedException e) {
                log.info("Пекарь " +  this.name + " закончил работу");
            }
        }
    }
}