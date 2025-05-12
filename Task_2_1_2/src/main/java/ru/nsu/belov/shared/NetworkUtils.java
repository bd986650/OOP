package ru.nsu.belov.shared;

import java.io.*;
import java.net.Socket;

public class NetworkUtils {
    public static void sendObject(Socket socket, Object object) throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(object);
        output.flush();
    }

    public static Object receiveObject(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        return input.readObject();
    }

    public static void closeQuietly(Socket socket) {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            
        }
    }
}