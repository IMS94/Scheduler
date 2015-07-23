/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Imesha Sudasingha
 */
public class Scheduler extends Thread {

    private int processCount, timeSlice;
    private long totalTime;
    private Queue<Process> readyQueue, auxiliaryQueue;
    LinkedList<Process> blockedQueue;

    /*
     Time slice in mili seconds.
     */
    public Scheduler(Process[] processes, int timeSlice) {
        this.processCount = processes.length;
        this.timeSlice = timeSlice;

        readyQueue = new LinkedList<>();
        auxiliaryQueue = new LinkedList<>();
        blockedQueue = new LinkedList<>();

        for (Process process : processes) {
            readyQueue.add(process);
        }
    }

    //set timeslice
    public void setTimeSlice(int timeSlice) {
        this.timeSlice = timeSlice;
    }

    @Override
    public void run() {
        totalTime = 0;

        long startTime=System.currentTimeMillis();
        //time to keep track of time slices
        long time;
        boolean executing = true;

        Process currentProcess = null;

        while (executing) {
            
            /**
             * First we should check the blocked queue if it has any unblocked processes.
             * If yes add it to the auxiliary queue.
             */
            if(!blockedQueue.isEmpty()){
                Iterator<Process> iter=blockedQueue.iterator();
                
                while(iter.hasNext()){
                    Process p=iter.next();
                    if(!p.isBlocked()){
                        iter.remove();
                        auxiliaryQueue.add(p);
                    }
                }
            }
            
            //change the process
            time = System.currentTimeMillis();
            if (currentProcess != null && !currentProcess.hasFinished()) {
                readyQueue.add(currentProcess);
            }

            if (!auxiliaryQueue.isEmpty()) {
                currentProcess = auxiliaryQueue.remove();
            } else if (!readyQueue.isEmpty()) {
                currentProcess = readyQueue.remove();
            } else {
                //havent think about the way to check blocked queue.
                if(!blockedQueue.isEmpty()){
                    continue;
                }
                currentProcess = null;
            }

            boolean is_blocked=false;
            if (currentProcess != null && !currentProcess.hasFinished()) {
                currentProcess.setArrivalTime(totalTime);
                is_blocked=currentProcess.execute(timeSlice);
                if(is_blocked){
                    blockedQueue.add(currentProcess);
                    currentProcess=null;
                }
            }
            
            
            totalTime=(System.currentTimeMillis()-startTime);
            
            //all the queues should be finished to finish the sheduler
            if (readyQueue.isEmpty() && auxiliaryQueue.isEmpty() && blockedQueue.isEmpty()) {
                executing = false;
            }

        }
    }

}
