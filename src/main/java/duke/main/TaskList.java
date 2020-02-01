package duke.main;

import duke.task.Task;
import java.util.ArrayList;

public class TaskList {
    protected ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(ArrayList<String> list, Parser parser) {
        this.taskList = parser.parseSavedFile(list);
    }

    public void addToTaskList(Task t) {
        this.taskList.add(t);
    }

    public void deleteFromTaskList(int index) {
        this.taskList.remove(index);
    }

    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }
}
