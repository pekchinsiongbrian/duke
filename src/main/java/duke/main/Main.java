package duke.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final String FILE_PATH = System.getProperty("user.home")
            + "/Downloads/Y2S2/CS2103T - Software Engineering/duke-master/task-list.txt";
    private Duke duke = new Duke(FILE_PATH);
    private static Stage stg;

    @Override
    public void start(Stage stage) {
        try {
            stg = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Duke");
            stage.setResizable(false);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeWindow() {
        stg.close();
    }
}
