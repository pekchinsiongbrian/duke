package duke.main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import duke.DukeException;
import duke.command.*;
import duke.task.*;

/**
 * Parses user commands.
 */
public class Parser {

    private final String EVENT_USE = "Use: 1) event <desc> /at <YYYY-MM-DD of event>"
            + "\n2) event <desc> /at <YYYY-MM-DD of event> <start HH:mm>"
            + "\n3) event <desc> /at <YYYY-MM-DD of event> <start HH:mm> to <end HH:mm>"
            + "\nNote: Input time in 24h format.";

    private final String DEADLINE_USE = "Use: 1) deadline <task> /by <YYYY-MM-DD of deadline>"
            + "\n2) deadline <task> /by <YYYY-MM-DD of deadline> <HH:mm>"
            + "\nNote: Input time in 24h format.";

    private final String TODO_USE = "Use: todo <description>";

    private final String DONE_USE = "Use: done <index of item to mark as done>";

    private final String DELETE_USE = "Use: delete <index of item to delete>";

    private final String LIST_USE = "Use: 1) list (displays list with current settings)"
            + "\n2) list /showtimer (displays list with timer toggled on)"
            + "\n3) list /hidetimer (displays list with timer toggled off)";

    /**
     * Checks the validity of the user input and returns the command type.
     *
     * @param userInput User input
     * @param list Task list
     * @return Type of command
     * @throws DukeException If user input (command and/or arguments) is invalid
     */
    public String getCommandType(String userInput, TaskList list) throws DukeException {
        String[] userInputSplit = userInput.toLowerCase().split(" ");
        String firstWord = userInputSplit[0].toUpperCase();
        switch (firstWord) {
        case "/HELP":
            if (userInputSplit.length > 1) {
                throw new DukeException("'/help' command has no arguments!");
            } else {
                return firstWord;
            }
            //Fallthrough
        case "TODO":
            if (userInputSplit.length == 1) {
                throw new DukeException(TODO_USE);
            } else {
                return firstWord;
            }
            //Fallthrough
        case "DEADLINE":
            if (userInputSplit.length == 1) {
                throw new DukeException(DEADLINE_USE);
            } else if (!userInput.toLowerCase().contains("/by")) {
                throw new DukeException("Please use the '/by' keyword to specify a deadline.\n"
                        + DEADLINE_USE);
            } else if (userInputSplit[1].equals("/by")) {
                throw new DukeException("Please enter a task.\n" + DEADLINE_USE);
            } else if (Arrays.asList(userInputSplit).indexOf("/by")
                    == Arrays.asList(userInputSplit).size() - 1) {
                throw new DukeException("Please enter a deadline for the task.\n" + DEADLINE_USE);
            } else if (Arrays.asList(userInputSplit).indexOf("/by")
                    != Arrays.asList(userInputSplit).lastIndexOf("/by")) {
                throw new DukeException("Only one '/by' keyword can be used!\n" + DEADLINE_USE);
            } else {
                return firstWord;
            }
            //Fallthrough
        case "EVENT":
            if (userInputSplit.length == 1) {
                throw new DukeException(EVENT_USE);
            } else if (!userInput.toLowerCase().contains("/at")) {
                throw new DukeException("Please use the '/at' keyword to specify the event date/time.\n"
                        + EVENT_USE);
            } else if (userInputSplit[1].equals("/at")) {
                throw new DukeException("The description of an event cannot be empty.\n" + EVENT_USE);
            } else if (Arrays.asList(userInputSplit).indexOf("/at")
                    == Arrays.asList(userInputSplit).size() - 1) {
                throw new DukeException("Please enter the event date/time.\n" + EVENT_USE);
            } else if (Arrays.asList(userInputSplit).indexOf("/at")
                    != Arrays.asList(userInputSplit).lastIndexOf("/at")) {
                throw new DukeException("Only one '/at' keyword can be used!\n" + EVENT_USE);
            } else {
                return firstWord;
            }
            //Fallthrough
        case "DONE":
            if (userInputSplit.length != 2) {
                throw new DukeException("Enter index of item to mark it as done. "
                        + "Type 'list' to see all items.\n" + DONE_USE);
            } else {
                try {
                    if (Integer.parseInt(userInputSplit[1]) <= 0) {
                        throw new DukeException("Argument must be a positive integer.\n" + DONE_USE);
                    } else if (Integer.parseInt(userInputSplit[1]) > list.getTaskList().size()) {
                        throw new DukeException("Argument exceeds number of items on the list!\n" + DONE_USE);
                    } else {
                        return firstWord;
                    }
                } catch (NumberFormatException e) {
                    throw new DukeException("Argument must be a positive integer.\n" + DONE_USE);
                }
            }
            //Fallthrough
        case "DELETE":
            if (userInputSplit.length != 2) {
                throw new DukeException("Enter index of item to delete it. "
                        + "Type 'list' to see all items.\n");
            } else {
                try {
                    if (Integer.parseInt(userInputSplit[1]) <= 0) {
                        throw new DukeException("Argument must be a positive integer.\n" + DELETE_USE);
                    } else if (Integer.parseInt(userInputSplit[1]) > list.getTaskList().size()) {
                        throw new DukeException("Argument exceeds number of items on the list!\n" + DELETE_USE);
                    } else {
                        return firstWord;
                    }
                } catch (NumberFormatException e) {
                    throw new DukeException("Argument must be a positive integer.\n" + DELETE_USE);
                }
            }
            //Fallthrough
        case "LIST":
            if (userInputSplit.length > 2) {
                throw new DukeException(LIST_USE);
            } else if (userInputSplit.length == 2) {
                if (!userInputSplit[1].equals("/showtimer") && !userInputSplit[1].equals("/hidetimer")) {
                    throw new DukeException(LIST_USE);
                } else {
                    return firstWord;
                }
            } else {
                return firstWord;
            }
            //Fallthrough
        case "BYE":
            if (userInputSplit.length > 1) {
                throw new DukeException("'bye' command has no arguments!");
            } else {
                return firstWord;
            }
            //Fallthrough
        case "FIND":
            if (userInputSplit.length == 1) {
                throw new DukeException("Enter a keyword to search!");
            } else {
                return firstWord;
            }
            //Fallthrough
        default:
            throw new DukeException("Invalid command!\nFor list of commands, type: /help");
            //Fallthrough
        }
    }

