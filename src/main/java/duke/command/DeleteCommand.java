package duke.command;

import duke.DukeException;
import duke.main.TaskList;
import duke.main.Ui;
import duke.main.Storage;
import duke.task.Task;

/**
 * Command that is executed when user inputs 'delete'
 */
public class DeleteCommand extends Command {
    private int deleteIndex;

    public DeleteCommand(int deleteIndex) {
        this.deleteIndex = deleteIndex;
    }

    /**
     * Executes the 'delete' command
     *
     * @param tasks Task list
     * @param ui Current user interface
     * @param storage Current storage
     * @throws DukeException If task list fails to save
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task toDelete = tasks.getTaskList().get(deleteIndex - 1);
        tasks.deleteFromTaskList(deleteIndex - 1);
        ui.showDelete(toDelete.toString(), tasks.getTaskList().size());
        storage.save(tasks.getTaskList());
    }

    /**
     * Updates main function if it should exit the programme or not
     *
     * @return True if 'bye' command is called, false otherwise. In this case, false is returned.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
