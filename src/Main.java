import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

// mom1,2,3,4,5
//mom5 är väldigt fult gjort men hann inte göra dett snyggare
//använde charm-glisten och gson external librarys

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
