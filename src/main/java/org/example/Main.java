package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("What is your name?");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Hello, " + name);

        String[] tasks = {
                "Préparer la valise pour les vacances",
                "Racheter des croquettes pour Gribouille",
                "Faire le plein de la voiture",
                "Faire quelques courses pour la semaine",
                "Jouer à WoW",
                "Terminer le dossier du projet fil rouge"
        };

        System.out.println("\nVoilà ta petite to do list de la semaine " + name + " !");

        for (String task : tasks) {
            System.out.println(task);
        }

        String[] tasks2 = new String[tasks.length+1];
        System.arraycopy(tasks, 0, tasks2, 0, tasks.length);
        System.out.println("Tu peux ajouter une tâche ci-dessous");
        String task = scanner.nextLine();
        tasks2[tasks2.length-1] = task;
        for (String t : tasks2) {
            System.out.println(t);
        }

        scanner.close();
    }
}