/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.awt.Color;
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
    
    private int processNumber;
    private int serviceTime,waitingTime,executedTime,block_at,blocked_for;
    Long lastExecutedTime,blocked_at,arrivalTime;
    private String name;
    boolean is_blocked,finished,blockedOnce;
    private Color color;
   
    /**
     * All the time should be in milli seconds
     * Last Executed time is to calculate waiting time.
     * @param serviceTime
     * @param name 
     */
    public Process(int serviceTime,String name,int processNumber,Color color){
        this.name=name;
        this.serviceTime=serviceTime;
        finished=false;
        blockedOnce=false;
        
        this.processNumber=processNumber;
        this.color=color;
        
        //set the block_at time. Process should be added to the blocked queue
        // when this time arrive.
        Random rand=new Random();
        block_at=rand.nextInt(serviceTime-1)+1;
        
        //blocked_for determines, for how long the process to be kept blocked.
        blocked_for=(rand.nextInt(5)+1)*1000;
        
        executedTime=0;
        waitingTime=0;
        arrivalTime=-1L;
        lastExecutedTime=System.currentTimeMillis();
    }
    
    
    /**
     * Returns the name of the process
     * @return 
     */
    public String getName(){
        return name;
    }
    
    public int getProcessNumber(){
        return processNumber;
    }
    
    public Color getColor(){
        return color;
    }
    /**
     * Set the arrival time of a process
     * Arrival time is only set once.
     * @param arrivalTime 
     */
    public void setArrivalTime(long arrivalTime){
        if(this.arrivalTime!=-1){
            return;
        }
        this.arrivalTime=arrivalTime;
    }
    
    /**
     * Get the arrival time of the process.
     * @return 
     */
    public long getArrivalTime(){
        return arrivalTime;
    }
    
    
    public int getServiceTime(){
        return serviceTime;
    }
    
    public int getTimeRemaining(){
        return (serviceTime-executedTime);
    }
    
    public void block(){
        is_blocked=true;
    }
    
    public void unblock(){
        is_blocked=false;
    }
    
    /**
     * Determines if a process is blocked at the moment.
     * Checked by comparing the time it was blocked and the time for which it should be blocked. 
     * @return 
     */
    public boolean isBlocked(){
        if(is_blocked && !hasFinished() && (blocked_at+blocked_for)<=System.currentTimeMillis()){
            is_blocked=false;
            
            System.out.println("Process "+name+" unblocked!");
            return false;
        }
        return is_blocked;
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
        if(finished){
            System.out.println("Process: "+name+" Arrival Time="+arrivalTime+" service time="+serviceTime+" executed time="+executedTime+" waiting time="+waitingTime);
        }
        return finished;
    }
    
    /**
     * Process will execute for the time slice or the remaining time.
     * Process have to be blocked at the given time(randomly assigned)
     * @param slice
     * @return 
     */
    public boolean execute(int slice){
        
        //process is finished is the executed time is greater than service time.
        if(executedTime>=serviceTime){
            finished=true;
            waitingTime+=(System.currentTimeMillis()-lastExecutedTime);
            return false;
        }
        // + the time since the last executed time.
        waitingTime+=(System.currentTimeMillis()-lastExecutedTime);
        
        int time;
        if(executedTime+slice>=serviceTime){
           time=serviceTime-executedTime; 
           finished=true;
        }
        else{
            time=slice;
        }
        
        /**
         *  sleep the thread for the slice or the remaining time and then return.
         * While being executed, we have to check whether the process have to be blocked.
         */
        try {
            
            System.out.println(name+" running...");
            
            long startTime=System.currentTimeMillis();
            while(startTime+time>System.currentTimeMillis()){
                if(!blockedOnce && !finished && 
                        executedTime+(System.currentTimeMillis()-startTime)>=block_at){
                    blockedOnce=true;
                    //process is just blocked. Blocked_at will retain the time it was blocked.
                    is_blocked=true;
                    blocked_at=System.currentTimeMillis();
                    executedTime+=(System.currentTimeMillis()-startTime);
                    lastExecutedTime=System.currentTimeMillis();
                    System.out.println("Process "+name+" blocked");
                    return true;
                }
                
                Thread.sleep(10);
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        executedTime+=time;
        lastExecutedTime=System.currentTimeMillis();
        
        return false;
    }

 
    
}
