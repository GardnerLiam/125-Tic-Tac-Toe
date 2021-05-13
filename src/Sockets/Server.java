package Sockets;

import Callers.Convert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/*
  @name: Server
  @Author: Liam Gardner
  @Date: I don't know. I can't think of a reason where it would be important to know the date of when you created your code.
         Updating I understand, which can be found in your file explorer under "Last Updated"
 */
public class Server extends Thread {

    private ServerSocket serverSocket;
    Socket x;
    Socket o;
    String name;
    static String lastMessage = "";
    public static String song = "NONE";
    int winPlayer = 0;
    boolean makeWin = true;
    private static int[][][] Board = {
            {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}}
    };

    //this is just a getter for the AdvancedServer class
    public String getLastMessage() {
        return lastMessage;
    }

    public Server(String namae) throws IOException {
        serverSocket = new ServerSocket(23610);
        this.name = "Server: " + namae;
    }

    Socket k;

    /*
      Should this really count as something worthy of a method header. it's a built in function brought from the Extends Thread.
      I don't have to explain ActionPerformed(ActionEvent e) methods so I shouldn't have to explain this.
      //this is the entire server. this one method. it runs everything.
     */
    public void run() {
        while (true) {
            try {
                while (x == null) {
                    k = serverSocket.accept();
                    write(k, this.name);
                    if (read(k).equals("CONNECT")) {
                        x = k;
                        System.out.println("X IS NOW CONNECTED");
                    }
                }
                k = null;
                System.out.println(x.isClosed());
                while (o == null) {
                    k = serverSocket.accept();
                    write(k, this.name);
                    if (read(k).equals("CONNECT")) {
                        o = k;
                        System.out.println("O IS NOW CONNECTED");
                    }
                }
                if (x != null && x.isConnected() && o != null && o.isConnected()) {
                    if (!makeWin) {
                        int hasWon = 0;
                        Music(song);
                        while (hasWon == 0) {
                            System.out.println("Retrieving x info");
                            write(x, "GAME:TURN");
                            String newcX = read(x);
                            System.out.println("XInfo: " + newcX);
                            int dep = Integer.parseInt(newcX.split(" ")[0]);
                            int row = Integer.parseInt(newcX.split(" ")[1]);
                            int col = Integer.parseInt(newcX.split(" ")[2]);
                            Board[dep][row][col] = 1;
                            write(x, new Convert(Board).ConvertToObject());
                            write(o, new Convert(Board).ConvertToObject());
                            hasWon = Callers.Board.check(Board);
                            System.out.println("hasWon: " + hasWon);
                            if (hasWon == 1) {
                                write(x, "GAME:WIN");
                                write(o, "GAME:LOSE");
                                serverSocket.close();
                                x.close();
                                o.close();
                                break;
                            } else {
                                System.out.println("Retrieving y info");
                                write(o, "GAME:TURN");
                                String newcO = read(o);
                                System.out.println("YInfo: " + newcO);
                                dep = Integer.parseInt(newcO.split(" ")[0]);
                                row = Integer.parseInt(newcO.split(" ")[1]);
                                col = Integer.parseInt(newcO.split(" ")[2]);
                                Board[dep][row][col] = 2;
                                write(x, new Convert(Board).ConvertToObject());
                                write(o, new Convert(Board).ConvertToObject());
                                hasWon = Callers.Board.check(Board);
                                if (hasWon == 2) {
                                    write(x, "GAME:LOSE");
                                    write(o, "GAME:WIN");
                                    serverSocket.close();
                                    x.close();
                                    o.close();
                                    break;
                                }
                            }
                        }
                    }else{
                        if(winPlayer == 1){
                            write(x, "GAME:WIN");
                            write(o, "GAME:LOSE");
                        }else if (winPlayer == 2){
                            write(x, "GAME:LOSE");
                            write(o, "GAME:WIN");
                        }
                        break;
                    }
                }

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    //this sends the music to both clients.
    public void Music(String Song) throws IOException {
        write(x, "MUSIC:" + Song);
        write(o, "MUSIC:" + Song);
    }

    //read and write do exactly the same thing they do in the Client class.
    public static String read(Socket s) throws IOException {
        DataInputStream in = new DataInputStream(s.getInputStream());
        String text = in.readUTF();
        lastMessage = s.getRemoteSocketAddress().toString() + " " + text;
        //lastMessage = s.getLocalSocketAddress().toString() + " " + text; was the original, this is just so I can easier see where my Friend goes.
        return text;
    }

    public static void write(Socket s, String message) throws IOException {
        DataOutput out = new DataOutputStream(s.getOutputStream());
        out.writeUTF(message);
    }

    //this is a special Write that I don't remember using but it exists. it writes a byte array instead of a string
    public static void write(Socket s, byte[] message) throws IOException {
        DataOutput out = new DataOutputStream(s.getOutputStream());
        out.write(message);
    }

    //this just made it easier to visualize the 3D array by printing it
    //didn't really use it lateron
    public static String build(int[][][] alpha) {
        String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        for (int[][] row : alpha) {
            for (int[] col : row) {
                sb.append(Arrays.toString(col)).append("     ");
            }
            sb.append(lineSeparator);
        }
        return sb.toString();
    }

    /*
          @please run the TICTACTOE_LG.java file
          @or the tictactoe.jar if you have that.
          @it should be in out/artifacts/TicTacToeLG/tictactoe.jar
         */
    public static void main(String[] args) {
        try {
            Thread t = new Server("Tokisada");
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //precursor to Board.check()
    //Does the same thing.
    public static int checkWin() {
        //check for depth
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (Board[0][row][col] == Board[1][row][col] && Board[1][row][col] == Board[2][row][col] && Board[2][0][0] == Board[3][row][col] && Board[3][row][col] == Board[4][row][col] && Board[3][row][col] != 0) {
                    return Board[0][row][col];
                }
            }
        }
        //check for row
        for (int dep = 0; dep < 5; dep++) {
            for (int row = 0; row < 5; row++) {
                if (Board[dep][row][0] == Board[dep][row][1] && Board[dep][row][1] == Board[dep][row][2] && Board[dep][row][2] == Board[dep][row][3] && Board[dep][row][3] == Board[dep][row][4] && Board[dep][row][4] != 0) {
                    return Board[dep][row][0];
                }
            }
        }

        //Check for Col
        for (int dep = 0; dep < 5; dep++) {
            for (int col = 0; col < 5; col++) {
                if (Board[dep][0][col] == Board[dep][1][col] && Board[dep][0][col] == Board[dep][2][col] && Board[dep][0][col] == Board[dep][3][col] && Board[dep][0][col] == Board[dep][4][col] && Board[dep][0][col] != 0) {
                    return Board[dep][0][col];
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (Board[0][i][j] == Board[1][i][j] && Board[0][i][j] == Board[2][i][j] && Board[0][i][j] == Board[3][i][j] && Board[0][i][j] == Board[4][i][j] && Board[0][i][j] != 0) {
                    return Board[0][i][j];
                }
            }
        }

        //diagonal depth win
        if (Board[0][0][0] == Board[1][1][1] && Board[1][1][1] == Board[2][2][2] && Board[2][2][2] == Board[3][3][3] && Board[3][3][3] == Board[4][4][4] && Board[4][4][4] != 0) {
            return Board[0][0][0];
        }
        if (Board[0][4][4] == Board[1][3][3] && Board[1][3][3] == Board[2][2][2] && Board[2][2][2] == Board[3][1][1] && Board[3][1][1] == Board[4][0][0] && Board[4][0][0] != 0) {
            return Board[0][4][4];
        }

        //Diagonal wins
        for (int i = 0; i < 5; i++) {
            if (Board[i][0][0] == Board[i][1][1] && Board[i][0][0] == Board[i][2][2] && Board[i][0][0] == Board[i][3][3] && Board[i][0][0] == Board[i][4][4] && Board[i][0][0] != 0) {
                return Board[i][0][0];
            }
            if (Board[i][4][4] == Board[i][3][3] && Board[i][4][4] == Board[i][2][2] && Board[i][4][4] == Board[i][1][1] && Board[i][4][4] == Board[i][0][0] && Board[i][4][4] != 0) {
                return Board[i][4][4];
            }
        }

        return 0;
    }

    //was not here before along with the edits to the implemented run method and the two variables
    public void forceWin(int player) {
        winPlayer = player;
        makeWin = true;
    }
}