    /**
     * Selects the command to execute based on the command type that is input.
     *
     * @param commandType Type of command
     * @param userInput User input
     * @param isTimerOn Whether the user has set timer on or timer off
     * @return Command
     * @throws DukeException If command is invalid
     */
    public Command selectCommand(String commandType, String userInput, boolean isTimerOn) throws DukeException {
        switch (commandType) {
        case "/HELP":
            return new HelpCommand();
            //Fallthrough
        case "TODO":
            return parseTodoCommand(userInput);
            //Fallthrough
        case "DEADLINE":
            return parseDeadlineCommand(userInput);
            //Fallthrough
        case "EVENT":
            return parseEventCommand(userInput);
            //Fallthrough
        case "DONE":
            return parseDoneCommand(userInput);
            //Fallthrough
        case "DELETE":
            return parseDeleteCommand(userInput);
            //Fallthrough
        case "LIST":
            return parseListCommand(userInput, isTimerOn);
            //Fallthrough
        case "BYE":
            return new ByeCommand();
            //Fallthrough
        case "FIND":
            return parseFindCommand(userInput, isTimerOn);
            //Fallthrough
        default:
            throw new DukeException("Invalid command!\nFor list of commands, type: /help");
            //Fallthrough
        }
    }

    /**
     * Parses user input and returns a to-do command if user input is valid.
     *
     * @param userInput User input
     * @return TodoCommand
     */
    public TodoCommand parseTodoCommand(String userInput) {
        return new TodoCommand(userInput.substring(5));
    }

    /**
     * Parses user input and returns a deadline command if user input is valid.
     *
     * @param userInput User input
     * @return DeadlineCommand
     * @throws DukeException If user input is invalid
     */
    public DeadlineCommand parseDeadlineCommand(String userInput) throws DukeException {
        String desc = userInput.substring(9, userInput.toLowerCase().indexOf("/by") - 1);
        String by = userInput.substring(userInput.indexOf("/by") + 4);
        String[] byComponents = by.split(" ");
        try {
            if (byComponents.length == 1) {
                // Case: deadline <task> /by <YYYY-MM-DD of deadline>
                LocalDate.parse(byComponents[0]);
                return new DeadlineCommand(new String[]{desc, byComponents[0]});
            } else if (byComponents.length == 2) {
                // Case: deadline <task> /by <YYYY-MM-DD of deadline> <HH:mm>
                LocalDate.parse(byComponents[0]);
                LocalTime.parse(byComponents[1]);
                return new DeadlineCommand(new String[]{desc, byComponents[0], byComponents[1]});
            } else {
                throw new DukeException(DEADLINE_USE);
            }
        } catch (DateTimeParseException dtpe) {
            throw new DukeException("Error reading date and/or time."
                    + "\nPlease enter date in the format: YYYY-MM-DD (with dashes),"
                    + "\nand time (if applicable) in the format: HH:mm (with colon).");
        }
    }

