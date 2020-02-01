package duke.command;

import duke.DukeException;
import duke.main.TaskList;
import duke.main.Ui;
import duke.main.Storage;
import duke.task.Task;


public class DeleteCommand extends Command {
    private int deleteIndex;

    public DeleteCommand(int deleteIndex) {
        this.deleteIndex = deleteIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task toDelete = tasks.getTaskList().get(deleteIndex - 1);
        tasks.deleteFromTaskList(deleteIndex - 1);
        ui.showDelete(toDelete.toString(), tasks.getTaskList().size());
        storage.save(tasks.getTaskList());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
