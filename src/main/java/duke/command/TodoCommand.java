package duke.command;

import duke.task.Todo;
import duke.main.TaskList;
import duke.main.Ui;
import duke.main.Storage;
import duke.DukeException;

public class TodoCommand extends Command {
    String todoDesc;

    public TodoCommand(String todoDesc) {
        this.todoDesc = todoDesc;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
        Todo newTodo = new Todo(todoDesc);
        tasks.addToTaskList(newTodo);
        ui.showSuccessMessage(newTodo.toString(), tasks.getTaskList().size());
        storage.save(tasks.getTaskList());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}