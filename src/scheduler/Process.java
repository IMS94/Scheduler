/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

/**
 *
 * @author Imesha Sudasingha
 */
public class Process {
    private int serviceTime,arrivalTime,waitingTime,executedTime;
    Long startTime;
    private String name;
    boolean is_blocked;
    
    public Process(int serviceTime,String name){
        this.name=name;
        this.serviceTime=serviceTime;
        
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
     *  A process will only be executed if it is not blocked.
    */
    public boolean execute(){
        return is_blocked;
    }
    
}
