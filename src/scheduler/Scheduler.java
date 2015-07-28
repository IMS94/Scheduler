/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Imesha Sudasingha
 */
public class Scheduler extends Thread {

    private int processCount, timeSlice;
    private long totalTime;
    Queue<Process> readyQueue, auxiliaryQueue,jobQueue;
    LinkedList<Process> blockedQueue;
    private final DefaultTableModel tableModel;
    private JPanel timeline;
    private MainWindow window;
    /*
     Time slice in mili seconds.
     */

    public Scheduler(Process[] processes, int timeSlice, DefaultTableModel model,
            JPanel timeline, MainWindow window) {
        this.processCount = processes.length;
        this.timeSlice = timeSlice;

        this.tableModel = model;
        this.timeline = timeline;
        this.window = window;

        readyQueue = new LinkedList<>();
        auxiliaryQueue = new LinkedList<>();
        blockedQueue = new LinkedList<>();
        jobQueue=new LinkedList<>();

        for (Process process : processes) {
            jobQueue.add(process);
            //readyQueue.add(process);
        }
    }

    //set timeslice
    public void setTimeSlice(int timeSlice) {
        this.timeSlice = timeSlice;
    }

    private void updateGUI(){
        SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            
                            window.auxiliaryQueuePanel.repaint();
                            timeline.repaint();
                            window.readyQueuePanel.repaint();
                            window.blockedQueuePanel.repaint();
                            
                        }
                    }
            );
    }
    
    
    @Override
    public void run() {
        totalTime = 0;

        final long startTime = System.currentTimeMillis();
        //time to keep track of time slices
        long time;
        boolean executing = true;

        Process currentProcess = null;

        
        
        
        
        
        while (executing) {

            updateGUI();
            
            /**
             * Look into the job Queue to find whether there's any new process
             */
            
            if(!jobQueue.isEmpty()){
                Iterator<Process> iter=jobQueue.iterator();
                
                while (iter.hasNext()) {
                    Process p = iter.next();
                    if (p.getArrivalTime()<=totalTime) {
                        iter.remove();
                        readyQueue.add(p);
                    }
                }
                
            }

            updateGUI();
            /**
             * First we should check the blocked queue if it has any unblocked
             * processes. If yes add it to the auxiliary queue.
             */
            if (!blockedQueue.isEmpty()) {
                Iterator<Process> iter = blockedQueue.iterator();

                while (iter.hasNext()) {
                    Process p = iter.next();
                    if (!p.isBlocked()) {
                        iter.remove();
                        auxiliaryQueue.add(p);
                    }
                }
            }
            
            updateGUI();

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
                if (!blockedQueue.isEmpty()) {
                    continue;
                }
                currentProcess = null;
            }

            boolean is_blocked = false;
            if (currentProcess != null && !currentProcess.hasFinished()) {
                
                final int progress=(int)(((float)currentProcess.getExcecutedTime())
                        /((float)currentProcess.getServiceTime())*100);
                
                final String p1=currentProcess.getName();
                final Color c1 = currentProcess.getColor();
                
                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                window.auxiliaryQueuePanel.repaint();
                                timeline.repaint();
                                window.readyQueuePanel.repaint();
                                window.blockedQueuePanel.repaint();
                                window.currentProcessLabel.setBackground(c1);
                                window.currentProcessLabel.setText("Current Process : "+p1);
                                window.progressBar.setValue(progress);
                                window.cpuTimeLabel.setText("CPU Time : "+
                                    (System.currentTimeMillis()-startTime));
                            }
                        }
                );

                is_blocked = currentProcess.execute(timeSlice);

                /*
                 Update the table according to the changes in processes.
                 */
                window.processes.add(currentProcess);

                tableModel.setValueAt(currentProcess.getArrivalTime(),
                        currentProcess.getProcessNumber() - 1, 2);
                tableModel.setValueAt(currentProcess.getServiceTime() - currentProcess.getExcecutedTime()
                        , currentProcess.getProcessNumber() - 1, 3);
                
                final int progress2=(int)(((float)currentProcess.getExcecutedTime())
                        /((float)currentProcess.getServiceTime())*100);
                final String p2=currentProcess.getName();
                final Color c2 = currentProcess.getColor();
                
                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                timeline.repaint();
                                window.readyQueuePanel.repaint();
                                window.blockedQueuePanel.repaint();
                                window.auxiliaryQueuePanel.repaint();
                                window.currentProcessLabel.setBackground(c2);
                                window.currentProcessLabel.setText("Current Process : "+p2);
                                window.progressBar.setValue(progress2);
                                window.cpuTimeLabel.setText("CPU Time : "+
                                    (System.currentTimeMillis()-startTime));
                            }
                        }
                );
                if (is_blocked) {
                    blockedQueue.add(currentProcess);
                    currentProcess = null;
                }

            }

            totalTime = (System.currentTimeMillis() - startTime);

            //all the queues should be finished to finish the sheduler
            if (readyQueue.isEmpty() && auxiliaryQueue.isEmpty()
                    && blockedQueue.isEmpty() && currentProcess == null && jobQueue.isEmpty()) {
                executing = false;
                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                timeline.repaint();
                                window.readyQueuePanel.repaint();
                                window.blockedQueuePanel.repaint();
                                window.auxiliaryQueuePanel.repaint();
                                window.currentProcessLabel.setBackground(Color.white);
                                window.currentProcessLabel.setText("Scheduler Finished");
                                window.progressBar.setValue(0);
                            }
                        }
                );
            }

        }
    }

}
