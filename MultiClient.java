package lotteria;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MultiClient {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket socket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    int[] nums;

    private Set<Integer> numberOfEqualNumbersInArray(int[] arr1, int[] arr2){
        if(arr1.length != arr2.length){
            return new HashSet<>();
        }
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int i = 0; i < arr1.length; i++) {
            set1.add(arr1[i]);
            set2.add(arr2[i]);
        }
        set1.retainAll(set2);
        return set1;
    }


    public Socket connetti(){
        System.out.println("2 CLIENT partito in esecuzione");
        try{
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket(nomeServer,portaServer);
            outVersoServer = new DataOutputStream(socket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //inDalServerObj = new ObjectInputStream(socket.getInputStream());
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
        try {
            System.out.println(inDalServer.readLine());
            int n_nums = inDalServer.read();
            nums = new int[n_nums];
            for(int i = 0;i<n_nums;i++){
                nums[i] = inDalServer.read();
            }
            n_nums = inDalServer.read();
            int[] winning_nums = new int[n_nums];
            //System.out.println("WINNING:");
            for(int i = 0;i<n_nums;i++){
                winning_nums[i] = inDalServer.read();
            }
            System.out.println(numberOfEqualNumbersInArray(nums,winning_nums));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        MultiClient cliente1 = new MultiClient();
        MultiClient cliente2 = new MultiClient();
        MultiClient cliente3 = new MultiClient();
        MultiClient cliente4 = new MultiClient();
        cliente1.connetti();
        cliente2.connetti();
        cliente3.connetti();
        cliente4.connetti();
        cliente1.comunica();
        cliente2.comunica();
        cliente3.comunica();
        cliente4.comunica();
    }
}