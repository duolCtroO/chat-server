package oort.cloud.user;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> userList;

    public UserManager() {
        userList = new ArrayList<>();
    }

    public synchronized void addUser(User user){
        this.userList.add(user);
    }

    public synchronized void removeUser(User user){
        this.userList.remove(user);
    }

    public List<User> getUserList(){
        return this.userList;
    }

}