    /**
     * Parses user input and returns an event command if user input is valid.
     *
     * @param userInput User input
     * @return EventCommand
     * @throws DukeException If user input is invalid
     */
    public EventCommand parseEventCommand(String userInput) throws DukeException {
        try {
            String desc = userInput.substring(6, userInput.toLowerCase().indexOf("/at") - 1);
            String at = userInput.substring(userInput.indexOf("/at") + 4);
            String[] atComponents = at.split(" ");
            if (atComponents.length == 1) {
                // Case: event <desc> /at <YYYY-MM-DD of event>
                LocalDate.parse(atComponents[0]);
                return new EventCommand(new String[]{desc, atComponents[0]});
            } else if (atComponents.length == 2) {
                // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm>
                LocalDate.parse(atComponents[0]);
                LocalTime.parse(atComponents[1]);
                return new EventCommand(new String[]{desc, atComponents[0], atComponents[1]});
            } else if (atComponents.length == 4) {
                // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm> to <end HH:mm>
                if (!atComponents[2].toLowerCase().equals("to")) {
                    // Check if the 'to' keyword is used
                    throw new DukeException(EVENT_USE);
                } else {
                    LocalDate.parse(atComponents[0]);
                    LocalTime.parse(atComponents[1]);
                    LocalTime.parse(atComponents[3]);
                    return new EventCommand(new String[]{desc, atComponents[0], atComponents[1], atComponents[3]});
                }
            } else {
                throw new DukeException(EVENT_USE);
            }
        } catch (DateTimeParseException dtpe) {
            throw new DukeException("Error reading date and/or time."
                    + "\nPlease enter date in the format: YYYY-MM-DD (with dashes),"
                    + "\nand time (if applicable) in the format: HH:mm (with colon).");
        }
    }

    /**
     * Parses user input and returns a list command if user input is valid.
     *
     * @param userInput User input
     * @param isTimerOn Whether the user has set timer on or timer off
     * @return ListCommand
     */
    public ListCommand parseListCommand(String userInput, boolean isTimerOn) {
        String[] userInputSplit = userInput.toLowerCase().split(" ");
        if (userInputSplit.length == 2) {
            return new ListCommand(userInputSplit[1], isTimerOn);
        } else {
            return new ListCommand("", isTimerOn);
        }
    }

    /**
     * Parses user input and returns a delete command if user input is valid.
     *
     * @param userInput User input
     * @return DeleteCommand
     */
    public DeleteCommand parseDeleteCommand(String userInput) {
        String[] userInputSplit = userInput.toLowerCase().split(" ");
        return new DeleteCommand(Integer.parseInt(userInputSplit[1]));
    }

    /**
     * Parses user input and returns a done command if user input is valid.
     *
     * @param userInput User input
     * @return DoneCommand
     */
    public DoneCommand parseDoneCommand(String userInput) {
        String[] userInputSplit = userInput.toLowerCase().split(" ");
        return new DoneCommand(Integer.parseInt(userInputSplit[1]));
    }

    /**
     * Parses user input and returns a find command if user input is valid.
     *
     * @param userInput User input
     * @param isTimerOn Whether the user has set timer on or timer off
     * @return FindCommand
     */
    public FindCommand parseFindCommand(String userInput, boolean isTimerOn) {
        return new FindCommand(userInput.substring(5), isTimerOn);
    }

