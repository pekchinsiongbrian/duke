import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String horizontalLine = "\t____________________________________________________________";
        System.out.println(horizontalLine + "\n\tHello! I'm Duke\n\tWhat can I do for you?\n" + horizontalLine);

        String[] list = new String[100];
        int indexToAdd = 0;

        while (true) {
            System.out.println();
            String userInput = sc.nextLine();
            if (userInput.equals("bye")) {
                System.out.println(horizontalLine + "\n\tBye. Hope to see you again soon!\n" + horizontalLine);
                sc.close();
                break;
            } else if (userInput.equals("list")) {
                System.out.println(horizontalLine);
                for (int i = 0; i < list.length; i++) {
                    if (list[i] == null) {
                        break;
                    } else {
                        System.out.println("\t" + (i + 1) + ". " + list[i]);
                    }
                }
                System.out.println(horizontalLine);
            } else {
                list[indexToAdd] = userInput;
                indexToAdd += 1;
                System.out.println(horizontalLine + "\n\tadded: " + userInput + "\n" + horizontalLine);
            }
        }
    }
}
