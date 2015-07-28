/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
//import javafx.print.Collation;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Imesha Sudasingha
 */
public class MainWindow extends javax.swing.JFrame {

    private int processCount = 0;
    List<Process> processes;
    List<Integer> timeSlots;
    private Scheduler scheduler;
    JPanel timeline, readyQueuePanel, blockedQueuePanel, auxiliaryQueuePanel;
    private Color[] colors = {Color.BLUE, Color.RED, Color.GRAY, Color.ORANGE, Color.GREEN, Color.PINK,
        new Color(0,102,0),new Color(51,51,255), Color.MAGENTA,Color.LIGHT_GRAY};
    private int timeSlice;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        processes = new ArrayList<>();
        timeSlots = new ArrayList<>();

        stopButton.setEnabled(false);
        timeline = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                this.setBackground(Color.WHITE);
                int cord = 25;
                boolean onceIdle = false;
                int x = 0;
                for (Process p : processes) {

                    if (p == null && !onceIdle) {

                        g.setColor(Color.BLACK);

                        g.drawRect(cord, 10, 20, 80);
                        onceIdle = true;
                        x++;
                        cord += 25;
                        continue;
                    }
                    g.setColor(p.getColor());
                    int width = timeSlots.get(x);
                    
                    g.fillRect(cord, 10, width, 80);
                    g.setColor(Color.WHITE);
                    g.drawString(""+p.getProcessNumber(), cord+2, 25);
                    cord += (width + 5);
                    onceIdle = false;
                    x++;
                }
            }
        };
        timeline.setSize(1000, 100);
        bottomPanel.add(timeline);

        readyQueuePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                this.setBackground(Color.WHITE);
                int cord = 0;
                if (scheduler != null) {
                    int x = 1;
                    for (Process p : scheduler.readyQueue) {

                        cord += 25;
                        g.setColor(p.getColor());
                        g.fillRect(cord, 10, 20, 80);

                        g.setColor(Color.WHITE);

                        g.drawString("P " + p.getProcessNumber(), cord, 25);
                        g.drawString(Integer.toString(x), cord + 8, 50);
                        x++;
                    }
                }
            }
        };

        readyQueuePanel.setSize(300, 100);
        middlePanel.add(readyQueuePanel);

        blockedQueuePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                this.setBackground(Color.WHITE);
                int cord = 0;
                if (scheduler != null) {
                    int x = 1;
                    for (Process p : scheduler.blockedQueue) {

                        cord += 25;
                        g.setColor(p.getColor());
                        g.fillRect(cord, 10, 20, 80);

                        g.setColor(Color.WHITE);

                        g.drawString("P " + p.getProcessNumber(), cord, 25);
                        g.drawString(Integer.toString(x), cord + 8, 50);
                        x++;
                    }
                }
            }
        };

        blockedQueuePanel.setSize(300, 100);
        middleRightPanel.add(blockedQueuePanel);

        auxiliaryQueuePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                this.setBackground(Color.WHITE);
                int cord = 0;
                if (scheduler != null) {
                    int x = 1;
                    for (Process p : scheduler.auxiliaryQueue) {
                        cord += 25;
                        g.setColor(p.getColor());
                        g.fillRect(cord, 10, 20, 80);

                        g.setColor(Color.WHITE);

                        g.drawString("P " + p.getProcessNumber(), cord, 25);
                        g.drawString(Integer.toString(x), cord + 8, 50);
                        x++;
                    }
                }
            }
        };

        auxiliaryQueuePanel.setSize(300, 100);
        middleMiddlePanel.add(auxiliaryQueuePanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        topleftPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        addProcessButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTimeSlice = new javax.swing.JTextField();
        topRightPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        middlePanel = new javax.swing.JPanel();
        bottomPanel = new javax.swing.JPanel();
        middleRightPanel = new javax.swing.JPanel();
        middleMiddlePanel = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        currentProcessLabel = new javax.swing.JLabel();
        cpuTimeLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        logLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        startButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        addProcessButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addProcessButton.setText("Add Process");
        addProcessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProcessButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel1.setText("Round Robin Sceduling Simulator");

        clearButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Time Slice (s) :");

        txtTimeSlice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout topleftPanelLayout = new javax.swing.GroupLayout(topleftPanel);
        topleftPanel.setLayout(topleftPanelLayout);
        topleftPanelLayout.setHorizontalGroup(
            topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topleftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topleftPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(topleftPanelLayout.createSequentialGroup()
                                .addGroup(topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addProcessButton, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(stopButton, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(txtTimeSlice)))))
                    .addGroup(topleftPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        topleftPanelLayout.setVerticalGroup(
            topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topleftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTimeSlice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(addProcessButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(topleftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(stopButton))
                .addContainerGap())
        );

        table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Process", "Service Time (ms)", "Arrival Time (ms)", "Time Remaining (ms)", "Waitiing Time (ms)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout topRightPanelLayout = new javax.swing.GroupLayout(topRightPanel);
        topRightPanel.setLayout(topRightPanelLayout);
        topRightPanelLayout.setHorizontalGroup(
            topRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topRightPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                .addContainerGap())
        );
        topRightPanelLayout.setVerticalGroup(
            topRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topRightPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout middlePanelLayout = new javax.swing.GroupLayout(middlePanel);
        middlePanel.setLayout(middlePanelLayout);
        middlePanelLayout.setHorizontalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
        );
        middlePanelLayout.setVerticalGroup(
            middlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout middleRightPanelLayout = new javax.swing.GroupLayout(middleRightPanel);
        middleRightPanel.setLayout(middleRightPanelLayout);
        middleRightPanelLayout.setHorizontalGroup(
            middleRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        middleRightPanelLayout.setVerticalGroup(
            middleRightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout middleMiddlePanelLayout = new javax.swing.GroupLayout(middleMiddlePanel);
        middleMiddlePanel.setLayout(middleMiddlePanelLayout);
        middleMiddlePanelLayout.setHorizontalGroup(
            middleMiddlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
        );
        middleMiddlePanelLayout.setVerticalGroup(
            middleMiddlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        progressBar.setStringPainted(true);

        currentProcessLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        currentProcessLabel.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Ready Queue");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Auxiliary Queue");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Blocked Queue");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topleftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topRightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addGap(252, 252, 252)
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(currentProcessLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cpuTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(logLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(middlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(36, 36, 36)
                        .addComponent(middleMiddlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(middleRightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(topRightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(topleftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(middleRightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(middlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(middleMiddlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(currentProcessLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cpuTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        scheduler.stop();

        DefaultTableModel dt = (DefaultTableModel) table.getModel();
        int rows = dt.getRowCount();
        for (int i = rows - 1; i >= 0; i--) {
            dt.removeRow(i);
        }
        clearButtonActionPerformed(evt);
        System.out.println("Scheduler stopped...");
        System.out.println("");
    }//GEN-LAST:event_stopButtonActionPerformed


    private void addProcessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProcessButtonActionPerformed
        if (processCount >= 10) {
            return;//iiii
        }

        Random rand = new Random();
        processCount++;

        //service time in milliseconds.
        int serviceTime = (rand.nextInt(20) + 5) * 1000;

        //process should be given a random arrival time.
        Process process = new Process(serviceTime, (rand.nextInt(20)) * 1000,
                "Process " + processCount, processCount,
                colors[processCount - 1]);

        Object row[] = new Object[5];
        row[0] = process.getName();
        row[1] = process.getServiceTime();
        row[2] = process.getArrivalTime();
        row[3] = process.getTimeRemaining();
        row[4] = process.getWaitingTime();
        processes.add(process);

        ((DefaultTableModel) table.getModel()).addRow(row);
    }//GEN-LAST:event_addProcessButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (processCount < 5) {

            JOptionPane.showMessageDialog(null, "Please add five or more processes and continue", "Invalid process number", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            Process[] p = new Process[processes.size()];
            processes.toArray(p);

            String ts = txtTimeSlice.getText();

            if (ts.equals("")) {
                ts = "4";
            }

            try {
                timeSlice = Integer.parseInt(ts);
                scheduler = new Scheduler(p, timeSlice * 1000, (DefaultTableModel) table.getModel(), timeline, this);
                processes.clear();
                scheduler.start();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid time slice", "Invalid time slice", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            stopButton.setEnabled(true);
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed

        DefaultTableModel dt = (DefaultTableModel) table.getModel();
        int rows = dt.getRowCount();
        for (int i = rows - 1; i >= 0; i--) {
            dt.removeRow(i);
        }

        processes = new ArrayList<>();
        processCount = 0;
        scheduler = null;

        middleMiddlePanel.revalidate();
        middleMiddlePanel.repaint();

        timeSlots.clear();
        processes.clear();
        processCount = 0;

        System.gc();

        System.out.println("Clear processes and reset...");
        System.out.println("---------------------------------");
        System.out.println("");

    }//GEN-LAST:event_clearButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new GraphiteLookAndFeel());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProcessButton;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton clearButton;
    javax.swing.JLabel cpuTimeLabel;
    javax.swing.JLabel currentProcessLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    javax.swing.JLabel logLabel;
    private javax.swing.JPanel middleMiddlePanel;
    private javax.swing.JPanel middlePanel;
    private javax.swing.JPanel middleRightPanel;
    javax.swing.JProgressBar progressBar;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JTable table;
    private javax.swing.JPanel topRightPanel;
    private javax.swing.JPanel topleftPanel;
    private javax.swing.JTextField txtTimeSlice;
    // End of variables declaration//GEN-END:variables
}
