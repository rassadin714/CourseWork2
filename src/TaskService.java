import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    private static final Map<Integer, Task> taskMap = new HashMap<>();
    private static final Collection<Task> removedTask = new ArrayList<>();

    public static void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public static void printMap() {
        System.out.println(taskMap);
    }
    public static Task foundTaskId(int id) throws TaskNotFoundException {
        Task task = taskMap.entrySet().stream()
                .filter(num -> num.getKey().equals(id))
                .map(t -> t.getValue())
                .findFirst()
                .orElseThrow(TaskNotFoundException::new);
        return task;
    }
    public static Task updateTitle(Task task, String newTitle) {
        task.setTitle(newTitle);
        return task;
    }
    public static Task updateDescription(Task task, String newDescription) {
        task.setDescription(newDescription);
        return task;
    }

    public static void removeTask(){
        System.out.println("Удаление задачи в архив");
        Task task = ScannerService.foundTaskScanner();
        removedTask.add(task);
        taskMap.remove(task.getId());
        System.out.println("Задача " + task.getId()+ " перенесена в архив");
    }
    public static void getAllByDate() {
        System.out.println("Ищем задачи на день");
        LocalDate date = ScannerService.createDate();
        Collection<Task> allByDate = null;
        try {
            allByDate = foundAllByDate(date);
        } catch (TaskNotFoundException e) {
            System.err.println("Задачи не найдены");
            ScannerService.printChoiceRepeatOrExit();
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    getAllByDate();
                    break;
                case 2:
                default:
                    System.out.println("Вы вышли из программы");
                    System.exit(1);
            }
        }
        System.out.println("Задачи на день " + date);
        printAllByDate(allByDate);

    }
    public static Collection<Task> foundAllByDate (LocalDate date) throws TaskNotFoundException {
        Collection<Task> tasksByDate = taskMap.entrySet().stream()
                .filter(t -> t.getValue().appearsIn(date))
                .map(t -> t.getValue())
                .sorted(Comparator.comparing(Task::getDateTime))
                .collect(Collectors.toList());
        if(tasksByDate.isEmpty()){
            throw new TaskNotFoundException();
        }
        return tasksByDate;
    }
    public static void printAllByDate(Collection<Task> tasks){
        tasks.stream()
                .forEach(t-> System.out.println("Задача: " + t.getTitle() + ". Описание: " + t.getDescription() + ". ID: " + t.getId() + ". " + t.getType() + ". Время: " + LocalTime.from(t.getDateTime())));
    }

    public static Collection<Task> getRemovedTasks(){
        return removedTask;
    }

    public static void printRemovedTasks(){
        if (removedTask.isEmpty()){
            System.out.println("В архиве нет задач\n");
        } else {
            System.out.println("Задачи в архиве");
            removedTask.stream()
                    .forEach(System.out::println);
        }
    }

    public static Map<LocalDate, Collection<Task>> getAllGroupByDate(){
        Map<LocalDate, Collection<Task>> mapAllGroupByDate = taskMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .sorted(Comparator.comparing(Task::getDateTime))
                .collect(Collectors.groupingBy(t -> LocalDate.from(t.getDateTime()), Collectors.toCollection(ArrayList::new)));
        return mapAllGroupByDate;
    }

    public static void printAllGroupByDate(){
        Map<LocalDate, Collection<Task>> mapAllGroupByDate = getAllGroupByDate();
        if(mapAllGroupByDate.isEmpty()){
            System.out.println("В календаре нет задач\n");
        }else {
            System.out.println("Задачи сгруппированы по датам");
            mapAllGroupByDate.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(t-> System.out.println("Дата " + t.getKey() + "\n" + t.getValue()));
        }
    }
}
