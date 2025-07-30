package org.example.server;

import org.example.database.DatabaseAccess;
import org.example.models.Task;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HttpServer {

    private static final int PORT = 8080;
    private DatabaseAccess database;

    public HttpServer() {
        this.database = DatabaseAccess.getInstance();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion reçue");

                InputStream inputStream = clientSocket.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String requestLine = bufferedReader.readLine();
                System.out.println("Requête : " + requestLine);

                String line;
                while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                }

                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/plain; charset=utf-8");
                writer.println();

                List<Task> tasks = database.getAllTasks();
                writer.println("To Do List");
                writer.println("Nombre de tâches : " + tasks.size());

                for (Task task : tasks) {
                    String status = task.isDone() ? "✓" : "✗";
                    writer.println(status + " " + task.getTitle() + " (" + task.getUser().getFirstName() + ")");
                }

                writer.flush();

                clientSocket.close();
            }

        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}