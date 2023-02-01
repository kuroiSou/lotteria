package lotteria;

import java.util.ArrayList;
import java.util.TimerTask;

public class StartLotteryTask extends TimerTask {
    ArrayList<ServerThread> players;
    boolean started;
    public void run(){
        System.out.println("So passati 10 secondi brutto tonto");
    }
    public StartLotteryTask(MultiServer tcpServer) {
        tcpServer.startLottery();
    }
}