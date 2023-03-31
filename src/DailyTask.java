import java.time.LocalDate;
import java.time.LocalDateTime;

public class DailyTask extends Task{

    public DailyTask(String title, String description, Type type, LocalDateTime dateTime) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        return true;
    }
    @Override
    public String toString() {
        return super.toString() + ". Повторяемость - ежедневная";
    }
}