    /**
     * Converts the input list from strings to task objects.
     *
     * @param list Task list
     * @return Array list of task objects
     */
    public ArrayList<Task> parseSavedFile(ArrayList<String> list) {
        ArrayList<Task> parsedList = new ArrayList<>();
        if (list.size() != 0) {
            for (String s : list) {
                String taskType = s.substring(1, 2);
                String description = s.substring(7);
                String status = s.substring(4, 5);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uuuu");
                switch (taskType) {
                case "T":
                    parsedList.add(new Todo(description));
                    if (status.equals("V")) {
                        parsedList.get(parsedList.size() - 1).markAsDone();
                    }
                    break;
                case "D":
                    String by = description.substring(description.indexOf("by:") + 4);
                    String[] byComponents = by.split(" ");
                    String deadlineDateToParse = byComponents[0] + " " + byComponents[1] + " "
                            + byComponents[2].substring(0, 4);
                    if (Integer.parseInt(deadlineDateToParse.split(" ")[0]) < 10) {
                        deadlineDateToParse = "0" + deadlineDateToParse;
                    }
                    if (byComponents.length == 3) {
                        // Case: deadline <task> /by <YYYY-MM-DD of deadline>
                        LocalDate deadlineDate = LocalDate.parse(
                                LocalDate.parse(deadlineDateToParse, formatter).toString());
                        parsedList.add(new Deadline(description.substring(0, description.indexOf("(by:") - 1),
                                deadlineDate));
                        if (status.equals("V")) {
                            parsedList.get(parsedList.size() - 1).markAsDone();
                        }
                        break;
                    } else if (byComponents.length == 4) {
                        // Case: deadline <task> /by <YYYY-MM-DD of deadline> <HH:mm>
                        LocalDate deadlineDate = LocalDate.parse(
                                LocalDate.parse(deadlineDateToParse, formatter).toString());
                        LocalTime deadlineTime = LocalTime.parse(byComponents[3].substring(0,
                                byComponents[3].indexOf(")")));
                        parsedList.add(new Deadline(description.substring(0, description.indexOf("(by:") - 1),
                                deadlineDate, deadlineTime));
                        if (status.equals("V")) {
                            parsedList.get(parsedList.size() - 1).markAsDone();
                        }
                        break;
                    } else {
                        break;
                    }
                case "E":
                    String at = description.substring(description.indexOf("at:") + 4);
                    String[] atComponents = at.split(" ");
                    String eventDateToParse = atComponents[0] + " " + atComponents[1] + " "
                            + atComponents[2].substring(0, 4);
                    if (Integer.parseInt(eventDateToParse.split(" ")[0]) < 10) {
                        eventDateToParse = "0" + eventDateToParse;
                    }
                    if (atComponents.length == 3) {
                        // Case: event <desc> /at <YYYY-MM-DD of event>
                        LocalDate eventDate = LocalDate.parse(
                                LocalDate.parse(eventDateToParse, formatter).toString());
                        parsedList.add(new Event(description.substring(0, description.indexOf("(at:") - 1),
                                eventDate));
                        if (status.equals("V")) {
                            parsedList.get(parsedList.size() - 1).markAsDone();
                        }
                        break;
                    } else if (atComponents.length == 4) {
                        // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm>
                        LocalDate eventDate = LocalDate.parse(
                                LocalDate.parse(eventDateToParse, formatter).toString());
                        LocalTime eventTime = LocalTime.parse(atComponents[3].substring(0,
                                atComponents[3].indexOf(")")));
                        parsedList.add(new Event(description.substring(0, description.indexOf("(at:") - 1),
                                eventDate, eventTime));
                        if (status.equals("V")) {
                            parsedList.get(parsedList.size() - 1).markAsDone();
                        }
                        break;
                    } else if (atComponents.length == 6) {
                        // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm> to <end HH:mm>
                        LocalDate eventDate = LocalDate.parse(
                                LocalDate.parse(eventDateToParse, formatter).toString());
                        LocalTime eventTimeStart = LocalTime.parse(atComponents[3]);
                        LocalTime eventTimeEnd = LocalTime.parse(atComponents[5].substring(0,
                                atComponents[5].indexOf(")")));
                        parsedList.add(new Event(description.substring(0, description.indexOf("(at:") - 1),
                                eventDate, eventTimeStart, eventTimeEnd));
                        if (status.equals("V")) {
                            parsedList.get(parsedList.size() - 1).markAsDone();
                        }
                        break;
                    } else {
                        break;
                    }
                default:
                    break;
                }
            }
        }
        return parsedList;
    }
}
