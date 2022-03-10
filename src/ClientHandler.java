import java.io.*;
import java.net.PortUnreachableException;
import java.net.Socket;
import java.util.ArrayList;

/*Esta es la clase que se utilizará para controlar los clientes*/
public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            /*Aquí se administrarán los mennsajes que aparecen cuando una persona se une al chat*/
            broadcastMessage("Servidor:" + clientUsername + " ha entrado al chat!");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }
    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    /*Aquí se administrarán los clientes que salen del chat*/
    public void removeClientHandler(){
        clientHandlers.remove(this);
        /*Aquí se administrarán los mennsajes que aparecen cuando una persona se salió al chat*/
        broadcastMessage("Servidor:" + clientUsername + "salió del chat");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
