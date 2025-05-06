package ru.nsu.belov.shared;

import ru.nsu.belov.shared.NetworkUtils;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import static org.junit.jupiter.api.Assertions.*;

class NetworkUtilsTest {

    @Test
    void testSendAndReceiveObject() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            int port = serverSocket.getLocalPort();
            
            new Thread(() -> {
                try (Socket clientSocket = serverSocket.accept()) {
                    Object received = NetworkUtils.receiveObject(clientSocket);
                    NetworkUtils.sendObject(clientSocket, "Response: " + received);
                } catch (Exception e) {
                    fail("Server error: " + e.getMessage());
                }
            }).start();

            try (Socket socket = new Socket("localhost", port)) {
                NetworkUtils.sendObject(socket, "Test Message");
                String response = (String) NetworkUtils.receiveObject(socket);
                assertEquals("Response: Test Message", response);
            }
        }
    }
}