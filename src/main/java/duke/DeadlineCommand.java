package duke;

import java.time.LocalDate;
import java.time.LocalTime;

public class DeadlineCommand extends Command {
    private String[] deadlineDesc;

    public DeadlineCommand(String[] deadlineDesc) {
        this.deadlineDesc = deadlineDesc;
    }

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

    @Override
    public boolean isExit() {
        return false;
    }
}
