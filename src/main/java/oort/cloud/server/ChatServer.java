package oort.cloud.server;


import oort.cloud.service.ChatService;
import oort.cloud.session.Session;
import oort.cloud.session.SessionManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private final ServerSocket serverSocket;
    private final ChatService chatService;
    private SessionManager sessionManager;

    public ChatServer(ServerSocket serverSocket, SessionManager sessionManager, ChatService chatService) {
        this.serverSocket = serverSocket;
        this.sessionManager = sessionManager;
        this.chatService = chatService;
    }

    public void start() throws IOException {
        while (true){
            Socket socket = this.serverSocket.accept();
            Session session = new Session(socket, chatService);
            new Thread(session).start();
            sessionManager.add(session);
        }
    }
}
