package duke.main;

import duke.task.Task;
import java.util.ArrayList;

/**
 * Contains the task list. Has operations to add and delete tasks in the list.
 */
public class TaskList {
    protected ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(ArrayList<String> list, Parser parser) {
        this.taskList = parser.parseSavedFile(list);
    }

    /**
     * Adds a task to the task list.
     *
     * @param t Task to be added
     */
    public void addToTaskList(Task t) {
        this.taskList.add(t);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param index Index of the item in the task list to be deleted
     */
    public void deleteFromTaskList(int index) {
        this.taskList.remove(index);
    }

    /**
     * Gets the task list.
     *
     * @return Array list of tasks
     */
    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }
}
