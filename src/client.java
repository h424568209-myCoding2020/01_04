import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        Scanner scanner  = new Scanner(System.in);

        Socket socket = new Socket();
        byte[] ipv4 = { (byte)192,(byte)168,(byte)43,(byte)166};
        //Creates an InetAddress based on the provided host name and IP address.
        InetAddress inetAddress = InetAddress.getByAddress(ipv4);

        //Creates a socket address from an IP address and a port number.
        SocketAddress socketAddress  = new InetSocketAddress(inetAddress,12345);

        //Connects this socket to the server.
        socket.connect(socketAddress);
        while(true){
            System.out.println("请输入: ");
            String message = scanner.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os,true,"UTF-8");
            out.println(message);
            out.flush();

            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is,"UTF-8"));
            String response = reader.readLine();
            System.out.println(response);
        }
    }
}
