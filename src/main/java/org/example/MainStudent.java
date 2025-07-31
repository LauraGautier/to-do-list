package org.example;

import org.example.models.Student;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStudent {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Mel"));
        students.add(new Student("Greg"));
        students.add(new Student("Laura"));
        students.add(new Student("Clément"));
        students.add(new Student("Flo"));
        students.add(new Student("Lucas"));
        students.add(new Student("Seb"));
        students.add(new Student("Sergio"));
        students.add(new Student("Théo"));
        students.add(new Student("German"));

        students.forEach(student -> {
            double mathAvg = student.getMaths().stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            student.setMathAverage(mathAvg);

            double frenchAvg = student.getFrench().stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            student.setFrenchAverage(frenchAvg);

            double historyAvg = student.getHistory().stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0.0);
            student.setHistoryAverage(historyAvg);

            double totalAvg = (mathAvg + frenchAvg + historyAvg) / 3.0;
            student.setTotalAverage(totalAvg);

            System.out.println(student.getName() + " - Maths: " + String.format("%.2f", mathAvg) +
                    ", Français: " + String.format("%.2f", frenchAvg) +
                    ", Histoire: " + String.format("%.2f", historyAvg) +
                    ", Moyenne totale: " + String.format("%.2f", totalAvg));
        });

        System.out.println("\n--- Vérifications avec streams ---");

        boolean allAbove5 = students.stream()
                .allMatch(student -> student.getTotalAverage() >= 5.0);
        System.out.println("Tous les étudiants ont plus de 5 de moyenne : " + allAbove5);

        boolean atLeastOneAbove10 = students.stream()
                .anyMatch(student -> student.getTotalAverage() > 10.0);
        System.out.println("Au moins un étudiant a plus de 10 de moyenne : " + atLeastOneAbove10);

        Student bestStudent = students.stream()
                .max(Comparator.comparing(Student::getTotalAverage))
                .orElse(null);

        if (bestStudent != null) {
            System.out.println("Meilleur étudiant : " + bestStudent.getName() +
                    " avec une moyenne de " + String.format("%.2f", bestStudent.getTotalAverage()));
        }

        double classAverage = students.stream()
                .mapToDouble(Student::getTotalAverage)
                .average()
                .orElse(0.0);
        System.out.println("Moyenne de la classe : " + String.format("%.2f", classAverage));

        Map<String, Double> mathAveragesMap = students.stream()
                .collect(Collectors.toMap(
                        Student::getName,
                        Student::getMathAverage
                ));

        System.out.println("\n--- Map des moyennes de Maths ---");
        mathAveragesMap.forEach((name, mathAvg) ->
                System.out.println(name + " : " + String.format("%.2f", mathAvg)));
    }
}