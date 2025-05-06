package ru.nsu.belov.worker;

import ru.nsu.belov.shared.NetworkUtils;
import ru.nsu.belov.shared.PrimeUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.concurrent.*;

class DistributedPrimeCheckerWorkerTest {
    private static final int WORKER_PORT = 12349;

    @Test
    void testWorker() throws IOException, ClassNotFoundException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> workerFuture = executorService.submit(() -> {
            try {
                DistributedPrimeCheckerWorker.main(new String[]{String.valueOf(WORKER_PORT)});
            } catch (Exception e) {
                fail("Worker error: " + e.getMessage());
            }
        });

        Thread.sleep(1000);

        try {
            try (Socket socket = new Socket("localhost", WORKER_PORT)) {
                NetworkUtils.sendObject(socket, new int[]{2, 3, 4, 5});
                assertTrue((Boolean) NetworkUtils.receiveObject(socket));
            }

            try (Socket socket = new Socket("localhost", WORKER_PORT)) {
                NetworkUtils.sendObject(socket, new int[]{2, 3, 5, 7});
                assertFalse((Boolean) NetworkUtils.receiveObject(socket));
            }
        } finally {
            executorService.shutdownNow();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}