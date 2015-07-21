/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Imesha Sudasingha
 */
public class Process implements Comparable{
    
    //service time is set when initializing
    //arrival time to be set manually
    
    
    private int serviceTime,arrivalTime,waitingTime,executedTime;
    Long startTime;
    private String name;
    boolean is_blocked,finished;
    
    /**
     * All the time should be in milli seconds
     * @param serviceTime
     * @param name 
     */
    public Process(int serviceTime,String name){
        this.name=name;
        this.serviceTime=serviceTime;
        finished=false;
    }
    
    
    //set the arrival time of the process.
    public void setArrivalTime(int arrivalTime){
        this.arrivalTime=arrivalTime;
    }
    
    
    public void block(){
        is_blocked=true;
    }
    
    public void unblock(){
        is_blocked=false;
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
            return false;
        }
        
        int time;
        if(executedTime+slice>serviceTime){
           time=serviceTime-executedTime; 
           finished=true;
        }
        else{
            time=slice;
        }
        
        //sleep the thread for the slice or the remaining time and then return
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return is_blocked;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    
}
