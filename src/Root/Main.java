package Root;

import Callers.Convert;
import Sockets.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
//this is never used, don't look here.
public class Main extends JFrame {

    ArrayList<JButton[][]> btns = new ArrayList<JButton[][]>();
    public JTabbedPane tabbedPane = new JTabbedPane();
    JPanel[] dimension = {new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()};
    static Client c;

    public Main(String IP) throws IOException {
        c = new Client(IP, 2610);
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
        setTitle("Tic Tac Toe " + ServerName);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);

        panelBackground(dimension);
        setTabbedPane(tabbedPane, dimension);
        addButtons(dimension);
        buttonAction();

        add(tabbedPane);
        //setVisible(true);

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
                            if (update.equals("GAME:LOSE")){
                                getContentPane().removeAll();
                                //getContentPane().add(new WinLose(WinLose.GAME_LOST));
                                getContentPane().repaint();
                            }else if (update.equals("GAME:WIN")){
                                getContentPane().removeAll();
                                //getContentPane().add(new WinLose(WinLose.GAME_WON));
                                getContentPane().repaint();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void buttonAction() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    btns.get(i)[j][k].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton b = (JButton) e.getSource();
                            String dim = b.getName().substring(1, 2);
                            String row = b.getName().substring(3, 4);
                            String col = b.getName().substring(5, 6);
                            try {
                                Client.write(Client.getSocket(), dim + " " + row + " " + col);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

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

    public static void main(String[] args) {
        try {
            new Main("127.0.0.1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
