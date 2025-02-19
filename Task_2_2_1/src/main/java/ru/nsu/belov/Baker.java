package ru.nsu.belov;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Baker {
    private final int id;
    private final int speed;

    // Пустой конструктор для Jackson
    public Baker() {
        this.id = 0;   // Инициализация по умолчанию
        this.speed = 0; // Инициализация по умолчанию
    }

    // Используем конструктор с аннотациями для десериализации
    @JsonCreator
    public Baker(@JsonProperty("id") int id, @JsonProperty("speed") int speed) {
        this.id = id;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public int getSpeed() {
        return speed;
    }

    public void work(OrderQueue orderQueue, Storage storage) {
        while (true) {
            Order order = orderQueue.getOrder();
            if (order == null) break; // Выход из цикла, если заказы больше не принимаются

            System.out.println("Пекарь " + id + " начал готовить заказ " + order.getId());
            try {
                TimeUnit.MILLISECONDS.sleep(speed); // Симуляция готовки
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Пекарь " + id + " приготовил заказ " + order.getId());
            storage.putPizza(order);
        }
    }
}
