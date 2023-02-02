package client_server;
import java.net.*;
import java.io.*;
import java.util.Random;

public class ServerStr extends Thread {

    Random rand = new Random();
    int upperbound = 90;

    int[] estrazioni = new int[5];


    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public ServerStr(Socket socket, int estrazione1, int estrazione2, int estrazione3, int estrazione4, int estrazione5){
        this.client = socket;
        this.estrazioni[0] = estrazione1;
        this.estrazioni[1] = estrazione2;
        this.estrazioni[2] = estrazione3;
        this.estrazioni[3] = estrazione4;
        this.estrazioni[4] = estrazione5;
    }

    @Override
    public void run() {
       try{
           comunica();
       } catch (Exception e){
           e.printStackTrace(System.out);
       }
    }

    public void comunica() throws Exception{
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        outVersoClient.writeBytes("I numeri vincenti sono: " + estrazioni[0] +" "+ estrazioni[1] +" "+ estrazioni[2] +" "+ estrazioni[3] +" "+ estrazioni[4]+"\n");

        boolean vincente = false;

        for(int i = 0;i <5; i++){
            stringaRicevuta = inDalClient.readLine();
            for(int j = 0;j <5; j++) {
                if (Integer.parseInt(stringaRicevuta)==estrazioni[j]){
                    vincente = true;
                }
            }
            if(vincente == true){
                outVersoClient.writeBytes("Bravo il tuo numero vincente era il: " + stringaRicevuta + "\n");
                vincente = false;
            }
            else{
                outVersoClient.writeBytes("Mi dispiace non e' vincente"+"\n");
            }
        }
        System.out.println("Echo sul server in chiusura" + stringaRicevuta);
        outVersoClient.close();
        inDalClient.close();
        System.out.println("Chiusura socket " + client );
        client.close();
    }
}
