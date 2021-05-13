package Root;

import Sockets.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/*
  @name: Advanced Server
  @Author: Liam Gardner
  @Date: 2018/4/4 or 2018/4/5 because it's 12:08AM
 */
public class AdvancedServer extends JFrame {

    JTextArea ta = new JTextArea();
    JTextField naming = new JTextField();
    JButton b = new JButton("Start Server");
    JButton pickSong = new JButton("Pick Song");

    JButton xWin = new JButton("Make X win"); //was not here before, I can cheat in games if I make them!
    JButton oWIn = new JButton("Make O win"); //look up

    JPanel p = new JPanel();
    Server s;
    String added = "";
    boolean hasStarted = false;
    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    if (s.isAlive()) {
                        if (!s.getLastMessage().equals(added)) {
                            added = s.getLastMessage();
                            addText(added);
                        }
                    }
                } catch (Exception e) {
                    addText(e.getMessage());
                }
            }
        }
    });

    public AdvancedServer() {

        xWin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        setTitle("Server Host - Tic Tac Toe");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        ta.setEnabled(false);
        ta.setBackground(getBackground());
        ta.setForeground(Color.BLACK);
        ta.setDisabledTextColor(Color.BLACK);
        add(ta, BorderLayout.CENTER);

        naming.setText("Set Server Name: ");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!hasStarted) {
                    try {
                        if (naming.getText().equals("Set Server Name: ")) {
                            s = new Server("Amakusa Shiro Tokisada");
                            addText("Server 'Amakusa Shiro Tokisada' Started.");
                        } else {
                            s = new Server(naming.getText());
                            addText("Server '" + naming.getText() + "' Started.");
                        }
                        b.setText("Stop Server");
                        hasStarted = true;
                        s.start();
                        t.start();
                    } catch (IOException ex) {
                        addText(ex.getMessage());
                    }
                }else{
                    s.stop();
                    t.stop();
                    hasStarted = false;
                    b.setText("Start Server");
                }
            }
        });
        pickSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // there were only the first three options, the last song wasn't there before.
                // was told to add new songs, so I picked another from the same anime.
                Object[] opts = {"NONE", "ホワイトアルバム", "とどかない 優しい嘘", "回答"};
                String opt = (String) JOptionPane.showInputDialog(null, "Pick Song", "Song", 1, null, opts, opts[0]);
                if (opt == null) {
                    opt = "NONE";
                }
                if (opt.equals(opts[1])) {
                    Server.song = "WhiteAlbum";
                } else if (opt.equals(opts[2])) {
                    Server.song = "Todokanaikoi";
                }else if (opt.equals(opts[3])){
                    Server.song = "Kaitou";
                }
            }
        });
        p.add(naming, BorderLayout.WEST);
        p.add(b, BorderLayout.CENTER);
        p.add(pickSong, BorderLayout.EAST);
        add(p, BorderLayout.SOUTH);
        setVisible(true);
    }
    /*
     @name add text
     @param string text
     @purpose: add text to text area
     */
    private void addText(String text) {
        if (ta.getText().equals("")) {
            ta.setText(text);
        } else {
            ta.setText(ta.getText() + "\n" + text);
        }
    }
    /*
          @please run the TICTACTOE_LG.java file
          @or the tictactoe.jar if you have that.
          @it should be in out/artifacts/TicTacToeLG/tictactoe.jar
         */
    public static void main(String[] args) {
        new AdvancedServer();
    }
}
