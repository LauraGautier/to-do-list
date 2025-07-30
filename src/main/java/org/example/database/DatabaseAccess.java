package org.example.database;

import org.example.exceptions.ElementNotFoundException;
import org.example.models.Task;
import org.example.models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseAccess {

    private static DatabaseAccess instance;

    private List<Task> tasks;
    private List<User> users;

    private DatabaseAccess() {
        this.tasks = new ArrayList<>();
        this.users = new ArrayList<>();
        initializeData();
    }

    public static DatabaseAccess getInstance() {
        if (instance == null) {
            instance = new DatabaseAccess();
        }
        return instance;
    }

    private void initializeData() {
        User defaultUser = new User("Admin");
        User testUser = new User("TestUser");

        users.add(defaultUser);
        users.add(testUser);

        tasks.add(new Task("Tâche d'exemple", "Ceci est une tâche d'exemple", defaultUser));
        tasks.add(new Task("Apprendre Java", "Toute la semaine", testUser));
    }

    public Task findTaskById(UUID id) throws ElementNotFoundException {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException("Tâche avec l'ID " + id + " introuvable"));
    }

    public Task findTaskByIdPrefix(String idPrefix) throws ElementNotFoundException {
        return tasks.stream()
                .filter(task -> task.getId().toString().startsWith(idPrefix))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException("Tâche avec l'ID commençant par " + idPrefix + " introuvable"));
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public List<Task> getTasksByUser(User user) {
        return tasks.stream()
                .filter(task -> task.getUser().getId().equals(user.getId()))
                .toList();
    }

    public Task addTask(Task task) {
        tasks.add(task);
        return task;
    }

    public void removeTaskById(UUID id) throws ElementNotFoundException {
        Task taskToRemove = findTaskById(id);
        tasks.remove(taskToRemove);
    }

    public void removeTaskByIdPrefix(String idPrefix) throws ElementNotFoundException {
        Task taskToRemove = findTaskByIdPrefix(idPrefix);
        tasks.remove(taskToRemove);
    }

    public void updateTask(Task task) throws ElementNotFoundException {
        int index = -1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(task.getId())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new ElementNotFoundException("Impossible de mettre à jour la tâche avec l'ID " + task.getId());
        }

        tasks.set(index, task);
    }

    public User findUserById(UUID id) throws ElementNotFoundException {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException("Utilisateur avec l'ID " + id + " introuvable"));
    }

    public User findUserByFirstName(String firstName) throws ElementNotFoundException {
        return users.stream()
                .filter(user -> user.getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException("Utilisateur avec le prénom " + firstName + " introuvable"));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public void removeUserById(UUID id) throws ElementNotFoundException {
        User userToRemove = findUserById(id);
        users.remove(userToRemove);
    }

    public void updateUser(User user) throws ElementNotFoundException {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new ElementNotFoundException("Impossible de mettre à jour l'utilisateur avec l'ID " + user.getId());
        }

        users.set(index, user);
    }

    public void reset() {
        tasks.clear();
        users.clear();
        initializeData();
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public int getUserCount() {
        return users.size();
    }
}