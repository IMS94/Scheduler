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
    Queue<Process> readyQueue, auxiliaryQueue;
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

        long startTime = System.currentTimeMillis();
        //time to keep track of time slices
        long time;
        boolean executing = true;

        Process currentProcess = null;

        while (executing) {

            SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            timeline.repaint();
                            window.readyQueuePanel.repaint();
                            window.blockedQueuePanel.repaint();
                            window.auxiliaryQueuePanel.repaint();
                        }
                    }
            );

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
                currentProcess.setArrivalTime(totalTime);
                
                final int progress=(int)(((float)currentProcess.getExcecutedTime())
                        /((float)currentProcess.getServiceTime())*100);
                
                final String p1=currentProcess.getName();
                
                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                timeline.repaint();
                                window.readyQueuePanel.repaint();
                                window.blockedQueuePanel.repaint();
                                window.auxiliaryQueuePanel.repaint();
                                window.currentProcessLabel.setText("Current Process : "+p1);
                                window.progressBar.setValue(progress);
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
                
                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                timeline.repaint();
                                window.readyQueuePanel.repaint();
                                window.blockedQueuePanel.repaint();
                                window.auxiliaryQueuePanel.repaint();
                                window.currentProcessLabel.setText("Current Process : "+p2);
                                window.progressBar.setValue(progress2);
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
                    && blockedQueue.isEmpty() && currentProcess == null) {
                executing = false;
            }

        }
    }

}
