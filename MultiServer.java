package client_server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MultiServer {
    Random rand = new Random();
    int upperbound = 90;

    int estrazione1 = rand.nextInt(upperbound);
    int estrazione2 = rand.nextInt(upperbound);
    int estrazione3 = rand.nextInt(upperbound);
    int estrazione4 = rand.nextInt(upperbound);
    int estrazione5 = rand.nextInt(upperbound);
    public void start(){
        try{
            System.out.println(estrazione1 + " " + estrazione2 + " " + estrazione3 + " " + estrazione4 + " " + estrazione5);
            ServerSocket serverSocket= new ServerSocket(6789);
            for(;;){
                System.out.println("1 Server in attesa...");
                Socket socket =  serverSocket.accept();
                System.out.println("3 Server socket" + socket);
                ServerStr serverThread = new ServerStr(socket, estrazione1, estrazione2, estrazione3, estrazione4, estrazione5);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        MultiServer tcpServer =  new MultiServer();
        tcpServer.start();
    }
}
