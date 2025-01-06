package oort.cloud;

import oort.cloud.server.ChatServer;
import oort.cloud.service.ChatService;
import oort.cloud.session.SessionManager;
import oort.cloud.user.UserManager;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Hello world!
 */
public class ChatApplication {
    public static void main(String[] args) {
        try {
            int port = 1234;
            ServerSocket serverSocket = new ServerSocket(port);
            SessionManager sessionManager = new SessionManager();
            UserManager userManager = new UserManager();
            ChatService chatService = new ChatService(userManager);
            new ChatServer(serverSocket, sessionManager, chatService).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
