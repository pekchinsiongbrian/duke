import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    // Prints welcome message when user first enters app
    public void showWelcome() {
        showTopLine();
        System.out.println("\tHello! I'm Brian-bot\n\tWhat can I do for you?");
        showBottomLine();
    }

    // Prints goodbye message after user calls 'bye' to close the app
    public void showGoodbye() {
        showTopLine();
        System.out.println("\tGoodbye! Hope to see you again soon!");
        showBottomLine();
    }

    // Prints this bare bones manual page when user calls '/help'
    public void showHelp() {
        showTopLine();
        System.out.println("\tYou called for help? Help is here! List of commands:" +
                "\n\ttodo, deadline, event, done, delete, list, bye");
        showBottomLine();
    }

    // Prints this message after a task is marked as done
    public void showDone(String task) {
        showTopLine();
        System.out.println("\tNice! I've marked this task as done:\n\t\t" + task);
        showBottomLine();
    }

    // Prints this message after a task is deleted
    public void showDelete(String task, int sizeOfList) {
        showTopLine();
        System.out.println("\tNoted. I've removed this task:\n\t\t" + task +
                "\n\tNow you have " + sizeOfList + " tasks in the list.");
        showBottomLine();
    }

    public void showSuccessMessage(String task, int sizeOfList) {
        showTopLine();
        System.out.println("\tGot it. I've added this task:\n\t\t" + task + "\n\tNow you have " +
                sizeOfList + " tasks in the list.");
        showBottomLine();
    }

    // Turns on countdown timer
    public void showListTimerOn(ArrayList<Task> list) {
        showTopLine();
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof Deadline) {
                System.out.println("\t" + (i + 1) + "." + ((Deadline) list.get(i)).displayDeadline());
            } else if (list.get(i) instanceof Event) {
                System.out.println("\t" + (i + 1) + "." + ((Event) list.get(i)).displayEventTime());
            } else {
                System.out.println("\t" + (i + 1) + "." + list.get(i));
            }
        }
        System.out.println("\n\tTip: Try using 'list /showtimer' or list '/hidetimer'!");
        showBottomLine();
    }

    // Turns off countdown timer
    public void showListTimerOff(ArrayList<Task> list) {
        showTopLine();
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t" + (i + 1) + "." + list.get(i));
        }
        System.out.println("\n\tTip: Try using 'list /showtimer' or list '/hidetimer'!");
        showBottomLine();
    }

    // Prints this message when task list is empty and user wants to view list
    public void showEmptyListMessage() {
        showTopLine();
        System.out.println("\tHere are the tasks in your list:\n\n\tYou have no tasks " +
                "right now.\n\tUse 'todo', 'deadline', or 'event' to add task!");
        showBottomLine();
    }

    // Prints out error message
    public void showError(String errorMessage) {
        System.err.println(errorMessage + "\n");
    }

    // Reads the user input
    public String readCommand() {
        return sc.nextLine();
    }

    // Prints top border for all messages
    public void showTopLine() {
        System.out.println("\t________________________________________________________________________________");
    }

    public void showBottomLine() {
        System.out.println("\t________________________________________________________________________________\n");
    }
}
