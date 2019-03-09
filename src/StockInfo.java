import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class StockInfo {
   private String symbol;
   private String size;
   private   String dSeries;
   private   String tSeries;
   private   String interval;
  private   ArrayList<JsonObject> data = new ArrayList<>();
    private ArrayList<String> keyset = new ArrayList<>();
    private   ArrayList<String> open = new ArrayList<>();


    public StockInfo(String dSeries, String tSeries, String symbol, String interval, String size){
            this.dSeries=dSeries;
            this.tSeries=tSeries;
            this.interval=interval;
            this.symbol=symbol;
            this.size=size;

        }

        private String URLBuilder(){
           String finalURL= "https://www.alphavantage.co/query?function=" +
                    "TIME_SERIES_INTRADAY&symbol=" +
                    symbol+"&interval=" +
                    interval+"&outputsize=" +
                    "full&apikey=ZR69NHOOT7AMCZH8";
            return finalURL;
    }


    public void getData(){

        try {
            URL url = new URL(URLBuilder());
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject obj = root.getAsJsonObject();
            System.out.println("yeet");
            int h=0;

            data.add(obj.get("Time Series (15min)").getAsJsonObject());
            String prePart = obj.get("Time Series (15min)").getAsJsonObject().keySet().toString().replace("[","") ;

            String [] parts = prePart.split(",");

            for (int i = 0; i<obj.get("Time Series (15min)").getAsJsonObject().size(); i++){
                keyset.add(parts[i]);
            }
            System.out.println(keyset.size());
            System.out.println(data.get(0).size());

            System.out.println(URLBuilder());
            for (int i = 1; i<keyset.size()-1;i++){
                   // System.out.println(obj.get("Time Series (15min)").getAsJsonObject().get(keyset.get(i).substring(1)).getAsJsonObject().get("1. open").toString());
                    open.add(obj.get("Time Series (15min)").getAsJsonObject().get(keyset.get(i).substring(1)).getAsJsonObject().get("1. open").toString());
            }
        }catch (IOException | NullPointerException e){
            System.out.println("fugma");
        }

        }
        protected ArrayList<String> getDate(){
            return keyset;
        }
    protected ArrayList<String> getOpen(){

        return open;
    }
}
