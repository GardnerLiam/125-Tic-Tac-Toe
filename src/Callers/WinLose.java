package Callers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 @name: WinLose
 @Author: Liam Gardner
 @Date: 2018/4/4 --> 2018/4/5
 */
public class WinLose extends JPanel {
    public static final int GAME_LOST = 0;
    public static final int GAME_WON = 1;

    public WinLose(int type) {
        JLabel lbl = new JLabel();
        setSize(800, 600);
        setLayout(new GridLayout(1,1));
        if (type == 0) {
            System.out.println(getWidth());
            setBackground(Color.RED);
            lbl.setText("YOU LOST");
            lbl.setFont(new Font("Tahoma", 0, 100));
            add(lbl);
        }
        if (type == 1) {
            System.out.println(getWidth());
            setBackground(Color.GREEN);
            lbl.setText("YOU WON");
            lbl.setFont(new Font("Tahoma", 0, 100));
            add(lbl);
        }
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        t.start();
    }
}
