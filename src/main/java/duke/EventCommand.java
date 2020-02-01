package duke;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventCommand extends Command {
    private String[] eventDesc;

    public EventCommand(String[] eventDesc) {
        this.eventDesc = eventDesc;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Event newEvent;
        if (eventDesc.length == 2) {
            // Case: event <desc> /at <YYYY-MM-DD of event>
            newEvent = new Event(eventDesc[0], LocalDate.parse(eventDesc[1]));
        } else if (eventDesc.length == 3) {
            // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm>
            newEvent = new Event(eventDesc[0], LocalDate.parse(eventDesc[1]),
                    LocalTime.parse(eventDesc[2]));
        } else {
            // Case: event <desc> /at <YYYY-MM-DD of event> <start HH:mm> to <end HH:mm>
            newEvent = new Event(eventDesc[0], LocalDate.parse(eventDesc[1]),
                    LocalTime.parse(eventDesc[2]), LocalTime.parse(eventDesc[3]));
        }
        tasks.addToTaskList(newEvent);
        ui.showSuccessMessage(newEvent.toString(), tasks.getTaskList().size());
        storage.save(tasks.getTaskList());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
