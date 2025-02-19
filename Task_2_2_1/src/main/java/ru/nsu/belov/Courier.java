package ru.nsu.belov;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Courier {
    private final int id;
    private final int capacity;

    // Пустой конструктор для Jackson
    public Courier() {
        this.id = 0;  // Инициализация по умолчанию
        this.capacity = 0;  // Инициализация по умолчанию
    }

    // Конструктор с аннотациями для Jackson
    @JsonCreator
    public Courier(@JsonProperty("id") int id, @JsonProperty("capacity") int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void work(Storage storage) {
        while (true) {
            int pizzasTaken = storage.takePizzas(capacity);
            if (pizzasTaken == 0) continue; // Если нечего забирать, ждем

            System.out.println("Курьер " + id + " забрал " + pizzasTaken + " пицц(ы) и доставляет...");
            try {
                TimeUnit.MILLISECONDS.sleep(2000); // Симуляция доставки
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Курьер " + id + " доставил заказ.");
        }
    }
}
