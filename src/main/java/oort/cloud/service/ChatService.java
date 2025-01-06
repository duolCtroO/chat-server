package oort.cloud.service;

import oort.cloud.user.User;
import oort.cloud.user.UserManager;

import java.io.IOException;
import java.util.List;

public class ChatService {
    private final UserManager userManager;

    public ChatService(UserManager userManager) {
        this.userManager = userManager;
    }

    public void join(User user){
        userManager.addUser(user);
        try {
            user.getSession().getOutputStream().writeUTF("welcome!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMassage(String message){
        userManager.getUserList().stream().forEach(user -> {
            try {
                user.getSession().getOutputStream().writeUTF(message);
            } catch (IOException e) {
                System.out.println("Message Send Error!");
                throw new RuntimeException(e);
            }
        });
    }

    public void changeName(User user, String name){
        user.setName(name);
    }

    public void userList(User user){
        try {
            user.getSession().getOutputStream().writeUTF(userManager.getUserList().toString());
        } catch (IOException e) {
            System.out.println("User List Error : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void exitChat(User user){
        user.getSession().close();
    }


}
