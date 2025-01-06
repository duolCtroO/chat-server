package oort.cloud.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientTest {
    private static final int PORT = 1234;

    public static void main(String[] args) throws IOException {

        try(Socket socket = new Socket("localhost", PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            Scanner scanner = new Scanner(System.in);
            Thread write = new Thread(() -> {
                while (!socket.isClosed()) {
                    try {
                        String sendMsg = scanner.nextLine();
                        if (sendMsg.equals("exit")) {
                            closeAll(socket, dataInputStream, dataOutputStream);
                        }
                        dataOutputStream.writeUTF(sendMsg);
                    } catch (IOException e) {
                        closeAll(socket, dataInputStream, dataOutputStream);
                        throw new RuntimeException(e);
                    }
                }
                ;
            });
            Thread view = new Thread(() -> {
                while (!socket.isClosed()) {
                    String received = null;
                    try {
                        received = dataInputStream.readUTF();
                        System.out.println(received);
                    } catch (IOException e) {
                        closeAll(socket, dataInputStream, dataOutputStream);
                    }
                }
            });
            write.start();
            view.start();

            write.join();
            view.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void closeAll(Socket socket, DataInputStream dis, DataOutputStream dos) {
        try {
            if (!socket.isClosed()) socket.close();
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
