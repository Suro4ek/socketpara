package three;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true){
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Server: test_server\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("\r\n");
            out.write("<title>Test</title>");
            out.write("<p>Test</p>");
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
