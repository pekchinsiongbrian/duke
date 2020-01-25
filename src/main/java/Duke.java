public class Duke {

    private static final String HORIZONTAL_LINE = "\t____________________________________________________________";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        textFormatAndPrint("Hello! I'm Duke\n\tWhat can I do for you?");

        Task[] list = new Task[100];
        int indexToAdd = 0;

        while (true) {
            System.out.println();
            String userInput = sc.nextLine();
            if (userInput.equals("bye")) {
                String toPrint = "Bye. Hope to see you again soon!";
                textFormatAndPrint(toPrint);
                sc.close();
                break;
            } else if (userInput.equals("list")) {
                System.out.println(HORIZONTAL_LINE);
                for (int i = 0; i < list.length; i++) {
                    if (list[i] == null) {
                        break;
                    } else {
                        System.out.println("\t" + (i + 1) + "." + list[i]);
                    }
                }
                System.out.println(HORIZONTAL_LINE);
            } else if (userInput.split(" ")[0].equals("done")){
                if (userInput.split(" ").length != 2) {
                    list[indexToAdd] = createNewTask(userInput);
                    indexToAdd += 1;
                } else {
                    try {
                        if (Integer.parseInt(userInput.split(" ")[1]) <= 0) {
                            throw new Exception();
                        } else {
                            Task t = list[Integer.parseInt(userInput.split(" ")[1]) - 1];
                            t.markAsDone();
                            String toPrint = "Nice! I've marked this task as done:\n\t  " + t.toString();
                            textFormatAndPrint(toPrint);
                        }
                    } catch (Exception e) {
                        list[indexToAdd] = createNewTask(userInput);
                        indexToAdd += 1;
                    }
                }
            } else {
                list[indexToAdd] = createNewTask(userInput);
                indexToAdd += 1;
            }
        }
    }

    public static Task createNewTask(String userInput) {
        Task t = new Task(userInput);
        String toPrint = "added: " + userInput;
        textFormatAndPrint(toPrint);
        return t;
    }

    public static void textFormatAndPrint(String text) {
        System.out.println(HORIZONTAL_LINE + "\n\t" + text + "\n" + HORIZONTAL_LINE);
    }
}