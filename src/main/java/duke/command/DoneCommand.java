package duke.command;

import duke.main.TaskList;
import duke.main.Ui;
import duke.main.Storage;
import duke.DukeException;
import duke.task.Task;

/**
 * Command that is executed when user inputs 'done'.
 */
public class DoneCommand extends Command {
    private int doneIndex;

    public DoneCommand(int doneIndex) {
        this.doneIndex = doneIndex;
    }

    /**
     * Executes the 'done' command.
     *
     * @param tasks Task list
     * @param ui Current user interface
     * @param storage Current storage
     * @throws DukeException If task list fails to save
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task doneTask = tasks.getTaskList().get(doneIndex - 1);
        doneTask.markAsDone();
        storage.save(tasks.getTaskList());
        return ui.showDone(doneTask.toString());
    }

    /**
     * Updates main function if it should exit the programme or not.
     *
     * @return True if 'bye' command is called, false otherwise. In this case, false is returned.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
