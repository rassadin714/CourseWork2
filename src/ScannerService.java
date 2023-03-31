import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ScannerService {
    private static final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void createNewTask(){
        System.out.println("Придумайте название для задачи");
        String title = createText();

        System.out.println("Кратко опишите задачу");
        String description = createText();

        System.out.println("Выберите тип задачи:");
        Type type = createType();

        System.out.println("В какой день будет выполнена задача?");
        LocalDate date = createDate();

        System.out.println("В какое время будет выполнена задача?");
        LocalTime time = createTime();

        System.out.println("Укажите повторяемость задачи");
        Task task = chooseRepeatability(title, description, type, date, time);

        TaskService.addTask(task);
        printTask(task);

    }
    public static String createText(){
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        if (title.equals("")||title.equals(" ")){
            try {
                throw new IncorrectArgumentException("Данные введены некорректно");
            } catch (IncorrectArgumentException e) {
                System.err.println(e.getArgument());
                printChoiceRepeatOrExit();
                int choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        System.out.println("Введите текст");
                        return createText();
                    case 2:
                    default:
                        System.out.println("Вы вышли из программы");
                        System.exit(1);
                        break;
                }
            }
        }
        return title;
    }
    public static LocalDate createDate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите дату в формате дд.мм.гггг (Например, 04.04.2023)");
        String date = scanner.next();
        try {
            return LocalDate.parse(date, formatterDate);
        } catch (DateTimeParseException e){
            printIncorrect();
            printChoiceRepeatOrExit();
            int c = scanner.nextInt();
            switch (c) {
                case 1:
                    return createDate();
                case 2:
                default:
                    System.out.println("Вы вышли из программы");
                    System.exit(1);
                    break;
            }
        }
        return LocalDate.parse("01.01.2000", formatterDate);
    }
    public static LocalTime createTime() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите время в формате чч:мм (Например, 17:30)");
        String time = scanner.next();
        try {
            return LocalTime.parse(time, formatterTime);
        } catch (DateTimeParseException e) {
            printIncorrect();
            printChoiceRepeatOrExit();
            int c = scanner.nextInt();
            switch (c) {
                case 1:
                    return createTime();
                case 2:
                default:
                    System.out.println("Вы вышли из программы");
                    System.exit(1);
                    break;
            }
            return LocalTime.parse("00:00",formatterTime);
        }
    }
    public static LocalDateTime createLocalDateTime(LocalDate date, LocalTime time){
        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        return localDateTime;
    }

    public static Type createType(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите: \n 1 - если задача рабочая \n 2 - если задача личная");
        int typeOfTask = scanner.nextInt();
        Type type = Type.WORK;
        switch (typeOfTask){
            case 1:
                break;
            case 2:
                type = Type.PERSONAL;
                break;
            default:
                try {
                    throw new IncorrectArgumentException("Данные введены некорректно");
                } catch (IncorrectArgumentException e) {
                    System.err.println(e.getArgument());
                    printChoiceRepeatOrExit();
                    int c = scanner.nextInt();
                    switch (c) {
                        case 1:
                            return createType();
                        case 2:
                        default:
                            System.out.println("Вы вышли из программы");
                            System.exit(1);
                            break;
                    }
                }
        }
        return type;
    }

    public static Task chooseRepeatability(String title, String description, Type type, LocalDate date, LocalTime time){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите \n 1 - разовая\n 2 - ежедневная\n 3 - еженедельная\n 4 - ежемесячная\n 5 - ежегодная");
        int repeatability = scanner.nextInt();
        Task task = new OneTimeTask(title, description, type, createLocalDateTime(date,time));

        switch (repeatability){
            case 1:
                break;
            case 2:
                task = new DailyTask(title, description, type, createLocalDateTime(date,time));
                break;
            case 3:
                task = new WeeklyTask(title, description, type, createLocalDateTime(date,time));
                break;
            case 4:
                task = new MonthlyTask(title, description, type, createLocalDateTime(date,time));
                break;
            case 5:
                task = new YearlyTask(title, description, type, createLocalDateTime(date,time));
                break;
            default:
                try {
                    throw new IncorrectArgumentException("Данные введены некорректно");
                } catch (IncorrectArgumentException e) {
                    System.err.println(e.getArgument());
                    printChoiceRepeatOrExit();
                    int c = scanner.nextInt();
                    switch (c) {
                        case 1:
                            return chooseRepeatability(title,description, type, date, time);
                        case 2:
                        default:
                            System.out.println("Вы вышли из программы");
                            System.exit(1);
                            break;
                    }
                }
        }
        return task;
    }

    public static void printTask(Task task){
        System.out.println("Задача создана и добавлена в календарь\n" + task);
    }

    public static void printIncorrect(){
        System.err.println("Данные введены некорректно");
    }

    public static void printChoiceRepeatOrExit(){
        System.out.println("Введите\n 1 - попробовать еще раз\n 2- выход из программы");
    }

    public static void editTask() {
        System.out.println("Редактирование задачи");
        Task task = foundTaskScanner();
        editTitleOrDescription(task);

    }
    public static void editTitleOrDescription(Task task){
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Что будем редактировать?\nВведите:\n 1 - заголовок\n 2 - описание");
        int choice = scanner1.nextInt();
        switch (choice){
            case 1:
                System.out.println("Введите новый заголовок");
                String newTitle = createText();
                System.out.println("Задача успешно изменена\n"+TaskService.updateTitle(task,newTitle));
                break;
            case 2:
                System.out.println("Введите новое описание");
                String newDescription = createText();
                System.out.println("Задача успешно изменена\n"+TaskService.updateDescription(task,newDescription));
                break;
            default:
                printIncorrect();
                printChoiceRepeatOrExit();
                int c = scanner1.nextInt();
                switch (c){
                    case 1:
                        editTitleOrDescription(task);
                        break;
                    default:
                        System.exit(1);
                }
        }
    }
    public static Task foundTaskScanner(){
        Scanner scanner = new Scanner(System.in);
        LocalDateTime dateTime = createLocalDateTime(LocalDate.parse("11.11.2001",formatterDate),LocalTime.parse("00:00", formatterTime));
        Task task = new OneTimeTask("Задача", "Описание", Type.PERSONAL, dateTime);
        System.out.println("Введите id задачи");
        int id = scanner.nextInt();
        try {
            task = TaskService.foundTaskId(id);
        } catch (TaskNotFoundException e) {
            System.err.println("Задача не найдена");
            printChoiceRepeatOrExit();
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    return foundTaskScanner();
                case 2:
                default:
                    System.out.println("Вы вышли из программы");
                    System.exit(1);
            }
        }
        return task;
    }
}
