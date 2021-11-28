import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket s;
    private BufferedReader in;
    private BufferedWriter out;
    private String clientName;

    public Client(Socket s, String clientName) {
        try {
            this.s = s;
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            this.clientName = clientName;
        } catch(IOException e) {
            closeSocketAndStreams(s, in, out);
        }
    }

    public void sendMessage() {
        try {
            out.write(clientName);
            out.newLine();
            out.flush();

            Scanner scanner = new Scanner(System.in);
            while(s.isConnected()) {
                String messageToSend = scanner.nextLine();
                out.write(clientName+": "+messageToSend);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            closeSocketAndStreams(s, in, out);
        }
    }

    public void listenForMessages() {
        new Thread(new Runnable() { // listening to messages is a blocking operation. If thread is not used here then we may end up waiting for messages from other users and not be able to send messages
            @Override
            public void run() {
                String messageFromClients;

                while(s.isConnected()) {
                    try {
                        messageFromClients = in.readLine();
                        System.out.println(messageFromClients);
                    } catch(IOException e) {
                        closeSocketAndStreams(s, in, out);
                    }
                }
            }
        }).start();
    }

    public void closeSocketAndStreams(Socket s, BufferedReader in, BufferedWriter out) {

        try {
            if(in != null) {
                in.close();
            }

            if(out != null) {
                out.close();
            }

            if(s != null) {
                s.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name");
        String clientName = scanner.nextLine();
        Socket s = new Socket("localhost", 6767);
        Client client = new Client(s, clientName);
        client.listenForMessages();
        client.sendMessage();
    }
}
