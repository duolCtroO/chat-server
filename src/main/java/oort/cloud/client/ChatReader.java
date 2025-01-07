package oort.cloud.client;

import java.io.DataInputStream;
import java.io.IOException;

public class ChatReader implements Runnable{
    private final Client client;
    private final DataInputStream dataInputStream;

    public ChatReader(Client client, DataInputStream dataInputStream) {
        this.client = client;
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
            try {
                while (true){
                    String input = dataInputStream.readUTF();
                    System.out.println("======== 도착 메세지 =====");
                    System.out.println(input);
                    System.out.println("========================");
                }
            } catch (IOException e) {
                System.out.println(e);;
            }finally {
                client.close();
            }
    }
    public void close(){
        System.out.println(this.getClass().getSimpleName() + " close");
    }
}
