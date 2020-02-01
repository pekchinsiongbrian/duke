package duke.command;

import duke.main.TaskList;
import duke.main.Ui;
import duke.main.Storage;
import duke.DukeException;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
        ui.showGoodbye();
        storage.save(tasks.getTaskList());
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
