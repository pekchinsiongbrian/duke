package duke.command;

import duke.task.Todo;
import duke.main.TaskList;
import duke.main.Ui;
import duke.main.Storage;
import duke.DukeException;

/**
 * Command that is executed when user inputs 'to-do'.
 */
public class TodoCommand extends Command {
    String todoDesc;

    public TodoCommand(String todoDesc) {
        this.todoDesc = todoDesc;
    }

    /**
     * Executes the 'to-do' command.
     *
     * @param tasks Task list
     * @param ui Current user interface
     * @param storage Current storage
     * @throws DukeException If task list fails to save
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Todo newTodo = new Todo(todoDesc);
        tasks.addToTaskList(newTodo);
        ui.showSuccessMessage(newTodo.toString(), tasks.getTaskList().size());
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
