import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String startHLine = "\t____________________________________________________________";
        String endHLine = "\n\t____________________________________________________________\n";
        System.out.println(startHLine + "\n\tHello! I'm Duke\n\tWhat can I do for you?" + endHLine);
        Scanner sc = new Scanner(System.in);

        while (true) {
            String userInput = sc.nextLine();
            if (userInput.equals("bye")) {
                System.out.println(startHLine + "\n\tBye. Hope to see you again soon!" + endHLine);
                break;
            } else {
                System.out.println(startHLine + "\n\t" + userInput + endHLine);
            }
        }
    }
}