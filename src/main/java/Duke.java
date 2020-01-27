import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    private static final String HORIZONTAL_LINE = "\t____________________________________________________________";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        textFormatAndPrint("Hello! I'm Duke\n\tWhat can I do for you?");

        ArrayList<Task> list = new ArrayList<>();
        boolean continueLoop = true;

        while (continueLoop) {
            System.out.println();
            String userInput = sc.nextLine();
            String[] userInputSplit = userInput.split(" ");
            String firstWord = userInputSplit[0].toUpperCase();

            try {
                switch(firstWord) {
                    case "TODO":
                        if (userInputSplit.length == 1) {
                            throw new DukeException("Use: todo <description>");
                        } else {
                            list.add(createNewTask(userInput, list));
                            save(list);
                            break;
                        }
                    case "DEADLINE":
                        if (userInputSplit.length == 1) {
                            throw new DukeException("Use: deadline <task> /by <deadline>");
                        } else if (!userInput.contains("/by")) {
                            throw new DukeException("Please use the '/by' keyword to specify a deadline." +
                                    "\nUse: deadline <task> /by <deadline>");
                        } else if (userInputSplit[1].equals("/by")) {
                            throw new DukeException("Please enter a task.\nUse: deadline <task> /by <deadline>");
                        } else if (Arrays.asList(userInputSplit).indexOf("/by") ==
                                Arrays.asList(userInputSplit).size() - 1) {
                            throw new DukeException("Please enter a deadline for the task." +
                                    "\nUse: deadline <task> /by <deadline>");
                        } else if (Arrays.asList(userInputSplit).indexOf("/by") !=
                                Arrays.asList(userInputSplit).lastIndexOf("/by")) {
                            throw new DukeException("Only one '/by' keyword can be used!" +
                                    "\nUse: deadline <task> /by <deadline>");
                        } else {
                            list.add(createNewTask(userInput, list));
                            save(list);
                            break;
                        }
                    case "EVENT":
                        if (userInputSplit.length == 1) {
                            throw new DukeException("Use: deadline <task> /by <deadline>");
                        } else if (!userInput.contains("/at")) {
                            throw new DukeException("Please use the '/at' keyword to specify the event day/date/time." +
                                    "\nUse: event <desc> /at <day/date/time>");
                        } else if (userInputSplit[1].equals("/at")) {
                            throw new DukeException("The description of an event cannot be empty." +
                                    "\nUse: event <desc> /at <day/date/time>");
                        } else if (Arrays.asList(userInputSplit).indexOf("/at") ==
                                Arrays.asList(userInputSplit).size() - 1) {
                            throw new DukeException("Please enter the event day/date/time." +
                                    "\nUse: event <desc> /at <day/date/time>");
                        } else if (Arrays.asList(userInputSplit).indexOf("/at") !=
                                Arrays.asList(userInputSplit).lastIndexOf("/at")) {
                            throw new DukeException("Only one '/at' keyword can be used!" +
                                    "\nUse: event <desc> /at <day/date/time>");
                        } else {
                            list.add(createNewTask(userInput, list));
                            save(list);
                            break;
                        }
                    case "DONE":
                        if (userInputSplit.length != 2) {
                            throw new DukeException("Enter index of item to mark it as done." +
                                    "Type 'list' to see all items.\nUse: done 2 (marks 2nd item on the list as done)");
                        } else {
                            try {
                                if (Integer.parseInt(userInputSplit[1]) <= 0) {
                                    throw new DukeException("Parameter must be a positive integer." +
                                            "\nUse: done 2 (marks 2nd item on the list as done)");
                                } else if (Integer.parseInt(userInputSplit[1]) > list.size()) {
                                    throw new DukeException("Parameter exceeds number of items on the list!");
                                } else {
                                    Task t = list.get(Integer.parseInt(userInputSplit[1]) - 1);
                                    t.markAsDone();
                                    String toPrint = "Nice! I've marked this task as done:\n\t\t" + t.toString();
                                    textFormatAndPrint(toPrint);
                                    save(list);
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                throw new DukeException("Parameter must be a positive integer." +
                                        "\nUse: done 2 (marks 2nd item on the list as done)");
                            }
                        }
                    case "DELETE":
                        if (userInputSplit.length != 2) {
                            throw new DukeException("Enter index of item to delete it. " +
                                    "Type 'list' to see all items.\nUse: delete 2 (deletes 2nd item off the list)");
                        } else {
                            try {
                                if (Integer.parseInt(userInputSplit[1]) <= 0) {
                                    throw new DukeException("Parameter must be a positive integer." +
                                            "\nUse: delete 2 (deletes 2nd item off the list)");
                                } else if (Integer.parseInt(userInputSplit[1]) > list.size()) {
                                    throw new DukeException("Parameter exceeds number of items on the list!");
                                } else {
                                    Task t = list.get(Integer.parseInt(userInputSplit[1]) - 1);
                                    String toPrint = "Noted. I've removed this task:\n\t\t" + t.toString() +
                                            "\n\tNow you have " + (list.size() - 1) + " tasks in the list.";
                                    textFormatAndPrint(toPrint);
                                    list.remove(Integer.parseInt(userInputSplit[1]) - 1);
                                    save(list);
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                throw new DukeException("Parameter must be a positive integer." +
                                        "\nUse: delete 2 (deletes 2nd item off the list)");
                            }
                        }
                    case "LIST":
                        if (userInputSplit.length != 1) {
                            throw new DukeException("'List' has no parameters! Please try again.");
                        } else {
                            System.out.println(HORIZONTAL_LINE + "\n\tHere are the tasks in your list:");
                            if (list.isEmpty()) {
                                System.out.println("\t\tYou have no tasks right now." +
                                        "\n\t\tUse 'todo', 'deadline', or 'event' to add task!");
                            } else {
                                for (int i = 0; i < list.size(); i++) {
                                    System.out.println("\t" + (i + 1) + "." + list.get(i));
                                }
                            }
                            System.out.println(HORIZONTAL_LINE);
                            break;
                        }
                    case "BYE":
                        String toPrint = "Goodbye! Hope to see you again soon!";
                        textFormatAndPrint(toPrint);
                        sc.close();
                        continueLoop = false;
                        break;
                    default:
                        throw new DukeException("Invalid command!");
                }
            } catch (DukeException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static Task createNewTask(String userInput, ArrayList<Task> list) {
        Task t;
        String taskType = userInput.split(" ")[0].toUpperCase();
        String description = userInput.substring(taskType.length() + 1);
        switch(taskType) {
            case "TODO":
                t = new Todo(description);
                break;
            case "DEADLINE":
                String by = userInput.substring(userInput.indexOf("/by") + 4);
                t = new Deadline(description.substring(0, description.indexOf("/by") - 1), by);
                break;
            case "EVENT":
                String at = userInput.substring(userInput.indexOf("/at") + 4);
                t = new Event(description.substring(0, description.indexOf("/at") - 1), at);
                break;
            default:
                t = null;
        }

        assert t != null;
        String toPrint = "Got it. I've added this task:\n\t\t" + t.toString() + "\n\tNow you have " +
                (list.size() + 1) + " tasks in the list.";
        textFormatAndPrint(toPrint);
        return t;
    }

    public static void textFormatAndPrint(String text) {
        System.out.println(HORIZONTAL_LINE + "\n\t" + text + "\n" + HORIZONTAL_LINE);
    }

    public static void save(ArrayList<Task> list) {
        String home = System.getProperty("user.home");

        File f = new File(home + "/Downloads/Y2S2/CS2103T - Software Engineering/duke-master/task-list.txt");
        try {
            OutputStream outputStream = new FileOutputStream(f);
            for (int i = 0; i < list.size(); i++) {
                String toWrite = (i + 1) + ". " + list.get(i) + "\n";
                outputStream.write(toWrite.getBytes());
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.err.println("Failed to save list");
        }
    }
}