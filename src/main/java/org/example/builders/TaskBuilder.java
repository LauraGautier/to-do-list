package org.example.builders;

import org.example.models.Task;
import org.example.models.DatedTask;
import org.example.models.User;
import java.util.Date;

public class TaskBuilder {

    private String title;
    private String description;
    private User user;
    private boolean done = false;
    private Date dueDate;

    private TaskBuilder() {}

    public static TaskBuilder create() {
        return new TaskBuilder();
    }

    public static TaskBuilder withTitle(String title) {
        return new TaskBuilder().title(title);
    }

    public TaskBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder assignedTo(User user) {
        this.user = user;
        return this;
    }

    public TaskBuilder completed() {
        this.done = true;
        return this;
    }

    public TaskBuilder done(boolean done) {
        this.done = done;
        return this;
    }

    public TaskBuilder withDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public TaskBuilder withCurrentDateAsDueDate() {
        this.dueDate = new Date();
        return this;
    }

    public TaskBuilder dueDateInDays(int days) {
        long currentTime = System.currentTimeMillis();
        long daysInMillis = days * 24L * 60L * 60L * 1000L;
        this.dueDate = new Date(currentTime + daysInMillis);
        return this;
    }

    public Task build() {
        validateRequiredFields();

        if (dueDate != null) {
            DatedTask datedTask = new DatedTask(title, description, user, dueDate);
            datedTask.setDone(done);
            return datedTask;
        } else {
            Task task = new Task(title, description, user);
            task.setDone(done);
            return task;
        }
    }

    private void validateRequiredFields() {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalStateException("Le titre de la tâche est obligatoire");
        }

        if (user == null) {
            throw new IllegalStateException("L'utilisateur assigné à la tâche est obligatoire");
        }

        if (description == null) {
            description = "";
        }
    }

    public TaskBuilder reset() {
        this.title = null;
        this.description = null;
        this.user = null;
        this.done = false;
        this.dueDate = null;
        return this;
    }

    public static TaskBuilder fromExistingTask(Task existingTask) {
        TaskBuilder builder = new TaskBuilder()
                .title(existingTask.getTitle())
                .description(existingTask.getDescription())
                .assignedTo(existingTask.getUser())
                .done(existingTask.isDone());

        if (existingTask instanceof DatedTask) {
            DatedTask datedTask = (DatedTask) existingTask;
            builder.withDueDate(datedTask.getDueDate());
        }

        return builder;
    }
}