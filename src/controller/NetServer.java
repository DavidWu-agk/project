package controller;
import java.io.*;
import java.net.*;
public class NetServer {
    public void Server(){
        int net=1145;
        try (ServerSocket serverSocket = new ServerSocket(net);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
            String inputLine;
            String outputLine;
            while ((inputLine = in.readLine()) != null) {
                outputLine = "get:" + inputLine;
                out.println(outputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
