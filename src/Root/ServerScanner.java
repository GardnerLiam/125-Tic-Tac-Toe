package Root;

import Callers.Main_Panel;
import Callers.WinLose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
/*
  @Author: Liam Gardner
  @Class: Server Scanner
  @Date: Sometime Recent, don't remember.
 */
public class ServerScanner extends JFrame {

    private JButton scan = new JButton("Scan Network");
    private JPanel p = new JPanel();
    private int y = 10;

    private String subnet = "192.168.1";
    public int port = 23610;

    public ServerScanner(String net) {
        this.subnet = net;
        JButton spec = new JButton("Scan IP");
        JPanel s = new JPanel();

        setTitle("Server Scanner");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        p.setLayout(null);
        scan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check(subnet);
                scan.setEnabled(false);
            }
        });

        spec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = JOptionPane.showInputDialog("Enter Server IP");
                if (!ip.contains(subnet)) {
                    try {
                        InetAddress addr = InetAddress.getByName(ip);
                        ip = addr.getHostAddress();
                    } catch (UnknownHostException ex) {
                        ex.printStackTrace();
                    }
                }
                checkIP(ip);
            }
        });

        add(p, BorderLayout.CENTER);

        BorderLayout b = new BorderLayout();
        s.setLayout(b);
        JPanel one = new JPanel();
        one.setLayout(new BorderLayout());
        one.add(scan, BorderLayout.SOUTH);
        JPanel two = new JPanel();
        two.setLayout(new BorderLayout());
        two.add(spec, BorderLayout.SOUTH);
        s.add(one);
        s.add(two, BorderLayout.EAST);

        add(s, BorderLayout.SOUTH);

        setVisible(true);
    }
    /*
      @name: check
      @params: network subnet (eg 192.168.1)
      @purpose: check for servers
     */
    private void check(String subnet) {
        for (int i = 0; i < 256; i++) {
            String host = subnet + "." + i;
            System.out.println("Trying " + host);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket s = new Socket();
                        s.connect(new InetSocketAddress(host, port), 500);
                        InputStream inFromServer = s.getInputStream();
                        DataInputStream in = new DataInputStream(inFromServer);
                        String Text = in.readUTF();
                        OutputStream outToServer = s.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        out.writeUTF("LOOK");
                        JButton b = new JButton();
                        b.setText(Text);
                        b.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(null, "Test from " + host);
                                try {
                                    test(host);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        b.setBounds(10, y, 130, 30);
                        y += 40;
                        p.add(b);
                        p.repaint();


                    } catch (IOException e) {

                    }
                }
            }).start();
        }
    }
    /*
     @name: CheckIP
     @params: ip
     @purpose: check ip for server
     */
    private void checkIP(String ipAddress) {

        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ipAddress, port), 500);
            InputStream inFromServer = s.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            String Text = in.readUTF();
            OutputStream outToServer = s.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("LOOK");
            JButton b = new JButton();
            b.setText(Text);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Test from " + ipAddress);
                    //setVisible(false);
                    try {
                        test(ipAddress);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            b.setBounds(10, y, 130, 30);
            y += 40;
            p.add(b);
            p.repaint();
        } catch (IOException e) {

        }

    }
    /*
     @name test
     @params ip
     @purpose changes from scanner to tic tac toe
     */
    private void test(String ip) throws IOException {
        getContentPane().removeAll();
        //getContentPane().setLayout(null);
        Main_Panel mp = new Main_Panel(ip, port);
        add(mp.tabbedPane);
        mp.start_update_thread(Main_Panel.c);
        getContentPane().repaint();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (mp.game == 1){
                        getContentPane().removeAll();
                        add(new WinLose(WinLose.GAME_LOST));
                        getContentPane().repaint();
                        setSize(800,600);
                        break;
                    }else if (mp.game == 2){
                        getContentPane().removeAll();
                        add(new WinLose(WinLose.GAME_WON));
                        getContentPane().repaint();
                        setSize(800,600);
                        break;
                    }
                    System.out.println(mp.game);
                }
            }
        }).start();
        setSize(800, 601);
    }
}
