package oort.cloud.session;

import oort.cloud.service.ChatService;
import oort.cloud.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable{
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    private final ChatService chatService;

    private User user;

    public Session(Socket socket, ChatService chatService) throws IOException {
        this.socket = socket;
        this.chatService = chatService;
        this.in = new DataInputStream(this.socket.getInputStream());
        this.out = new DataOutputStream(this.socket.getOutputStream());
    }

    public DataOutputStream getOutputStream(){
        return this.out;
    }

    @Override
    public void run() {
        try {
            String path = "";
            String content = "";
            while (socket.isConnected()){
                System.out.println("Session is Connect");
                String receiveMassage = this.in.readUTF();
                System.out.println("receive Massage : " + receiveMassage);
                path = receiveMassage;
                if(receiveMassage.contains("|")){
                    String[] split = receiveMassage.split("\\|");
                    path = split[0];
                    content = split[1];
                }

                switch (path){
                    case "/join":
                        user = new User(content, this);
                        chatService.join(user);
                        break;
                    case "/message":
                        chatService.sendMassage(content);
                        break;
                    case "/change":
                        chatService.changeName(user, content);
                        break;
                    case "/users":
                        chatService.userList(user);
                        break;
                    case "/exit":
                        chatService.exitChat(user);
                        break;
                    default:
                        break;
                }
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public synchronized void close(){
        try {
            if(!socket.isConnected()){
                return;
            }
            out.writeUTF("Bye!");
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {;
            System.out.println("Session Close Exception : " + e.getMessage());
        }
    }
}
