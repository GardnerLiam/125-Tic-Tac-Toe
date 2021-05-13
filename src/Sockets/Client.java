package Sockets;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
/*
  @name: Client
  @author: Liam Gardner
  @Date: can't remember
  @Honestly this is really annoying. it's almost 12:00 and I'm really tired.
 */
public class Client {
    static Socket client;
    public Client (String ip, int port) throws IOException{
        client = new Socket(ip, port);
    }
    /*
      @this returns the socket.
     */
    public static Socket getSocket(){
        return client;
    }
    /*
          @please run the TICTACTOE_LG.java file
          @or the tictactoe.jar if you have that.
          @it should be in out/artifacts/TicTacToeLG/tictactoe.jar
          @I think I remember there being a main method in ServerScanner but it's not there :(
    */
    public static void main(String [] args) {
        int port = 2610;
        String server = "127.0.0.1";
        try {
            new Client(server, port);
            String servername = read(client);
            System.out.println("Server name: "+ servername.substring(7));
            String a = "";
            Scanner s = new Scanner(System.in);
            while (!a.contains("GAME")){
                write(client, s.nextLine());
                System.out.println(read(client));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
      @the next three methods are exactly what they sound like
      @write takes sockets and message to send the message through the socket
      @read reads from the socket
      @disconnect closes the scoket
     */
    public static void write(Socket s, String message) throws IOException{
        OutputStream outToServer = s.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);

        out.writeUTF(message);
    }

    public static String read(Socket s) throws IOException{
        InputStream inFromServer = s.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        return in.readUTF();
    }

    public static void disconnect() throws IOException{
        client.close();
    }
}
