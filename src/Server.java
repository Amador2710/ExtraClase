import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*Clase servidor para iniciar el servidor del chat*/

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Server Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        return null;
    }
/*Aqui es donde ocurre la magia que permite iniciar el servidor*/
    public void  startServer() {

        try {

            while (!serverSocket.isClosed()){

                Socket socket = serverSocket.accept();
                System.out.println("Un nuevo usuario se conectó!");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }

        } catch (IOException e) {
        }
    }
/*Aqui es donde se hace que el servidor se cierre*/
    public void closeServerSocket() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();


    }
}