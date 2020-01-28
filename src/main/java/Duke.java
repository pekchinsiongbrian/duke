import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

public class Duke {

    private static final String HORIZONTAL_LINE = "\t____________________________________________________________";
    private static final String EVENT_USE = "Use: 1) event <desc> /at <YYYY-MM-DD of event>" +
            "\n2) event <desc> /at <YYYY-MM-DD of event> <start HH:mm>" +
            "\n3) event <desc> /at <YYYY-MM-DD of event> <start HH:mm> to <end HH:mm>" +
            "\nNote: Input time in 24h format.";
    private static final String DEADLINE_USE = "Use: 1) deadline <task> /by <YYYY-MM-DD of deadline>" +
            "\n2) deadline <task> /by <YYYY-MM-DD of deadline> <HH:mm>" +
            "\nNote: Input time in 24h format.";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        textFormatAndPrint("Hello! I'm Duke\n\tWhat can I do for you?");

        ArrayList<Task> list = new ArrayList<>();
        boolean continueLoop = true;
        boolean toggleTimer = false;

        while (continueLoop) {
            System.out.println();
            String userInput = sc.nextLine();
            String[] userInputSplit = userInput.toLowerCase().split(" ");
            String firstWord = userInputSplit[0].toUpperCase();

            try {
                switch(firstWord) {
                    case "/HELP":
                        System.out.println("You called for help? Help is here! List of commands:" +
                                "\ntodo, deadline, event, done, delete, list, bye");
                        break;
                    case "TODO":
                        if (userInputSplit.length == 1) {
                            throw new DukeException("Use: todo <description>");
                        } else {
                            Task toAdd = createNewTask(userInput, list);
                            if (toAdd != null) {
                                list.add(toAdd);
                                save(list);
                            }
                            break;
                        }
                    case "DEADLINE":
                        if (userInputSplit.length == 1) {
                            throw new DukeException(DEADLINE_USE);
                        } else if (!userInput.contains("/by")) {
                            throw new DukeException("Please use the '/by' keyword to specify a deadline.\n" +
                                    DEADLINE_USE);
                        } else if (userInputSplit[1].equals("/by")) {
                            throw new DukeException("Please enter a task.\n" + DEADLINE_USE);
                        } else if (Arrays.asList(userInputSplit).indexOf("/by") ==
                                Arrays.asList(userInputSplit).size() - 1) {
                            throw new DukeException("Please enter a deadline for the task.\n" + DEADLINE_USE);
                        } else if (Arrays.asList(userInputSplit).indexOf("/by") !=
                                Arrays.asList(userInputSplit).lastIndexOf("/by")) {
                            throw new DukeException("Only one '/by' keyword can be used!\n" + DEADLINE_USE);
                        } else {
                            Task toAdd = createNewTask(userInput, list);
                            if (toAdd != null) {
                                list.add(toAdd);
                                save(list);
                            }
                            break;
                        }
                    case "EVENT":
                        if (userInputSplit.length == 1) {
                            throw new DukeException(EVENT_USE);
                        } else if (!userInput.contains("/at")) {
                            throw new DukeException("Please use the '/at' keyword to specify the event date/time." +
                                    "\n" + EVENT_USE);
                        } else if (userInputSplit[1].equals("/at")) {
                            throw new DukeException("The description of an event cannot be empty.\n" + EVENT_USE);
                        } else if (Arrays.asList(userInputSplit).indexOf("/at") ==
                                Arrays.asList(userInputSplit).size() - 1) {
                            throw new DukeException("Please enter the event date/time.\n" + EVENT_USE);
                        } else if (Arrays.asList(userInputSplit).indexOf("/at") !=
                                Arrays.asList(userInputSplit).lastIndexOf("/at")) {
                            throw new DukeException("Only one '/at' keyword can be used!\n" + EVENT_USE);
                        } else {
                            Task toAdd = createNewTask(userInput, list);
                            if (toAdd != null) {
                                list.add(toAdd);
                                save(list);
                            }
                            break;
                        }
                    case "DONE":
                        if (userInputSplit.length != 2) {
                            throw new DukeException("Enter index of item to mark it as done. " +
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
                        if (userInputSplit.length > 2) {
                            throw new DukeException("Use: 1) list (displays list with current settings)" +
                                    "\n2) list /showtimer (displays list with timer toggled on)" +
                                    "\n3) list /hidetimer (displays list with timer toggled off)");
                        } else {
                            if (list.isEmpty()) {
                                if (userInputSplit.length == 1) {
                                    String toPrint = "Here are the tasks in your list:\n\n\tYou have no tasks " +
                                            "right now.\n\tUse 'todo', 'deadline', or 'event' to add task!";
                                    textFormatAndPrint(toPrint);
                                } else {
                                    if (userInputSplit[1].equals("/showtimer")) {
                                        toggleTimer = true;
                                    } else if (userInputSplit[1].equals("/hidetimer")) {
                                        toggleTimer = false;
                                    } else {
                                        throw new DukeException("Use: 1) list (displays list with current settings)" +
                                                "\n2) list /showtimer (displays list with timer toggled on)" +
                                                "\n3) list /hidetimer (displays list with timer toggled off)");
                                    }
                                    String toPrint = "Here are the tasks in your list:\n\n\tYou have no tasks " +
                                            "right now.\n\tUse 'todo', 'deadline', or 'event' to add task!";
                                    textFormatAndPrint(toPrint);
                                }
                            } else {
                                if (userInputSplit.length == 1) {
                                    if (toggleTimer) {
                                        listPrinterTimerOn(list);
                                    } else {
                                        listPrinterTimerOff(list);
                                    }
                                } else {
                                    if (userInputSplit[1].equals("/showtimer")) {
                                        toggleTimer = true;
                                        listPrinterTimerOn(list);
                                    } else if (userInputSplit[1].equals("/hidetimer")) {
                                        toggleTimer = false;
                                        listPrinterTimerOff(list);
                                    } else {
                                        throw new DukeException("Use: 1) list (displays list with current settings)" +
                                                "\n2) list /showtimer (displays list with timer toggled on)" +
                                                "\n3) list /hidetimer (displays list with timer toggled off)");
                                    }
                                }
                            }
                            break;
                        }
                    case "BYE":
                        String toPrint = "Goodbye! Hope to see you again soon!";
                        textFormatAndPrint(toPrint);
                        sc.close();
                        continueLoop = false;
                        break;
                    default:
                        throw new DukeException("Invalid command!\nFor list of commands, type: /help");
                }
            } catch (DukeException de) {
                System.err.println(de.getMessage());
            }
        }
    }

    public static Task createNewTask(String userInput, ArrayList<Task> list) {
        Task t = null;
        String taskType = userInput.split(" ")[0].toUpperCase();
        String description = userInput.substring(taskType.length() + 1);
        try {
            switch (taskType) {
                case "TODO":
                    t = new Todo(description);
                    break;
                case "DEADLINE":
                    String by = userInput.substring(userInput.indexOf("/by") + 4);
                    String[] byComponents = by.split(" ");
                    if (byComponents.length == 1) {
                        // Case: deadline <task> /by <YYYY-MM-DD of deadline>
                        LocalDate deadlineDate = LocalDate.parse(byComponents[0]);
                        t = new Deadline(description.substring(0, description.indexOf("/by") - 1), deadlineDate);
                        break;
                    } else if (byComponents.length == 2) {
                        // Case: deadline <task> /by <YYYY-MM-DD of deadline> <HH:mm>
                        LocalDate deadlineDate = LocalDate.parse(byComponents[0]);
                        LocalTime deadlineTime = LocalTime.parse(byComponents[1]);
                        t = new Deadline(description.substring(0, description.indexOf("/by") - 1),
                                deadlineDate, deadlineTime);
                        break;
                    } else {
                        throw new DukeException(DEADLINE_USE);
                    }
                case "EVENT":
                    String at = userInput.substring(userInput.indexOf("/at") + 4);
                    String[] atComponents = at.split(" ");
                    if (atComponents.length == 1) {
                        // Case: event <desc> /at <YYYY-MM-DD of event>
                        LocalDate eventDate = LocalDate.parse(at);
                        t = new Event(description.substring(0, description.indexOf("/at") - 1), eventDate);
                        break;
                    } else if (atComponents.length == 2) {
                        // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm>
                        LocalDate eventDate = LocalDate.parse(atComponents[0]);
                        LocalTime eventTime = LocalTime.parse(atComponents[1]);
                        t = new Event(description.substring(0, description.indexOf("/at") - 1),
                                eventDate, eventTime);
                        break;
                    } else if (atComponents.length == 4) {
                        // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm> to <end HH:mm>
                        if (!atComponents[2].toLowerCase().equals("to")) {
                            // Check if the 'to' keyword is used
                            throw new DukeException(EVENT_USE);
                        } else {
                            LocalDate eventDate = LocalDate.parse(atComponents[0]);
                            LocalTime eventTimeStart = LocalTime.parse(atComponents[1]);
                            LocalTime eventTimeEnd = LocalTime.parse(atComponents[3]);
                            t = new Event(description.substring(0, description.indexOf("/at") - 1),
                                    eventDate, eventTimeStart, eventTimeEnd);
                            break;
                        }
                    } else {
                        throw new DukeException(EVENT_USE);
                    }
                default:
                    t = null;
            }
            assert t != null : "Attempted to initiate null task";
            String toPrint = "Got it. I've added this task:\n\t\t" + t.toString() + "\n\tNow you have " +
                    (list.size() + 1) + " tasks in the list.";
            textFormatAndPrint(toPrint);
            return t;
        } catch (DateTimeParseException dtpe) {
            System.err.println("Error reading date and/or time." +
                    "\nPlease enter date in the format: YYYY-MM-DD (with dashes)," +
                    "\nand time (if applicable) in the format: HH:mm (with colon).");
        } catch (DukeException de) {
            System.err.println(de.getMessage());
        }
        return t;
    }

    public static void textFormatAndPrint(String text) {
        System.out.println(HORIZONTAL_LINE + "\n\t" + text + "\n" + HORIZONTAL_LINE);
    }

    public static void save(ArrayList<Task> list) {
        String home = System.getProperty("user.home");

        File f = new File(home + "/Downloads/Y2S2/CS2103T - Software Engineering/duke-master/task-list.txt");
        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            for (int i = 0; i < list.size(); i++) {
                String toWrite = (i + 1) + ". " + list.get(i) + "\n";
                outputStream.write(toWrite.getBytes());
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            System.err.println("Failed to save list");
        }
    }

    public static void listPrinterTimerOff(ArrayList<Task> list) {
        System.out.println(HORIZONTAL_LINE + "\n\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t" + (i + 1) + "." + list.get(i));
        }
        System.out.println("\n\tTip: Try using 'list /showtimer' or list '/hidetimer'!\n" + HORIZONTAL_LINE);
    }

    public static void listPrinterTimerOn(ArrayList<Task> list) {
        System.out.println(HORIZONTAL_LINE + "\n\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof Deadline) {
                System.out.println("\t" + (i + 1) + "." + ((Deadline) list.get(i)).displayDeadline());
            } else if (list.get(i) instanceof Event) {
                System.out.println("\t" + (i + 1) + "." + ((Event) list.get(i)).displayEventTime());
            } else {
                System.out.println("\t" + (i + 1) + "." + list.get(i));
            }
        }
        System.out.println("\n\tTip: Try using 'list /showtimer' or list '/hidetimer'!\n" + HORIZONTAL_LINE);
    }
}