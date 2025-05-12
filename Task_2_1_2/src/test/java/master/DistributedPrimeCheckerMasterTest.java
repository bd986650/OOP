package ru.nsu.belov.master;

import ru.nsu.belov.worker.DistributedPrimeCheckerWorker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

class DistributedPrimeCheckerMasterTest {
    private ExecutorService executorService;
    private static final int WORKER1_PORT = 12347;
    private static final int WORKER2_PORT = 12348;

    @BeforeEach
    void setUp() {
        executorService = Executors.newFixedThreadPool(2);
        
        executorService.submit(() -> {
            try {
                DistributedPrimeCheckerWorker.main(new String[]{String.valueOf(WORKER1_PORT)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                DistributedPrimeCheckerWorker.main(new String[]{String.valueOf(WORKER2_PORT)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        executorService.shutdownNow();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testHasNonPrime() throws ExecutionException, InterruptedException {
        DistributedPrimeCheckerMaster master = new DistributedPrimeCheckerMaster();
        
        String[] workers = {
            "localhost:" + WORKER1_PORT,
            "localhost:" + WORKER2_PORT
        };
        
        assertTrue(master.hasNonPrime(new int[]{4, 6, 8}, workers));
        assertFalse(master.hasNonPrime(new int[]{2, 3, 5}, workers));
        assertTrue(master.hasNonPrime(new int[]{2, 4, 3}, workers));
    }

    @Test
    void testWorkerFailure() throws ExecutionException, InterruptedException {
        DistributedPrimeCheckerMaster master = new DistributedPrimeCheckerMaster();
        
        String[] workers = {
            "localhost:" + WORKER1_PORT,
            "localhost:" + WORKER2_PORT,
            "localhost:9999"
        };
        
        assertTrue(master.hasNonPrime(new int[]{4, 6, 8}, workers));
        assertFalse(master.hasNonPrime(new int[]{2, 3, 5}, workers));
    }

    @Test
    void testWorkerCrash() throws ExecutionException, InterruptedException {
        DistributedPrimeCheckerMaster master = new DistributedPrimeCheckerMaster();
        
        String[] workers = {
            "localhost:" + WORKER1_PORT,
            "localhost:" + WORKER2_PORT
        };

        executorService.shutdownNow();
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                DistributedPrimeCheckerWorker.main(new String[]{String.valueOf(WORKER1_PORT)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(1000);

        assertTrue(master.hasNonPrime(new int[]{4, 6, 8}, workers));
        assertFalse(master.hasNonPrime(new int[]{2, 3, 5}, workers));
    }
}