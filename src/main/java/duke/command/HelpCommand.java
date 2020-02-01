package duke.command;

import duke.main.TaskList;
import duke.main.Ui;
import duke.main.Storage;

public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showHelp();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
