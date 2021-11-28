import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket s;
    private BufferedReader in;
    private BufferedWriter out;
    private String clientName;

    public ClientHandler(Socket s) {
        try {
            this.s = s;
            this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.clientName = in.readLine();//get the client's name using BufferedReader
            clientHandlers.add(this);
            broadcastMessage("Server: "+clientName+" has joined");
        } catch (IOException e) {
            closeSocketAndStreams(s, in, out);
        }
    }


    @Override
    public void run() {
        String messageFromClient;
        while(s.isConnected()) {
            try {
                messageFromClient = in.readLine(); // reading a message will block the client thread. Therefore it must be run on seperate threads
                if(messageFromClient.equals("stop")) {
                    throw new IOException(clientName+" has left");
                } else {
                    broadcastMessage(messageFromClient);
                }

            } catch (IOException e) {
                closeSocketAndStreams(s, in, out);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for(ClientHandler clientHandler: clientHandlers) {
            try {
                if(!clientHandler.clientName.equals(clientName)) {
                    clientHandler.out.write(messageToSend);
                    clientHandler.out.newLine();
                    clientHandler.out.flush();
                }
            } catch(IOException e) {
                closeSocketAndStreams(s, in, out);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("Server: "+clientName+" has left");
    }

    public void closeSocketAndStreams(Socket s, BufferedReader in, BufferedWriter out) {
        removeClientHandler(); // if some error happens with a client we are removing the client from the chat
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
}