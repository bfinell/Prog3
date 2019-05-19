import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.ini4j.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.sun.media.sound.InvalidFormatException;


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
    private ComboBox<String> symbol2;
    @FXML
    private ComboBox<String> timeInterval;
    @FXML
    private ComboBox<String> size;
    @FXML
    private ComboBox<String> API_KEY;
    @FXML
    private LineChart<Number,Number> graph;
    @FXML
    private TextField startDate;
    @FXML
    private TextField stopDate;
    @FXML
    private TextField pearson;

    ObservableList<String> apiKey = FXCollections.observableArrayList();
    ObservableList<String> dList = FXCollections.observableArrayList();
    ObservableList<String> tsList = FXCollections.observableArrayList();
    ObservableList<String> symbolList = FXCollections.observableArrayList();
    ObservableList<String> symbol2List = FXCollections.observableArrayList();
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
    symbol2List.addAll(sInfo);

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
        symbol2.setItems(symbol2List);
        timeInterval.setItems(tiList);
        size.setItems(sizeList);
    }


    @FXML
    protected void handledoQueryAction(ActionEvent event) throws InvalidFormatException{
        tArea.clear();
        StockInfo info = new StockInfo(dataSeries.getValue(),timeSeries.getValue(), symbol.getValue(),symbol2.getValue(),timeInterval.getValue() ,size.getValue(),API_KEY.getValue(),startDate.getText(),stopDate.getText());
        info.getData();
        setData(info.getDate(),info.getOpen(),info.getOpen2(),info.getStart(),info.getEnd());
        chartSetter(info.getDate(),info.getOpen(),info.getOpen2(),info.getStart(),info.getEnd());
        if (!info.getOpen2().isEmpty()){
            setPearson(info.pearsonCorrelation());
        }

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


    private void setData(ArrayList date, ArrayList price,ArrayList price2,int start, int stop) {
     date.remove(date.size()-1);
        System.out.println(price.size());
        System.out.println(date.get(2));

        if (symbol2.getValue()!=""){
            for (int i =price.size()-1 ; i >=0; i--) {

                int temp = Integer.parseInt(date.get(i).toString().replace("-","").replaceAll(" ","").substring(0,8));
                if (temp >=start && temp <= stop) {
                    tArea.appendText("Date:" + date.get(i) + " " + dataSeries.getValue().substring(3) + "  "+symbol.getValue()+" " + price.get(i).toString().replace("\"", "") + "   "+symbol2.getValue()+"" +
                            ": "+price2.get(i).toString().replace("\"", "")+"\n");
                }
            }

        }else {
            for (int i = price.size() - 1; i >= 0; i--) {

                int temp = Integer.parseInt(date.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
                if (temp >= start && temp <= stop) {
                    tArea.appendText("Date:" + date.get(i) + " " + dataSeries.getValue().substring(3) + "  " + symbol.getValue() + ": " + price.get(i).toString().replace("\"", "") +"\n");
                }
            }
        }
    }

    //mom5
    private void chartSetter(ArrayList date, ArrayList price,ArrayList price2, int start, int stop) {

        this.graph.getData().clear();

        System.out.println(date.get(1).toString().replace("-", ".").replace(":", "").replace(" ", "").substring(5, 10));

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(symbol.getValue());

        this.graph.getData().add(series);
        for (int i = 1; i < date.size() - 2; i++) {
            int temp = Integer.parseInt(date.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
            if (temp >= start && temp <= stop) {
                String tempD = date.get(i).toString().replace("-", ".").replace(":", "").replace(" ", "").substring(5, 10);
                String tempP = price.get(i).toString().replace("\"", "");
                Float p = Float.parseFloat(tempP);
                series.getData().add(new XYChart.Data(tempD, p));
            }
        }

        if (!price2.isEmpty()) {
            XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
            series2.setName(symbol2.getValue());
            this.graph.getData().add(series2);
            for (int i = 1; i < date.size() - 2; i++) {
                int temp = Integer.parseInt(date.get(i).toString().replace("-", "").replaceAll(" ", "").substring(0, 8));
                if (temp >= start && temp <= stop) {
                    String tempD = date.get(i).toString().replace("-", ".").replace(":", "").replace(" ", "").substring(5, 10);
                    String tempP = price2.get(i).toString().replace("\"", "");
                    Float p = Float.parseFloat(tempP);
                    series2.getData().add(new XYChart.Data(tempD, p));
                }
            }
        }

    }
    private void setPearson(double value){
        pearson.setText(Double.toString(value));
    }
}