package End;

import Root.AdvancedServer;
import Root.ServerScanner;
import Root.Singleplayer;
import Sockets.Server;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
@name: TicTacToe_LG
@date: 2018/4/5
@Author: Liam Gardner
@Please end my suffering
 */
public class TICTACTOE_LG {
    public static void main(String[] args) {
        boolean forMarking = false;
        if (forMarking) {
            Object opts[] = {"Singleplayer", "Look for servers", "Host a server", "Quit"};
            Object a = JOptionPane.showInputDialog(null, "What do you want to do", "Select", 1, null, opts, opts[0]);
            if (a.equals(opts[0])) {
                new Singleplayer();
            } else if (a.equals(opts[1])) {
                new ServerScanner(JOptionPane.showInputDialog("Enter subnet (incorrect subnets will close the program): "));
            } else if (a.equals(opts[2])) {
                new AdvancedServer();
            } else if (a.equals(opts[3])) {
                System.exit(0);
            }
        }else{
            menu();
        }
    }
    //This method did not exist until 2018/4/7 10:52AM
    //Thought it'd be a better menu screen.
    //the original main method was everything in the for marking == true if statement
    public static void menu() {
        int spacing = 50;

        JFrame f = new JFrame();
        JPanel p = new JPanel();

        JButton b1 = new JButton("Singleplayer");
        JButton b2 = new JButton("Look for servers");
        JButton b3 = new JButton("Host a server");
        JButton b4 = new JButton("Quit");

        JLabel title = new JLabel("125 Tic Tac Toe");

        title.setFont(new Font("Century", 0, 50));
        title.setAlignmentX(title.CENTER_ALIGNMENT);

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        b1.setAlignmentX(b1.CENTER_ALIGNMENT);
        b2.setAlignmentX(b1.CENTER_ALIGNMENT);
        b3.setAlignmentX(b1.CENTER_ALIGNMENT);
        b4.setAlignmentX(b1.CENTER_ALIGNMENT);
        b1.setMinimumSize(new Dimension(130,30));
        b1.setMaximumSize(new Dimension(130,30));
        b2.setMinimumSize(new Dimension(130,30));
        b2.setMaximumSize(new Dimension(130,30));
        b3.setMinimumSize(new Dimension(130,30));
        b3.setMaximumSize(new Dimension(130,30));
        b4.setMinimumSize(new Dimension(130,30));
        b4.setMaximumSize(new Dimension(130,30));
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                try {
                    Thread.sleep(500);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                new Singleplayer();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                try {
                    Thread.sleep(500);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                new ServerScanner(JOptionPane.showInputDialog("Enter subnet (incorrect subnets will close the program): "));
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                try {
                    Thread.sleep(500);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                new AdvancedServer();
            }
        });
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        f.setTitle("125 Tic Tac Toe Menu");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(3);
        f.setLocationRelativeTo(null);
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0,100)));
        p.add(b1);
        p.add(Box.createRigidArea(new Dimension(0,spacing)));
        p.add(b2);
        p.add(Box.createRigidArea(new Dimension(0,spacing)));
        p.add(b3);
        p.add(Box.createRigidArea(new Dimension(0,spacing)));
        p.add(b4);
        p.add(Box.createRigidArea(new Dimension(0,spacing)));
        f.add(p);
        f.setVisible(true);
    }

}
