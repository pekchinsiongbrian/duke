package duke.command;

import java.time.LocalDate;
import java.time.LocalTime;
import duke.main.TaskList;
import duke.main.Ui;
import duke.main.Storage;
import duke.DukeException;
import duke.task.Deadline;

/**
 * Command that is executed when user inputs 'deadline'.
 */
public class DeadlineCommand extends Command {
    private String[] deadlineDesc;

    public DeadlineCommand(String[] deadlineDesc) {
        this.deadlineDesc = deadlineDesc;
    }

    /**
     * Executes the 'deadline' command.
     *
     * @param tasks Task list
     * @param ui Current user interface
     * @param storage Current storage
     * @throws DukeException If task list fails to save
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
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
