package ru.nsu.belov;

import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

/**
 * Tests for Task_2_2_1.
 */
public class PizzeriaTests {
    @Test
    void test() throws IOException, ParseException, InterruptedException {
        List<Order> orders = ReadFiles.ordersRead("src/test/resources/orders.json");
        Pizzeria pizzeria = ReadFiles.pizzeriaRead("src/test/resources/pizzeria.json");
        Thread pizzeriaThread = new Thread(() -> {
            try {
                pizzeria.work();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread clientThread = new Thread(() -> {
            try {
                new Client(pizzeria, orders);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        pizzeriaThread.start();
        clientThread.start();
        clientThread.join();
        pizzeriaThread.join();
    }
}