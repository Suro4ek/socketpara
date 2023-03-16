package one;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int POPT = 9000;
    private static ServerSocket socket;
    public static void main(String[] args) throws IOException{
        try {
            socket = new ServerSocket(POPT);
            Socket client = socket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            String message = in.readLine();
            System.out.println("Пришло с клиента: " + message);
            System.out.println("Отправляем ответ от сервера");
            out.write("Вы отправили на сервер " + message);
            out.flush();
            client.close();
            in.close();
            out.close();
        }finally {
            socket.close();
        }
    }
}
