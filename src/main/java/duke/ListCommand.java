package duke;

public class ListCommand extends Command {
    private String listCommand;
    private boolean isTimerOn;

    public ListCommand(String listCommand, boolean isTimerOn) {
        this.listCommand = listCommand;
        this.isTimerOn = isTimerOn;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (tasks.getTaskList().isEmpty()) {
            // List is empty
            if (!listCommand.equals("")) {
                // Change display timer settings if necessary
                isTimerOn = listCommand.equals("/showtimer");
            }
            ui.showEmptyListMessage();
        } else {
            // List is NOT empty
            if (listCommand.equals("")) {
                // No arguments given
                if (isTimerOn) {
                    ui.showListTimerOn(tasks.getTaskList());
                } else {
                    ui.showListTimerOff(tasks.getTaskList());
                }
            } else {
                // Arguments given
                if (listCommand.equals("/showtimer")) {
                    isTimerOn = true;
                    ui.showListTimerOn(tasks.getTaskList());
                } else {
                    isTimerOn = false;
                    ui.showListTimerOff(tasks.getTaskList());
                }
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    public boolean isTimerOn() {
        return isTimerOn;
    }
}
