package ru.nsu.belov;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private final int capacity;
    private final Queue<Order> pizzas = new LinkedList<>();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    // Метод для добавления пиццы на склад
    public synchronized void putPizza(Order order) {
        // Ждём, если склад переполнен
        while (pizzas.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        pizzas.add(order);
        System.out.println("Пицца с заказом " + order.getId() + " добавлена на склад.");
        notifyAll(); // Сообщаем курьерам, что есть пицца
    }

    // Метод для забора пиццы курьерами
    public synchronized int takePizzas(int maxPizzas) {
        // Ждём, если на складе нет пиццы
        while (pizzas.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return 0;
            }
        }

        int pizzasTaken = Math.min(maxPizzas, pizzas.size());
        for (int i = 0; i < pizzasTaken; i++) {
            pizzas.poll();
        }

        System.out.println("Со склада забрали " + pizzasTaken + " пицц.");
        notifyAll(); // Сообщаем пекарям, что освободилось место
        return pizzasTaken;
    }
}


