package lotteria;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MultiServer {
    ArrayList<ServerThread> players;
    Timer timer;
    boolean started;
    public static int client_nums = 5;
    Integer[] winning_nums;
    public static int getClient_nums(){
        return client_nums;
    }
    public Integer[] getWinning_nums(){
        return winning_nums;
    }

    public static Integer[] generateClientNumbers(int size){
        Random rd = new Random(); // creating Random object
        Integer[] nums = new Integer[size];
        Set<Integer> arr = new HashSet<Integer>();
        while(  arr.size() < size) {
            arr.add(rd.nextInt(90) + 1); // storing random integers in an array
        }
        arr.toArray(nums);
        return nums;
    }


    public MultiServer(){
        this.players = new ArrayList<ServerThread>();
        this.started = false;
    }
    public void startLottery(){
        if(players.size() >= 4){
            System.out.println("Started ez");
            setStarted(true);
            for (ServerThread player : players) {
                synchronized (player){
                    System.out.println("Notificato");
                    player.notify();
                }
            }
            winning_nums = generateClientNumbers(getClient_nums());
            for (ServerThread player : players) {
                synchronized (player){
                    player.notify();
                }
            }

        }
    }
    public void start(){
        try{
            ServerSocket serverSocket= new ServerSocket(6789);
            while(!started){
                System.out.println("1 Server in attesa...");
                timer = new Timer();
                timer.schedule(new StartLotteryTask(this),1000);
                Socket socket =  serverSocket.accept();
                timer.cancel();
                System.out.println("3 Server socket" + socket);
                ServerThread serverThread = new ServerThread(socket,this);
                players.add(serverThread);
                serverThread.start();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            System.exit(1);
        }
    }
    public void setStarted(boolean started){
        this.started = started;
    }

    public static void main(String[] args) {
        MultiServer tcpServer =  new MultiServer();
        tcpServer.start();
    }
}