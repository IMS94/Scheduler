/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Imesha Sudasingha
 */
public class Scheduler extends Thread{
    private int processCount,timeSlice;
    long totalTime;
    private Queue<Process> readyQueue,auxiliaryQueue,blockedQueue;
    
    /*
        Time slice in mili seconds.
    */
    public Scheduler(Process[] processes,int timeSlice){
        this.processCount=processes.length;
        this.timeSlice=timeSlice;
        
        readyQueue=new PriorityQueue();
        auxiliaryQueue=new PriorityQueue();
        blockedQueue=new PriorityQueue();
        
        for(Process process:processes){
            readyQueue.add(process);
        }
    }
    
    //set timeslice
    public void setTimeSlice(int timeSlice){
        this.timeSlice=timeSlice;
    }
    
    @Override
    public void run(){
        totalTime=0;
        
        //time to keep track of time slices
        long time=System.currentTimeMillis();
        boolean executing=true;
        
        Process currentProcess=null;
        
        while(executing){
            if(time+timeSlice<System.currentTimeMillis()){
                continue;
            }
            else{
                //change the process
                time=System.currentTimeMillis();
                if(currentProcess!=null && !currentProcess.hasFinished()){
                    readyQueue.add(currentProcess);
                }
            }
            
            if(!auxiliaryQueue.isEmpty()){
                currentProcess=auxiliaryQueue.remove();
            }
            else if(!readyQueue.isEmpty()){
                currentProcess=readyQueue.remove();
            }
            else{
                //havent think about the way to check blocked queue.
            }
            
            if(currentProcess!=null){
                currentProcess.execute(timeSlice);
            }
            
            //all the queues should be finished to finish the sheduler
            if(readyQueue.isEmpty() && auxiliaryQueue.isEmpty() && blockedQueue.isEmpty()){
                executing=false;
            }
        }
    
    }
    
}
