package two;

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

            new ClientSocket(socket, reader);
        }finally {
//            socket.close();
        }
    }

    static class ClientSocket{

        private Socket socket;
        private String username;
        private BufferedWriter out;
        private BufferedReader in;

        private BufferedReader input;
        public ClientSocket(Socket socket, BufferedReader input) throws IOException {
            this.socket = socket;
            this.input = input;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Напишите свое имя пользователя");
            this.username = input.readLine();
            out.write("Присоединился к чату "+username+"\n");
            out.flush();
            new ReadMessage().start();
            new WriteMessage().start();
        }


        public class ReadMessage extends Thread {

            @Override
            public void run() {
                String message;
                try {
                    while (true){
                        message = in.readLine();
                        if(message.equals("стоп")){
                            System.out.println("Выключаюсь");
                            ClientSocket.this.stop();
                            break;
                        }
                        System.out.println(message);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    ClientSocket.this.stop();
                }
            }
        }

        public class WriteMessage extends Thread{
            @Override
            public void run() {
                while (true){
                    String message;
                    try {
                        message = input.readLine();
                        if(message.equals("стоп")){
                            out.write("стоп\n");
                            break;
                        }else{
                            out.write(username + " -> " + message +"\n");
                        }
                        out.flush();
                    }catch (IOException e){
                        e.printStackTrace();
                        ClientSocket.this.stop();
                    }
                }
            }
        }


        public void stop(){
            System.out.println("down");
            try {
                if(!socket.isClosed()){
                    socket.close();
                    in.close();
                    out.close();
                }
            }catch (IOException ignored) {}
        }
    }
}
