import java.util.Scanner;

public class Duke {

    private static final String HORIZONTAL_LINE = "\t____________________________________________________________";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        textFormatAndPrint("Hello! I'm Duke\n\tWhat can I do for you?");

        Task[] list = new Task[100];
        int indexToAdd = 0;

        while (true) {
            System.out.println();
            String userInput = sc.nextLine();
            String firstWord = userInput.split(" ")[0];
            if (userInput.equals("bye")) {
                String toPrint = "Bye. Hope to see you again soon!";
                textFormatAndPrint(toPrint);
                sc.close();
                break;
            } else if (userInput.equals("list")) {
                System.out.println(HORIZONTAL_LINE + "\n\tHere are the tasks in your list:");
                for (int i = 0; i < list.length; i++) {
                    if (list[i] == null) {
                        break;
                    } else {
                        System.out.println("\t" + (i + 1) + "." + list[i]);
                    }
                }
                System.out.println(HORIZONTAL_LINE);
            } else if (firstWord.equals("done")){
                if (userInput.split(" ").length != 2) {
                    list[indexToAdd] = createNewTask(userInput,indexToAdd);
                    indexToAdd += 1;
                } else {
                    try {
                        if (Integer.parseInt(userInput.split(" ")[1]) <= 0) {
                            throw new Exception();
                        } else {
                            Task t = list[Integer.parseInt(userInput.split(" ")[1]) - 1];
                            t.markAsDone();
                            String toPrint = "Nice! I've marked this task as done:\n\t  " + t.toString();
                            textFormatAndPrint(toPrint);
                        }
                    } catch (Exception e) {
                        list[indexToAdd] = createNewTask(userInput, indexToAdd);
                        indexToAdd += 1;
                    }
                }
            } else if ((firstWord.equals("todo") || firstWord.equals("deadline")) || firstWord.equals("event")) {
                list[indexToAdd] = createNewTask(userInput, indexToAdd);
                indexToAdd += 1;
            } else {
                list[indexToAdd] = createNewTask(userInput, indexToAdd);
                indexToAdd += 1;
            }
        }
    }

    public static Task createNewTask(String userInput, int numOfTasks) {
        Task t;
        String taskType = userInput.split(" ")[0];
        String description = userInput.substring(taskType.length() + 1);
        if (taskType.equals("todo")) {
            t = new Todo(description);
        } else if (taskType.equals("deadline")) {
            String by = userInput.substring(userInput.indexOf("/by") + 4);
            t = new Deadline(description.substring(0, description.indexOf("/by") - 1), by);
        } else if (taskType.equals("event")) {
            String at = userInput.substring(userInput.indexOf("/at") + 4);
            t = new Event(description.substring(0, description.indexOf("/at") - 1), at);
        } else {
            t = new Task("ERROR");
        }
        String toPrint = "Got it. I've added this task:\n\t\t" + t.toString() + "\n\tNow you have " + (numOfTasks + 1) +
                " tasks in the list.";
        textFormatAndPrint(toPrint);
        return t;
    }

    public static void textFormatAndPrint(String text) {
        System.out.println(HORIZONTAL_LINE + "\n\t" + text + "\n" + HORIZONTAL_LINE);
    }
}