package server;

import server.manager.BillManager;
import server.manager.CustomerManager;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class TimerSubject extends Observable {
    private HashMap<String, Observer> observerMap = new HashMap<>();
    private int queryInterval;
    private int billSendInterval;
    private int billUpdateInterval;
    public static final String QUERY = "query";
    public static final String BILL_UPDATE = "bill_update";
    public static final String BILL_BROADCAST = "bill_broadcast";

    public TimerSubject(int queryInterval, int billSendInterval, int billUpdateInterval){
        System.out.println("TimerSubject:" + queryInterval + billSendInterval + billUpdateInterval);
        this.queryInterval = queryInterval;
        this.billSendInterval = billSendInterval;
        this.billUpdateInterval = billUpdateInterval;
        observerMap.put(BILL_UPDATE, BillManager.getInstance());
        observerMap.put(QUERY, CustomerManager.getInstance());
        observerMap.put(BILL_BROADCAST, CustomerManager.getInstance());
        int[][] table = getScheduleTable();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (true) {
                    if (table[0][index] == 1) notifyObserver(QUERY, "query");
                    if (table[1][index] == 1) notifyObserver(BILL_BROADCAST, "send_bill");
                    if (table[2][index] == 1) notifyObserver(BILL_UPDATE, null);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    index = (index + 1) % 60;
                }
            }
        }).start();
    }

    private int[][] getScheduleTable() {
        int table[][] = new int[3][60];
        for (int i = 0; i < 60; ++i) {
            if (i % queryInterval == 0) table[0][i] = 1;
            else table[0][i] = 0;
            if (i % billSendInterval == 0) table[1][i] = 1;
            else table[1][i] = 0;
            if (i % billUpdateInterval == 0) table[2][i] = 1;
            else table[2][i] = 0;
        }
        return table;
    }

    public synchronized void addObserver(String type, Observer observer) {
        observerMap.put(type, observer);
    }

    public synchronized void deleteObserver(String type) {
        observerMap.remove(type);
    }

    public void notifyObserver(String type, Object arg) {
        if (observerMap.containsKey(type)) observerMap.get(type).update(this, arg);
    }

}
