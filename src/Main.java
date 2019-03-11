import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
   static ArrayList <String> parameters = new ArrayList<>();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ViewScene.fxml"));
        primaryStage.setTitle("Portfoilio");
        primaryStage.setScene(new Scene(root,800,500));
        primaryStage.show();

    }

}
