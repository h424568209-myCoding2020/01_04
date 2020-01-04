import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class Server {
    private static class Task implements Runnable{
        private Socket socket;
        public Task(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                InetAddress inetAddress = socket.getInetAddress();
                int prot = socket.getLocalPort();
                System.out.printf("客户端的主机地址是:%s ,端口号是 %d %n",inetAddress.getHostAddress(),prot);

                InputStream is = socket.getInputStream();
                InputStreamReader reader = new InputStreamReader(is,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(reader);

                OutputStream os = socket.getOutputStream();
                PrintStream printStream = new PrintStream(os,true,"UTF-8");

                Scanner scanner = new Scanner(System.in);
                while(true){
                    String line = bufferedReader.readLine();
                    System.out.println("收到响应：" + line);
                    System.out.println("回复消息: ");
                    String response = "已经收到: " + scanner.nextLine();
                    printStream.println(response);
                    printStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) throws IOException {
            ServerSocket serverSocket = new ServerSocket(12345);
            BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>();

            ExecutorService pool = new ThreadPoolExecutor(
                    100,
                    100,
                    0,
                    TimeUnit.MILLISECONDS,
                    queue
            );
            while (true){
                Socket socket = serverSocket.accept();
                pool.execute(new Task(socket));
            }

        }
    }
}
