import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class player {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "localhost";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, PORT);
        System.out.println("Connected to server: " + socket.getInetAddress());


        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        Thread responseReader = new Thread(() -> {
            try {
                String response;
                while ((response = input.readLine()) != null) {
                    System.out.println("Server response: " + response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Starten des Threads, der die Antworten liest
        responseReader.start();

        while (true) {
            // Eingabe des Benutzers für einen Befehl
            System.out.println("Gebe einen Buchstaben ein: ");
            String command = scanner.nextLine();
            output.println(command);
        }
        }
    }

