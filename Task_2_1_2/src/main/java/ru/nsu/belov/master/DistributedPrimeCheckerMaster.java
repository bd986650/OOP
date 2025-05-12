package ru.nsu.belov.master;

import ru.nsu.belov.shared.NetworkUtils;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class DistributedPrimeCheckerMaster {
    private static final int DEFAULT_TIMEOUT = 5000;
    private static final int MAX_RETRIES = 3;

    public boolean hasNonPrime(int[] numbers, String[] workerAddresses) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<Boolean>> futures = new ArrayList<>();

        try {
            int chunkSize = (numbers.length + workerAddresses.length - 1) / workerAddresses.length;

            for (int i = 0; i < workerAddresses.length; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, numbers.length);
                int[] chunk = Arrays.copyOfRange(numbers, start, end);

                String[] addressParts = workerAddresses[i].split(":");
                String host = addressParts[0];
                int port = addressParts.length > 1 ? Integer.parseInt(addressParts[1]) : 12345;

                futures.add(executor.submit(() -> processChunk(chunk, host, port)));
            }

            for (Future<Boolean> future : futures) {
                if (future.get()) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            executor.shutdown();
        }
    }

    private Boolean processChunk(int[] chunk, String host, int port) {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), DEFAULT_TIMEOUT);

                NetworkUtils.sendObject(socket, chunk);
                return (Boolean) NetworkUtils.receiveObject(socket);

            } catch (Exception e) {
                System.err.printf("Attempt %d failed for worker %s:%d - %s%n",
                        attempt + 1, host, port, e.getMessage());

                if (attempt == MAX_RETRIES - 1) {
                    System.err.printf("Worker %s:%d failed after %d attempts%n",
                            host, port, MAX_RETRIES);
                }
            } finally {
                NetworkUtils.closeQuietly(socket);
            }
        }
        return false;
    }
}