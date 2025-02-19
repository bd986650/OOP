package ru.nsu.belov;

import ru.nsu.belov.*;

import java.util.List;
import java.util.concurrent.*;

public class Pizzeria {
    private final List<Baker> bakers;
    private final List<Courier> couriers;
    private final Storage storage;
    private final ExecutorService bakerPool;
    private final ExecutorService courierPool;
    private final OrderQueue orderQueue;
    private final int simulationTime;

    public Pizzeria(PizzeriaConfig config) {
        this.bakers = config.getBakers();
        this.couriers = config.getCouriers();
        this.storage = new Storage(config.getStorageCapacity());
        this.simulationTime = config.getSimulationTime();
        this.orderQueue = new OrderQueue();

        this.bakerPool = Executors.newFixedThreadPool(bakers.size());
        this.courierPool = Executors.newFixedThreadPool(couriers.size());
    }

    public void startSimulation() {
        System.out.println("Пиццерия открылась!");

        // Запуск пекарей
        for (Baker baker : bakers) {
            bakerPool.execute(() -> baker.work(orderQueue, storage));
        }

        // Запуск курьеров
        for (Courier courier : couriers) {
            courierPool.execute(() -> courier.work(storage));
        }

        // Генерация заказов в течение simulationTime секунд
        new Thread(() -> {
            int orderId = 1;
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < simulationTime * 1000) {
                orderQueue.addOrder(new Order(orderId++));
                try {
                    Thread.sleep(1000); // Один заказ в секунду
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            orderQueue.stopTakingOrders();
        }).start();

        // Завершаем работу пиццерии после simulationTime секунд
        try {
            Thread.sleep(simulationTime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Пиццерия закрывается. Завершаем заказы...");
        bakerPool.shutdown();
        courierPool.shutdown();
        try {
            if (!bakerPool.awaitTermination(10, TimeUnit.SECONDS)) {
                bakerPool.shutdownNow();
            }
            if (!courierPool.awaitTermination(10, TimeUnit.SECONDS)) {
                courierPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            bakerPool.shutdownNow();
            courierPool.shutdownNow();
        }
        System.out.println("Работа завершена.");
    }
}
