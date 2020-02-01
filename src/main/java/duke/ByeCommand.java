package duke;

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
