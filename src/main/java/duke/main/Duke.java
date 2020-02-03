package duke.main;

import duke.DukeException;
import duke.command.*;

/**
 * Main class of the duke programme
 */
public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Loads existing task list by searching in filePath. If this path does not exist, programme will create a new file
     * in this path where tasks will be automatically saved to.
     *
     * Also, sets up user interface and command parser.
     *
     * @param filePath path of existing task list to load or path of new task list to create.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load(), parser);
        } catch (DukeException de) {
            ui.showError(de.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Gets user command, parses it, and executes it
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        boolean isTimerOn = false;
        while (!isExit) {
            try {
                String userInput = ui.readCommand();
                String commandType = parser.getCommandType(userInput, tasks);
                Command command = parser.selectCommand(commandType, userInput, isTimerOn);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
                if (command instanceof ListCommand) {
                    isTimerOn = ((ListCommand) command).isTimerOn();
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
