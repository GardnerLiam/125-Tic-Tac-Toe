package Root;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
/*
unimportant test files have no need to be looked at but take too much effort to delete.
 */
public class test {
    public static void main(String[] args) {
        String a = "MUSIC:Todokanaikoi";
        System.out.println(a.substring(6));
    }

    public static void check(String subnet) {
        for (int i = 0; i < 255; i++) {
            String host = subnet + "." + i;
            System.out.println("trying " + host);
            try {
                if (InetAddress.getByName(host).isReachable(1)) {
                    try {
                        Socket s = new Socket(host, 2610);
                        System.out.println("Connected");
                        InputStream inFromServer = s.getInputStream();
                        DataInputStream in = new DataInputStream(inFromServer);
                        System.out.println("Server: " + in.readUTF());
                    } catch (IOException e) {
                        System.out.println();
                    }
                }
            }catch (IOException e1){
                System.out.println();
            }
        }
    }
}
