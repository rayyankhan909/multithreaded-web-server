
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    public void run() throws IOException {
        int port = 8010;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(10000);

            while (true) {
                System.out.println("Server is listening on port " + port);

                try (Socket acceptedConnection = serverSocket.accept();
                     PrintWriter toClient = new PrintWriter(
                             acceptedConnection.getOutputStream(), true);
                     BufferedReader fromClient = new BufferedReader(
                             new InputStreamReader(
                                     acceptedConnection.getInputStream()))) {

                    System.out.println("Connection accepted from client "
                            + acceptedConnection.getRemoteSocketAddress());

                    // Read message from client
                    String message = fromClient.readLine();
                    System.out.println("Message from client: " + message);

                    // Send response to client
                    toClient.println("Hello from the server!");

                } catch (SocketTimeoutException ex) {
                    System.out.println("No client connected within 10 seconds.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

        try {
            server.run();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
