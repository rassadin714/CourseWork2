import java.time.LocalDate;
import java.time.LocalDateTime;

public class OneTimeTask extends Task{

    public OneTimeTask(String title, String description, Type type, LocalDateTime dateTime) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        LocalDate date = LocalDate.from(getDateTime());
        return localDate.isEqual(date);
    }
    @Override
    public String toString() {
        return super.toString() + ". Повторяемость - разовая";
    }
}
