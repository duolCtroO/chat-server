package oort.cloud.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private ChatReader chatReader;
    private ChatWriter chatWriter;

    private final int PORT;
    private final String SERVER_IP;

    public Client(int port, String server_ip) {
        PORT = port;
        SERVER_IP = server_ip;
    }

    public void start(){
        try {
            socket = new Socket(SERVER_IP, PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            chatReader = new ChatReader(this, dataInputStream);
            chatWriter = new ChatWriter(this, dataOutputStream);

            Thread chatReaderTh = new Thread(chatReader);
            Thread chatWriterTh = new Thread(chatWriter);

            chatReaderTh.start();
            chatWriterTh.start();

            chatReaderTh.join();
            chatWriterTh.join();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally{
            close();
        }

    }

    public synchronized void close(){
        chatWriter.close();
        chatReader.close();
        closeAll(socket, dataInputStream, dataOutputStream);
    }

    private void closeAll(Socket socket, DataInputStream dis, DataOutputStream dos) {
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
