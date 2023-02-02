package client_server;

import java.io.*;
import java.net.*;
import java.util.Random;

public class MultiClient {
    Random rand = new Random();
    int upperbound = 90;

    int[] numerelli = new int[5];

    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket socket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    String nomeGiocatore;

    public Socket connetti(){
        System.out.println("\n\n"+nomeGiocatore + " ha giocato i numeri.\n");
        try{
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket(nomeServer,portaServer);
            outVersoServer = new DataOutputStream(socket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Host sconosciuto");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }
        return socket;
    }
    public void comunica(){
        numerelli[0] = rand.nextInt(upperbound);
        numerelli[1] = rand.nextInt(upperbound);
        numerelli[2] = rand.nextInt(upperbound);
        numerelli[3] = rand.nextInt(upperbound);
        numerelli[4] = rand.nextInt(upperbound);
        try{
            stringaRicevutaDalServer = inDalServer.readLine();
            System.out.println(stringaRicevutaDalServer);
            for(int i = 0;i <5; i++){
                System.out.println("Valore:" + numerelli[i]);
                stringaUtente = String.valueOf(numerelli[i]);
                outVersoServer.writeBytes(stringaUtente + "\n");
                stringaRicevutaDalServer = inDalServer.readLine();
                System.out.println(stringaRicevutaDalServer);
            }

            socket.close();

        } catch( Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione");
            System.exit(1);
        }
    }

    public MultiClient(String nomeGiocatore) {
        this.nomeGiocatore = nomeGiocatore;
    }

    public static void main(String[] args) {
        MultiClient cliente1 = new MultiClient("Pippo");
        cliente1.connetti();
        cliente1.comunica();

        MultiClient cliente2 = new MultiClient("Marco");
        cliente2.connetti();
        cliente2.comunica();

        MultiClient cliente3 = new MultiClient("Paolo");
        cliente3.connetti();
        cliente3.comunica();

        MultiClient cliente4 = new MultiClient("Maria");
        cliente4.connetti();
        cliente4.comunica();
    }
}
