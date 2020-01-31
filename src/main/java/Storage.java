import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    protected String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<String> load() throws DukeException {
        ArrayList<String> taskListInString = new ArrayList<>();
        try {
            File f = new File(this.filePath);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()) {
                taskListInString.add(sc.nextLine());
            }
        } catch (IOException ioe) {
            throw new DukeException("Existing task list does not exist or failed to load.");
        }
        return taskListInString;
    }

    public void save(ArrayList<Task> list) throws DukeException {
        File f = new File(this.filePath);
        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            for (Task task : list) {
                String toWrite = task.toString() + "\n";
                outputStream.write(toWrite.getBytes());
            }
        } catch (IOException ioe) {
            throw new DukeException("Failed to save the list!");
        }
    }
}
