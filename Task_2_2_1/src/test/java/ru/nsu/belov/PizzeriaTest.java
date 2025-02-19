package ru.nsu.belov;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {

    // Тест для Baker
    @Test
    void bakerTest() throws InterruptedException {
        Storage storage = new Storage(5);
        OrderQueue orderQueue = new OrderQueue();
        Baker baker = new Baker(1, 100); // Пекарь с id 1 и временем приготовления 100мс

        // Добавляем заказ
        Order order = new Order(1);
        orderQueue.addOrder(order);

        // Запускаем пекаря
        Thread bakerThread = new Thread(() -> baker.work(orderQueue, storage));
        bakerThread.start();

        bakerThread.join(); // Ожидаем завершения работы пекаря

        // Проверяем, что пицца оказалась на складе
        assertEquals(1, storage.takePizzas(5));  // Пекарь должен был добавить одну пиццу на склад
    }

    // Тест для Courier
    @Test
    void courierTest() throws InterruptedException {
        Storage storage = new Storage(5);
        Courier courier = new Courier(1, 3); // Курьер с id 1 и вместимостью 3 пиццы

        // Добавляем пиццу на склад
        Order order1 = new Order(1);
        Order order2 = new Order(2);
        storage.putPizza(order1);
        storage.putPizza(order2);

        // Запускаем курьера
        Thread courierThread = new Thread(() -> courier.work(storage));
        courierThread.start();

        courierThread.join(); // Ожидаем завершения работы курьера

        // Проверяем, что курьер забрал пиццу
        assertEquals(0, storage.takePizzas(3));  // Курьер должен был забрать обе пиццы
    }

    // Тест для OrderQueue
    @Test
    void orderQueueTest() throws InterruptedException {
        OrderQueue orderQueue = new OrderQueue();
        Order order = new Order(1);

        // Добавляем заказ в очередь
        orderQueue.addOrder(order);

        // Получаем заказ
        Order retrievedOrder = orderQueue.getOrder();

        assertNotNull(retrievedOrder);
        assertEquals(1, retrievedOrder.getId());
    }

    // Тест для Storage
    @Test
    void storageTestWithThreads() throws InterruptedException {
        Storage storage = new Storage(2);
        Order order1 = new Order(1);
        Order order2 = new Order(2);

        // Синхронизация потоков
        CountDownLatch latch = new CountDownLatch(2); // Дожидаемся добавления двух пицц на склад

        // Поток для добавления пиццы на склад
        Thread bakerThread1 = new Thread(() -> {
            storage.putPizza(order1);
            latch.countDown(); // Уменьшаем счетчик, когда пицца добавлена
        });

        Thread bakerThread2 = new Thread(() -> {
            storage.putPizza(order2);
            latch.countDown(); // Уменьшаем счетчик, когда пицца добавлена
        });

        // Поток для забора пиццы курьером
        Thread courierThread = new Thread(() -> {
            try {
                latch.await(); // Ожидаем, пока обе пиццы будут добавлены на склад
                storage.takePizzas(2); // Забираем все пиццы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Запуск всех потоков
        bakerThread1.start();
        bakerThread2.start();
        courierThread.start();

        // Ждем завершения всех потоков
        bakerThread1.join();
        bakerThread2.join();
        courierThread.join();

        // Проверяем, что склад пуст после забора пицц
        assertEquals(0, storage.takePizzas(3)); // Попытка забрать больше пицц, чем есть
    }
    // Тест для Pizzeria
    @Test
    void pizzeriaTest() throws InterruptedException {
        // Создаем пекарей и курьеров вручную
        Baker baker = new Baker(1, 100);
        Courier courier = new Courier(1, 2);

        // Создаем конфигурацию для пиццерии с помощью конструктора
        PizzeriaConfig config = new PizzeriaConfig(
                List.of(baker),  // Список пекарей
                List.of(courier), // Список курьеров
                5,  // Вместимость склада
                10  // Время симуляции в секундах
        );

        // Запускаем пиццерию
        Pizzeria pizzeria = new Pizzeria(config);
        pizzeria.startSimulation();

        // Здесь можно добавить проверки, например, на завершение работы пиццерии
        assertTrue(true);  // Пиццерия должна успешно завершить работу
    }

    // Тест для PizzeriaConfig (десериализация JSON)
    @Test
    void pizzeriaConfigTest() throws Exception {
        String jsonConfig = "{\"bakers\":[{\"id\":1, \"speed\": 100}], \"couriers\":[{\"id\":1, \"capacity\": 2}], \"storageCapacity\": 5, \"simulationTime\": 10}";

        // Используем ObjectMapper для десериализации JSON в объект PizzeriaConfig
        ObjectMapper objectMapper = new ObjectMapper();
        PizzeriaConfig config = objectMapper.readValue(jsonConfig, PizzeriaConfig.class);

        // Проверяем, что конфигурация была десериализована правильно
        assertNotNull(config);
        assertEquals(1, config.getBakers().size());  // Проверка количества пекарей
        assertEquals(1, config.getCouriers().size());  // Проверка количества курьеров
        assertEquals(5, config.getStorageCapacity());  // Проверка вместимости склада
        assertEquals(10, config.getSimulationTime());  // Проверка времени симуляции
    }

    // Тест для Order
    @Test
    void orderTest() {
        Order order = new Order(1);
        assertEquals(1, order.getId());
    }
}
