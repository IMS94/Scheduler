/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Imesha Sudasingha
 */
public class Process{
    
    //service time is set when initializing
    //arrival time to be set manually
    
    
    private int serviceTime,arrivalTime,waitingTime,executedTime,block_at;
    Long lastExecutedTime;
    private String name;
    boolean is_blocked,finished;
    
    /**
     * All the time should be in milli seconds
     * Last Executed time is to calculate waiting time.
     * @param serviceTime
     * @param name 
     */
    public Process(int serviceTime,String name){
        this.name=name;
        this.serviceTime=serviceTime;
        finished=false;
        
        //set the block at time. Process should be added to the blocked queue
        // when this time arrive.
        Random rand=new Random();
        block_at=rand.nextInt(serviceTime-1)+1;
        
        executedTime=0;
        waitingTime=0;
        arrivalTime=-1;
        lastExecutedTime=System.currentTimeMillis();
    }
    
    
    /**
     * Set the arrival time of a process
     * Arrival time is only set once.
     * @param arrivalTime 
     */
    public void setArrivalTime(int arrivalTime){
        if(this.arrivalTime!=-1){
            return;
        }
        this.arrivalTime=arrivalTime;
    }
    
    /**
     * Get the arrival time of the process.
     * @return 
     */
    public int getArrivalTime(){
        return arrivalTime;
    }
    
    
    public void block(){
        is_blocked=true;
    }
    
    public void unblock(){
        is_blocked=false;
    }
    
    /**
     * Return the waiting time of the given process.
     * @return 
     */
    public int getWaitingTime(){
        return waitingTime;
    }
    
    
    /**
     * To get the executed time of a given process
     * Executed time is the total time spent executing the process.
     * @return 
     */
    public int getExcecutedTime(){
        return executedTime;
    }
    
    /**
     * Determine whether the process have finished execution.
     * @return 
     */
    public boolean hasFinished(){
        return finished;
    }
    
    /**
     * Process will execute for the time slice or the remaining time.
     * @param slice
     * @return 
     */
    public boolean execute(int slice){
               
        if(executedTime>=serviceTime){
            finished=true;
            return false;
        }
        waitingTime+=(System.currentTimeMillis()-lastExecutedTime);
        
        int time;
        if(executedTime+slice>=serviceTime){
           time=serviceTime-executedTime; 
           finished=true;
        }
        else{
            time=slice;
        }
        
        //sleep the thread for the slice or the remaining time and then return
        try {
            System.out.println(name+" running...");
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        executedTime+=time;
        lastExecutedTime=System.currentTimeMillis();
        
        return is_blocked;
    }

 
    
}
