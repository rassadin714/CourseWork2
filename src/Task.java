import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

public abstract class Task {
    private int idGenerator = new Random().nextInt(500);
    private String title;
    private final Type type;
    private final int id;
    private final LocalDateTime dateTime;
    private String description;

    public Task(String title, Type type, LocalDateTime dateTime, String description) {
        this.title = title;
        this.type = type;
        this.dateTime = dateTime;
        this.description = description;
        this.id = idGenerator;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id == task.id && Objects.equals(title, task.title) && type == task.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, type, id);
    }

    public String toString() {
        String formatDate = dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyy HH:mm"));
        return "Задача: " + title + ". Описание: " + description + ". ID: " + id + ". " + type + ". Дата: " + formatDate;
    }

    public abstract boolean appearsIn(LocalDate localDate);

}
