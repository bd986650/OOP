package ru.nsu.belov;

import ru.nsu.belov.master.DistributedPrimeCheckerMaster;
import ru.nsu.belov.worker.DistributedPrimeCheckerWorker;
import org.junit.jupiter.api.Test;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    void testFullSystem() throws Exception {
        Executors.newSingleThreadExecutor().submit(() -> {
            DistributedPrimeCheckerWorker.main(new String[]{"12345"});
        });
        
        TimeUnit.SECONDS.sleep(1);
        
        DistributedPrimeCheckerMaster master = new DistributedPrimeCheckerMaster();
        assertTrue(master.hasNonPrime(new int[]{4, 6, 8}, new String[]{"localhost:12345"}));
        assertFalse(master.hasNonPrime(new int[]{2, 3, 5}, new String[]{"localhost:12345"}));
    }
}