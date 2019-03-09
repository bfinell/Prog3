import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        StockInfo yeeet = new StockInfo("1. open","TIME_SERIES_INTRADAY","MSFT","15min","full");
        ViewSceneController view  = new ViewSceneController();
        view.setData(yeeet.getOpen(),yeeet.getDate());
}
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ViewScen.fxml"));
        primaryStage.setTitle("Portfoilio");
        primaryStage.setScene(new Scene(root,800,500));
        primaryStage.show();

    }

}
