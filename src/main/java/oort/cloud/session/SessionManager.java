package oort.cloud.session;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private List<Session> sessions = new ArrayList<>();

    public synchronized void add(Session session){
        this.sessions.add(session);
    }

    public synchronized void remove(Session session){
        this.sessions.remove(session);
    }

    public synchronized void closeAll(){
        for (Session session : sessions) {
            session.close();
        }
        this.sessions.clear();
    }
}
