import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.ini4j.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ViewSceneController {
    @FXML
    private TextArea tArea;
    @FXML
    private Button doQuery;
    @FXML
    private ComboBox<String> dataSeries;
    @FXML
    private ComboBox<String> timeSeries;
    @FXML
    private ComboBox<String> symbol;
    @FXML
    private ComboBox<String> timeInterval;
    @FXML
    private ComboBox<String> size;
    @FXML
    private ComboBox<String> API_KEY;
    @FXML
    private LineChart<Number,Number> graph;

    ObservableList<String> apiKey = FXCollections.observableArrayList();
    ObservableList<String> dList = FXCollections.observableArrayList();
    ObservableList<String> tsList = FXCollections.observableArrayList();
    ObservableList<String> symbolList = FXCollections.observableArrayList();
    ObservableList<String> tiList = FXCollections.observableArrayList();
    ObservableList<String> sizeList = FXCollections.observableArrayList();

    private void fillLists()throws IOException{
    Ini ini = new Ini(new FileReader("./src/StockAnalyzer.ini"));
    String key = ini.get("controllInfo","API_KEY");
    apiKey.setAll(key);

    String dString = ini.get("controllInfo","DATA_SERIES");
    String[] dInfo = dString.split(",");
    dList.addAll(dInfo);

    String tsString = ini.get("controllInfo","TIME_SERIES");
    String[] tsInfo = tsString.split(",");
    tsList.addAll(tsInfo);

    String sString = ini.get("controllInfo","SYMBOL");
    String[] sInfo = sString.split(",");
    symbolList.addAll(sInfo);

    String tiString = ini.get("controllInfo","TIME_INTERVAL");
    String[] tiInfo = tiString.split(",");
    tiList.addAll(tiInfo);

    String osString = ini.get("controllInfo","OUTPUT_SIZE");
    String[] osInfo = osString.split(",");
    sizeList.addAll(osInfo);
    }

    public void initialize()throws IOException {
        fillLists();
        API_KEY.setItems(apiKey);
        dataSeries.setItems(dList);
        timeSeries.setItems(tsList);
        symbol.setItems(symbolList);
        timeInterval.setItems(tiList);
        size.setItems(sizeList);
    }


    @FXML
    protected void handledoQueryAction(ActionEvent event) {
        StockInfo info = new StockInfo(dataSeries.getValue(),timeSeries.getValue(), symbol.getValue(),timeInterval.getValue() ,size.getValue(),API_KEY.getValue());
        info.getData();
        setData(info.getDate(),info.getOpen());
        chartSetter(info.getDate(),info.getOpen());
    }
    @FXML
    private void handleTimeSeriesAction(ActionEvent event){
        timeInterval.setDisable(false);
        size.setDisable(false);

        if (!timeSeries.getValue().equals("TIME_SERIES_INTRADAY")){
            timeInterval.setDisable(true);
        }
        if (timeSeries.getValue().equals("TIME_SERIES_WEEKLY")||
                 timeSeries.getValue().equals("TIME_SERIES_WEEKLY_ADJUSTED")||
                 timeSeries.getValue().equals("TIME_SERIES_MONTHLY")||
                 timeSeries.getValue().equals("TIME_SERIES_MONTHLY_ADJUSTED")){
            size.setDisable(true);
        }
    }



    private void setData(ArrayList date, ArrayList price) {
     date.remove(date.size()-1);
        System.out.println(price.size());
            for (int i =price.size()-1 ; i >=0; i--) {
                tArea.appendText("Date:" + date.get(i) +" "+ dataSeries.getValue().toString().substring(3)+ " "+ price.get(i).toString().replace("\"","") + "\n");

            }

    }
    //mom5
    private void chartSetter(ArrayList date, ArrayList price){

        this.graph.getData().clear();

        System.out.println(date.get(1).toString().replace("-",".").replace(":","").replace(" ","").substring(5,10));

        XYChart.Series<Number,Number> series = new XYChart.Series<>();
        this.graph.getData().add(series);
        for (int i = 1;i< date.size()-2; i++){
            String tempD = date.get(i).toString().replace("-",".").replace(":","").replace(" ","").substring(5,10   );
            String tempP = price.get(i).toString().replace("\"","");
            Float p = Float.parseFloat(tempP);
            series.getData().add(new XYChart.Data(tempD,p));
        }



    }

}