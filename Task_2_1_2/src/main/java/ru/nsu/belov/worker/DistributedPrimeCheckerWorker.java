package ru.nsu.belov.worker;

import ru.nsu.belov.shared.NetworkUtils;
import ru.nsu.belov.shared.PrimeUtils;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DistributedPrimeCheckerWorker {
    private static final int DEFAULT_PORT = 12345;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Worker started on port " + port);

            while (true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    System.out.println("Accepted connection from " + socket.getInetAddress());

                    int[] numbers = (int[]) NetworkUtils.receiveObject(socket);
                    boolean result = PrimeUtils.hasNonPrime(numbers);
                    NetworkUtils.sendObject(socket, result);

                } catch (Exception e) {
                    System.err.println("Error processing request: " + e.getMessage());
                } finally {
                    NetworkUtils.closeQuietly(socket);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start worker on port " + port);
            e.printStackTrace();
        }
    }
}