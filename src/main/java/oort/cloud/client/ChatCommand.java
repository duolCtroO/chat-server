package oort.cloud.client;

public enum ChatCommand {
    JOIN("/join"),
    SEND_MASSAGE("/message"),
    CHANGE_NAME("/change"),
    USERS("/users"),
    EXIT("/exit"),
    ;
    private String command;
    ChatCommand(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
