import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class main {


    public static void main(String[] args) throws IOException {

        String Wort = "Testat";
        String buchstabe = "";
        String found[];
        String wrong[];
        int guesses;
        int maxguesses = 10;
        int i = 0;
        int tempi = 0;
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Waiting for clients to connect...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);


            // Process the command and send the response back to the client
            int laenge = 0;

            while (laenge < Wort.length()) {
                buchstabe = input.readLine();
                System.out.println("Debug: " + buchstabe);
                if (buchstabe != null && !buchstabe.isEmpty()) {

                    if (Wort.contains(buchstabe)) {
                        output.println("Der Buchstabe " + buchstabe + " befindet sich an " + Wort.indexOf(buchstabe) + " Stelle!");
                        laenge++;
                        while (tempi >= Wort.length()) {
                            tempi++;
                            output.println("-");
                        }
                    } else {
                        output.println("Der Buchstabe " + buchstabe + " befindet sich nicht in dem Wort!");
                        maxguesses--;
                        if (maxguesses == 0)
                            laenge = Wort.length();
                        else
                            output.println("Du hast 1 Leben verloren und dadurch nurnoch" + maxguesses);
                    }
                    tempi = 0;
                }
            }
                if (maxguesses == 0)
                    output.println("Du hast leider Verloren! Das Wort war " + Wort);
                output.println("Gratulation du hast das Wort herausgefunden! Es war " + Wort);

            input.close();
            output.close();
            socket.close();
        }

    }
}

