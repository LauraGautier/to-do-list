package org.example;

import org.example.models.Task;
import org.example.models.User;
import org.example.models.DatedTask;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Votre prénom : ");
        String name = scanner.nextLine();
        User user = new User(name);

        while (true) {
            System.out.println("\n=== TODO LIST ===");
            System.out.println("1. Lister les tâches");
            System.out.println("2. Créer une tâche");
            System.out.println("3. Modifier une tâche");
            System.out.println("4. Supprimer une tâche");
            System.out.println("5. Quitter");
            System.out.print("Votre choix : ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: listerTaches(); break;
                case 2: creerTache(user); break;
                case 3: modifierTache(); break;
                case 4: supprimerTache(); break;
                case 5: return;
            }
        }
    }

    private static void listerTaches() {
        if (tasks.isEmpty()) {
            System.out.println("Aucune tâche");
            return;
        }
        for (Task task : tasks) {
            String status = task.isDone() ? "✓" : "✗";
            String dateInfo = "";
            if (task instanceof DatedTask) {
                DatedTask datedTask = (DatedTask) task;
                dateInfo = " (Échéance: " + datedTask.getDueDate().toString() + ")";
            }
            System.out.println(task.getId().toString().substring(0, 8) + "... " + status + " " + task.getTitle() + dateInfo);
        }
    }

    private static void creerTache(User user) {
        System.out.print("Titre : ");
        String title = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("Avec échéance ? : ");
        String withDate = scanner.nextLine();

        if (withDate.equalsIgnoreCase("o")) {
            System.out.println("Création d'une tâche avec échéance (date actuelle)");
            Date date = new Date();
            tasks.add(new DatedTask(title, description, user, date));
        } else {
            tasks.add(new Task(title, description, user));
        }
        System.out.println("Tâche créée !");
    }

    private static void modifierTache() {
        listerTaches();
        System.out.print("ID de la tâche à modifier : ");
        String idStr = scanner.nextLine();

        Task task = trouverTache(idStr);
        if (task == null) {
            System.out.println("Tâche introuvable");
            return;
        }

        System.out.print("Nouveau titre (actuel: " + task.getTitle() + ") : ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) task.setTitle(title);

        System.out.print("Terminée ? (o/n) : ");
        String done = scanner.nextLine();
        if (done.equalsIgnoreCase("o")) task.setDone(true);
        else if (done.equalsIgnoreCase("n")) task.setDone(false);

        System.out.println("Tâche modifiée !");
    }

    private static void supprimerTache() {
        listerTaches();
        System.out.print("ID de la tâche à supprimer : ");
        String idStr = scanner.nextLine();

        Task task = trouverTache(idStr);
        if (task != null) {
            tasks.remove(task);
            System.out.println("Tâche supprimée !");
        } else {
            System.out.println("Tâche introuvable");
        }
    }

    private static Task trouverTache(String idStr) {
        for (Task task : tasks) {
            if (task.getId().toString().startsWith(idStr)) return task;
        }
        return null;
    }
}