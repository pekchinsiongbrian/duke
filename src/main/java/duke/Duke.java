package duke;

public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

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
