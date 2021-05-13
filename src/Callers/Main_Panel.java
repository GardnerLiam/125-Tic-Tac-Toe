package Callers;

import Sockets.Client;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
/*
@name: Main (Main_Panel)
@author: Liam Gardner
@date: Sometime recent.
 */
public class Main_Panel extends JPanel {

    ArrayList<JButton[][]> btns = new ArrayList<JButton[][]>();
    public JTabbedPane tabbedPane = new JTabbedPane();
    JPanel[] dimension = {new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()};
    public static Client c;
    public int game = 0;
    boolean turn = false;
    public Main_Panel(String IP, int port) throws IOException {
        c = new Client(IP, port);
        Client.write(Client.getSocket(), "CONNECT");

        //add 2D array to BTNS
        for (int i = 0; i < 5; i++) {
            JButton[][] array = new JButton[5][5];
            //create 1D array
            for (int j = 0; j < 5; j++) {
                JButton[] alpha = new JButton[5];
                //Create JButton
                for (int k = 0; k < 5; k++) {
                    JButton b = new JButton();
                    b.setFont(new Font("Felix Titling", 0, 100));
                    b.setName("D" + i + "R" + j + "C" + k + " " + 0);
                    alpha[k] = b;
                }
                array[j] = alpha;
            }
            btns.add(array);
        }

        String ServerName = Client.read(Client.getSocket()).substring(8);
        String song = Client.read(Client.getSocket()).substring(6);
        if(!song.equals("NONE")){
            JOptionPane.showMessageDialog(null, "Playing Song: " + song);
            Board.playMusic("/"+song+".wav");
        }
        setSize(800, 600);
        setPreferredSize(new Dimension(800, 600));

        panelBackground(dimension);
        setTabbedPane(tabbedPane, dimension);
        addButtons(dimension);
        buttonAction();
        add(tabbedPane);
        disableAllButtons();
        //setVisible(true);
    }
    /*
      @name start update thread
      @params: client object
      @purpose: start a thread to constantly update buttons and handle other stuff.
     */
    public void start_update_thread(Client c) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String update = Client.read(Client.getSocket());
                        if (update.split(" ").length == 125) {
                            System.out.println(update);
                            int[][][] toUpdateGrid = new Convert(update).ConvertFromObject();
                            update(toUpdateGrid);
                        } else {
                            if (update.equals("GAME:TURN")){
                                turn = true;
                                enableAllButtons();
                            }
                            if (update.equals("GAME:LOSE")) {
                                removeAll();
                                //add(new WinLose(WinLose.GAME_LOST));
                                //repaint();
                                game = 1;
                                Client.disconnect();
                                break;
                            } else if (update.equals("GAME:WIN")) {
                                removeAll();
                                //add(new WinLose(WinLose.GAME_WON));
                                //repaint();
                                game = 2;
                                Client.disconnect();
                                break;
                            }
                            if (update.contains("MUSIC")){
                                System.out.println(update.substring(6));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    /*
      look at button action from Singleplayer
      it's the same thing but this one sends stuff through a socket and disables all buttons off turn
     */
    public void buttonAction() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    btns.get(i)[j][k].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (turn) {
                                JButton b = (JButton) e.getSource();
                                String dim = b.getName().substring(1, 2);
                                String row = b.getName().substring(3, 4);
                                String col = b.getName().substring(5, 6);
                                if (!btns.get(Integer.parseInt(dim))[Integer.parseInt(row)][Integer.parseInt(col)].getName().endsWith(" 0")) {
                                    JOptionPane.showMessageDialog(null, "Cannot play here");
                                } else {
                                    try {
                                        Client.write(Client.getSocket(), dim + " " + row + " " + col);
                                        turn = false;
                                        disableAllButtons();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }
    /*
      don't remember having two update methods...
      @name: update
      wait this is for the grid
      @params: 3d int array
      @purpose: this updates the button text.
     */
    public void update(int[][][] a) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (a[i][j][k] != 0) {
                        btns.get(i)[j][k].setName("D" + i + "R" + j + "C" + k + " " + a[i][j][k]);
                    }
                    if (a[i][j][k] == 1) {
                        btns.get(i)[j][k].setText("X");
                    } else if (a[i][j][k] == 2) {
                        btns.get(i)[j][k].setText("O");
                    }
                }
            }
        }
    }
    /*
      @these next three are similar so i'm grouping them together
      @setbuttons sets or resets the button properties
      @enable/disable all buttons does exactly what it sounds like
     */
    public void setButtons() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    btns.get(i)[j][k].setText("");
                    btns.get(i)[j][k].setName("D" + i + "R" + j + "C" + k + " " + 0);
                    btns.get(i)[j][k].setEnabled(true);
                }
            }
        }
    }

    public void enableAllButtons(){
        for (JButton[][] i : btns){
            for (JButton[] j : i){
                for (JButton k : j){
                    k.setEnabled(true);
                }
            }
        }
    }

    public void disableAllButtons(){
        for (JButton[][] i : btns){
            for (JButton[] j : i){
                for (JButton k : j){
                    k.setEnabled(false);
                }
            }
        }
    }
    /*
      @these next three are copy-pastes from Singleplayer look there. these were programmed a while ago and it's late and i'm too scared to touch it again.
     */
    public void panelBackground(JPanel[] dimension) {
        for (int i = 0; i < 5; i++) {
            dimension[i].setLayout(new GridLayout(5, 5));
        }
        dimension[0].setBackground(Color.RED);
        dimension[1].setBackground(Color.GREEN);
        dimension[2].setBackground(Color.ORANGE);
        dimension[3].setBackground(Color.MAGENTA);
        dimension[4].setBackground(Color.CYAN);
    }

    public void setTabbedPane(JTabbedPane tabbedPane, JPanel[] dimension) {
        tabbedPane.addTab("Dimension 1", dimension[0]);
        tabbedPane.addTab("Dimension 2", dimension[1]);
        tabbedPane.addTab("Dimension 3", dimension[2]);
        tabbedPane.addTab("Dimension 4", dimension[3]);
        tabbedPane.addTab("Dimension 5", dimension[4]);
    }

    public void addButtons(JPanel[] dimension) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    dimension[i].add(btns.get(i)[j][k]);
                }
            }
        }
    }
}
