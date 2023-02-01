package lotteria;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerThread extends Thread {
    /**
     * Generare 5 numeri random da 1-90
     * Comunicare ai client gli esiti dell'estrazione
     * Conferire la vincita
     */
    MultiServer server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    int client_nums;

    public ServerThread(Socket socket, MultiServer server){
        this.client = socket;
        this.client_nums = MultiServer.getClient_nums();
        this.server = server;
    }


    @Override
    public void run() {
        try{
            synchronized (this){
                wait();
                System.out.println("THREAD PARTITO SZE");
                outVersoClient = new DataOutputStream(client.getOutputStream());
                outVersoClient.writeBytes("Inizio Numeri\n");
                outVersoClient.write(client_nums);
                for(int i: MultiServer.generateClientNumbers(client_nums)){
                    outVersoClient.write(i);
                }
                wait();

                outVersoClient.write(client_nums);
                for(int i: server.getWinning_nums()){
                    outVersoClient.write(i);
                }
            }
        } catch (Exception e){
            e.printStackTrace(System.out);
        }
    }

    public void comunica() throws Exception{
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        for(;;){
            stringaRicevuta = inDalClient.readLine();
            if(stringaRicevuta == null || stringaRicevuta.equals("FINE")){
                outVersoClient.writeBytes(stringaRicevuta+"(=>server in chiusura)" + '\n');
                System.out.println("Echo sul server in chiusura" + stringaRicevuta);
                break;
            } else {
                outVersoClient.writeBytes(stringaRicevuta.toUpperCase() +" (ricevuta e ritrasmessa)" + '\n');
                System.out.println("6 Echo sul server: " + stringaRicevuta);
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("Chiusura socket " + client );
        client.close();
    }
}