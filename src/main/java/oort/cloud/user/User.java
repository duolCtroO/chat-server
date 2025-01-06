package oort.cloud.user;

import oort.cloud.session.Session;

public class User {
    private String name;
    private Session session;

    public User(String name, Session session) {
        this.name = name;
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
