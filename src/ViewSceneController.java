import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Window;

import java.awt.*;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;



public class ViewSceneController{
    @FXML private TextArea textArea;
    @FXML private Button doQuery ;
    @FXML private ComboBox dataSeries;
    @FXML private ComboBox timeSeries;
    @FXML private ComboBox symbol;
    @FXML private ComboBox timeInterval;
    @FXML private ComboBox size;

    protected void handleSubmitButtonAction(ActionEvent event){
        TextArea t = doQuery.getScene().snapshot(param -> )
    }

    public void setData (ArrayList date, ArrayList price){
        for (int i = 1; i < date.size(); i++)
            textArea.setText("Date:" + date.get(i) + ":" + price.get(i));

    }


}