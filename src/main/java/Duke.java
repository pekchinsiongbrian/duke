import java.time.LocalDate;
import java.time.LocalTime;

public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException de) {
            ui.showError(de.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        boolean timerOn = false;
        while (!isExit) {
            try {
                String userInput = ui.readCommand();
                String commandType = Parser.getCommandType(userInput, tasks);
                switch(commandType) {
                    case "/HELP":
                        ui.showHelp();
                        break;
                    case "TODO":
                        String todoDesc = Parser.parseTodoCommand(userInput);
                        Todo newTodo = new Todo(todoDesc);
                        tasks.addToTaskList(newTodo);
                        ui.showSuccessMessage(newTodo.toString(), tasks.getTaskList().size());
                        storage.save(tasks.getTaskList());
                        break;
                    case "DEADLINE":
                        String[] deadlineDesc = Parser.parseDeadlineCommand(userInput);
                        Deadline newDeadline;
                        if (deadlineDesc.length == 2) {
                            // Case: deadline <task> /by <YYYY-MM-DD of deadline>
                            newDeadline = new Deadline(deadlineDesc[0], LocalDate.parse(deadlineDesc[1]));
                        } else {
                            // Case: deadline <task> /by <YYYY-MM-DD of deadline> <HH:mm>
                            newDeadline = new Deadline(deadlineDesc[0], LocalDate.parse(deadlineDesc[1]),
                                    LocalTime.parse(deadlineDesc[2]));
                        }
                        tasks.addToTaskList(newDeadline);
                        ui.showSuccessMessage(newDeadline.toString(), tasks.getTaskList().size());
                        storage.save(tasks.getTaskList());
                        break;
                    case "EVENT":
                        String[] eventDesc = Parser.parseEventCommand(userInput);
                        Event newEvent;
                        if (eventDesc.length == 2) {
                            // Case: event <desc> /at <YYYY-MM-DD of event>
                            newEvent = new Event(eventDesc[0], LocalDate.parse(eventDesc[1]));
                        } else if (eventDesc.length == 3) {
                            // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm>
                            newEvent = new Event(eventDesc[0], LocalDate.parse(eventDesc[1]),
                                    LocalTime.parse(eventDesc[2]));
                        } else {
                            // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm> to <end HH:mm>
                            newEvent = new Event(eventDesc[0], LocalDate.parse(eventDesc[1]),
                                    LocalTime.parse(eventDesc[2]), LocalTime.parse(eventDesc[3]));
                        }
                        tasks.addToTaskList(newEvent);
                        ui.showSuccessMessage(newEvent.toString(), tasks.getTaskList().size());
                        storage.save(tasks.getTaskList());
                        break;
                    case "DONE":
                        int doneIndex = Parser.parseDoneCommand(userInput);
                        Task doneTask = tasks.getTaskList().get(doneIndex - 1);
                        doneTask.markAsDone();
                        ui.showDone(doneTask.toString());
                        storage.save(tasks.getTaskList());
                        break;
                    case "DELETE":
                        int deleteIndex = Parser.parseDeleteCommand(userInput);
                        Task toDelete = tasks.getTaskList().get(deleteIndex - 1);
                        tasks.deleteFromTaskList(deleteIndex - 1);
                        ui.showDelete(toDelete.toString(), tasks.getTaskList().size());
                        storage.save(tasks.getTaskList());
                        break;
                    case "LIST":
                        String listCommand = Parser.parseListCommand(userInput);
                        if (tasks.getTaskList().isEmpty()) {
                            // List is empty
                            if (!listCommand.equals("")) {
                                // Change display timer settings if necessary
                                timerOn = listCommand.equals("/showtimer");
                            }
                            ui.showEmptyListMessage();
                        } else {
                            // List is NOT empty
                            if (listCommand.equals("")) {
                                // No arguments given
                                if (timerOn) {
                                    ui.showListTimerOn(tasks.getTaskList());
                                } else {
                                    ui.showListTimerOff(tasks.getTaskList());
                                }
                            } else {
                                // Arguments given
                                if (listCommand.equals("/showtimer")) {
                                    timerOn = true;
                                    ui.showListTimerOn(tasks.getTaskList());
                                } else {
                                    timerOn = false;
                                    ui.showListTimerOff(tasks.getTaskList());
                                }
                            }
                        }
                        break;
                    case "BYE":
                        ui.showGoodbye();
                        storage.save(tasks.getTaskList());
                        isExit = true;
                        break;
                    default:
                        break;
                }
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String home = System.getProperty("user.home");
        String filePath = home + "/Downloads/Y2S2/CS2103T - Software Engineering/duke-master/task-list.txt";
        new Duke(filePath).run();
    }
}
