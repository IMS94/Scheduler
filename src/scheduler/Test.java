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
public class Test {
    public static void main(String[] args) {
        Process[] processes=new Process[5];
        
        for(int x=0;x<5;x++){
            processes[x]=new Process((x+2)*1000,"Process "+x);
        }
        
        Scheduler scheduler=new Scheduler(processes, 1000);
        scheduler.start();
    }
}
