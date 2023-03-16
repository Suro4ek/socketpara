package two;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    private static int POPT = 9000;
    private static ServerSocket socket;
    public static LinkedList<ClientSocket> clientList = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        try {
            socket = new ServerSocket(POPT);
           while (true){
               Socket client = socket.accept();
               try {
                   clientList.add(new ClientSocket(client));
               }catch (IOException e){
                   client.close();
               }
           }
        }finally {
            socket.close();
        }
    }


    static class ClientSocket extends Thread{

        private BufferedWriter out;
        private BufferedReader in;

        public ClientSocket(Socket client) throws IOException{
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            start();
        }

        @Override
        public void run() {
            String message;
            try {
                while (true){
                    message = in.readLine();
                    System.out.println(message);
                    if(message.equals("стоп")){ break;}

                    for (ClientSocket client1 : Server.clientList){
                        client1.sendMessage(message);
                    }
                }
            }catch (IOException ignored){}
        }

        private void sendMessage(String message){
            try {
                out.write(message + "\n");
                out.flush();
            }catch (IOException ignored) {}
        }
    }
}
