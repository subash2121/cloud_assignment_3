import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket ss;

    public Server(ServerSocket ss) {
        this.ss = ss;
    }

    public void startServer() {
        int count = 0;
        try {
            while(!ss.isClosed()) {
                Socket s = ss.accept();
                System.out.println(++count +" client(s) connected");
                ClientHandler clientHandler = new ClientHandler(s); // ClientHandler implements Runnable which will spawn a new thread for each client that is connected with the server

                Thread thread = new Thread(clientHandler); // Thread created to invoke the run() in ClientHandler
                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() { // function to avoid nested try catches in startServer
        try {
            if(ss != null) {
                ss.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6767);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
