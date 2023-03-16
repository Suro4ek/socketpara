package one;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static Socket socket;
    public static void main(String[] args) throws UnknownHostException, IOException {
        try {
            socket = new Socket("localhost", 9000);
            //Читаем с консоли
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("Напишите сообщения для сервера:");
            String message = reader.readLine();
            out.write(message + "\n");
            out.flush();
            String serverMessage = in.readLine();
            System.out.println(serverMessage);
            in.close();
            out.close();
        }finally {
            socket.close();
        }
    }
}
