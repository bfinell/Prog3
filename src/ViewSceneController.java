import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

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
    private LineChart<Number,Number> graph;

    ObservableList<String> dList = FXCollections.observableArrayList("1. open", "2. high", "3. low", "4. close", "5. volume");
    ObservableList<String> tsList = FXCollections.observableArrayList("TIME_SERIES_INTRADAY", "TIME_SERIES_DAILY", "TIME_SERIES_DAILY_ADJUSTED", "TIME_SERIES_WEEKLY", "TIME_SERIES_WEEKLY_ADJUSTED", "TIME_SERIES_MONTHLY", "TIME_SERIES_MONTHLY_ADJUSTED");
    ObservableList<String> symbolList = FXCollections.observableArrayList("MSFT", "AAPL");
    ObservableList<String> tiList = FXCollections.observableArrayList("1min", "5min", "15min", "30min", "60min");
    ObservableList<String> sizeList = FXCollections.observableArrayList("full", "compact");

    ArrayList<String> params = new ArrayList<>();

    @FXML


    protected void handledoQueryAction(ActionEvent event) {
        StockInfo info = new StockInfo(dataSeries.getValue(),timeSeries.getValue(), symbol.getValue(),timeInterval.getValue() ,size.getValue() );
        info.getData();
        setData(info.getDate(),info.getOpen());
        chartSetter(info.getDate(),info.getOpen());
    }

    public void initialize() {
        dataSeries.setItems(dList);
        timeSeries.setItems(tsList);
        symbol.setItems(symbolList);
        timeInterval.setItems(tiList);
        size.setItems(sizeList);
    }

    public void setData(ArrayList date, ArrayList price) {
     date.remove(date.size()-1);
        System.out.println(price.size());
            for (int i =price.size()-1 ; i >=0; i--) {
                tArea.appendText("Date:" + date.get(i) +" "+ dataSeries.getValue().toString().substring(3)+ " "+ price.get(i).toString().replace("\"","") + "\n");

            }

    }
    public void chartSetter(ArrayList date, ArrayList price){

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