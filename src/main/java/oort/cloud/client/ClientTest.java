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
        Client client = new Client(1234, "localhost");
        client.start();
    }
}
