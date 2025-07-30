package org.example;

import org.example.builders.TaskBuilder;
import org.example.database.DatabaseAccess;
import org.example.exceptions.ElementNotFoundException;
import org.example.models.Task;
import org.example.models.User;
import org.example.models.DatedTask;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static DatabaseAccess database = DatabaseAccess.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Votre prénom : ");
        String name = scanner.nextLine();

        User user;
        try {
            user = database.findUserByFirstName(name);
            System.out.println("Bienvenue " + user.getFirstName() + " !");
        } catch (ElementNotFoundException e) {
            user = new User(name);
            database.addUser(user);
            System.out.println("Nouvel utilisateur créé : " + user.getFirstName());
        }

        while (true) {
            System.out.println("\nTODO List");
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
                default: System.out.println("Choix invalide !");
            }
        }
    }

    private static void listerTaches() {
        List<Task> tasks = database.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("Aucune tâche");
            return;
        }

        System.out.println("\nListe des tâches");
        for (Task task : tasks) {
            String status = task.isDone() ? "✓" : "✗";
            String dateInfo = "";

            if (task instanceof DatedTask) {
                DatedTask datedTask = (DatedTask) task;
                dateInfo = " (Échéance: " + datedTask.getDueDate().toString() + ")";
            }

            System.out.println(task.getId().toString().substring(0, 8) + "... " +
                    status + " " + task.getTitle() +
                    " [" + task.getUser().getFirstName() + "]" + dateInfo);
        }
    }

    private static void creerTache(User user) {
        System.out.print("Titre : ");
        String title = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.print("Avec échéance ? (o/n) : ");
        String withDate = scanner.nextLine();

        try {
            TaskBuilder builder = TaskBuilder.withTitle(title)
                    .description(description)
                    .assignedTo(user);

            if (withDate.equalsIgnoreCase("o")) {
                System.out.print("Dans combien de jours ? (0 pour aujourd'hui) : ");
                int days = scanner.nextInt();
                scanner.nextLine();

                if (days == 0) {
                    builder.withCurrentDateAsDueDate();
                } else {
                    builder.dueDateInDays(days);
                }
            }

            Task newTask = builder.build();
            database.addTask(newTask);

            System.out.println("Tâche créée avec succès !");

        } catch (IllegalStateException e) {
            System.out.println("Erreur lors de la création : " + e.getMessage());
        }
    }

    private static void modifierTache() {
        listerTaches();
        System.out.print("ID de la tâche à modifier : ");
        String idStr = scanner.nextLine();

        try {
            Task task = database.findTaskByIdPrefix(idStr);

            System.out.print("Nouveau titre (actuel: " + task.getTitle() + ") : ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) {
                task.setTitle(title);
            }

            System.out.print("Nouvelle description (actuelle: " + task.getDescription() + ") : ");
            String description = scanner.nextLine();
            if (!description.isEmpty()) {
                task.setDescription(description);
            }

            System.out.print("Terminée ? (o/n) : ");
            String done = scanner.nextLine();
            if (done.equalsIgnoreCase("o")) {
                task.setDone(true);
            } else if (done.equalsIgnoreCase("n")) {
                task.setDone(false);
            }

            database.updateTask(task);
            System.out.println("Tâche modifiée avec succès !");

        } catch (ElementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void supprimerTache() {
        listerTaches();
        System.out.print("ID de la tâche à supprimer : ");
        String idStr = scanner.nextLine();

        try {
            database.removeTaskByIdPrefix(idStr);
            System.out.println("Tâche supprimée avec succès !");
        } catch (ElementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}