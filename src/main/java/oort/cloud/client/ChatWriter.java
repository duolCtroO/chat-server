package oort.cloud.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ChatWriter implements Runnable{
    private final Client client;
    private final DataOutputStream dataOutputStream;

    private final String SEPARATOR = "|";

    public ChatWriter(Client client, DataOutputStream dataOutputStream) {
        this.client = client;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String name = "";
        do{
            System.out.println("Welcome Chat");
            System.out.println("사용할 이름을 작성해 주세요.");
            System.out.print("이름 : ");
            name = scanner.nextLine();
        }while (name.isEmpty());

        try {
            dataOutputStream.writeUTF(ChatCommand.JOIN.getCommand());
            while (true){
                System.out.println("1. 메세지 보내기 2. 이름 바꾸기 3. 모든 사용자 조회 4. 종료");
                int input = 0;
                System.out.print("선택: ");
                input = scanner.nextInt();
                String content = "";
                scanner.nextLine();
                switch (input) {
                    case 1:
                        System.out.print("메세지 입력 : ");
                        content = scanner.nextLine();
                        dataOutputStream.writeUTF(createSendData(ChatCommand.SEND_MASSAGE, content));
                        break;
                    case 2:
                        System.out.print("변경할 이름 : ");
                        content = scanner.nextLine();
                        dataOutputStream.writeUTF(createSendData(ChatCommand.CHANGE_NAME, content));
                        break;
                    case 3:
                        dataOutputStream.writeUTF(createSendData(ChatCommand.USERS, content));
                        break;
                    case 4:
                        System.out.println("bye!");
                        close();
                        break;
                    default:
                        System.out.println("잘못된 입력 입니다.");
                        continue;
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            System.out.println(e);
        } finally{
            client.close();
        }
    }

    public void close(){
        try {
            System.in.close();
            System.out.println(this.getClass().getSimpleName() + " close");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private String createSendData(ChatCommand command, String content){
        String sendData = switch (command){
            case JOIN -> null; //추 후에 필요할지도...?
            case SEND_MASSAGE, CHANGE_NAME
                    -> command.getCommand() + SEPARATOR + content;
            case USERS, EXIT -> command.getCommand();
        };
        return sendData;
    }
}
