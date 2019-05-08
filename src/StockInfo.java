import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.ini4j.*;

public class StockInfo {
   private String symbol;
   private String size;
   private   String dSeries;
   private   String tSeries;
   private   String interval;
   private String apikey;
   private   ArrayList<JsonObject> data = new ArrayList<>();
   private ArrayList<String> keyset = new ArrayList<>();
   private   ArrayList<String> open = new ArrayList<>();



    public StockInfo(String dSeries, String tSeries, String symbol, String interval, String size){
            this.dSeries=dSeries;
            this.tSeries=tSeries;
            this.symbol=symbol;
            this.interval=interval;
            this.size=size;
        //    this.apikey = apikey;

        }
// https://www.alphavantage.co/query?function=Time_SERIES_INTRADAY&symbol=MSFT&outputsize=full&apikey=ZR69NHOOT7AMCZH8
//https://www.alphavantage.co/query?function=Time_SERIES_INTRADAY&symbol=MSFT&interval=60min&outputsize=full&apikey=ZR69NHOOT7AMCZH8
        private String URLBuilder(){
        String finalURL ="";

        if (this.tSeries.equals("TIME_SERIES_INTRADAY")){
             finalURL= "https://www.alphavantage.co/query?function=" +
                    tSeries+"&symbol=" +
                    symbol+"&interval=" +
                    interval+"&outputsize=" +
                    size+"&apikey="+apikey;
            return finalURL;
        }
          else {
              finalURL =
                      "https://www.alphavantage.co/query?function=" +
                              tSeries+"&symbol=" +
                              symbol+"&outputsize=" +
                              size+"&apikey=ZR69NHOOT7AMCZH8";
              return finalURL;

        }

    }


    public void getData(){
        System.out.println(URLBuilder());
        try {
            URL url = new URL(URLBuilder());
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject obj = root.getAsJsonObject();

            String[] tempObjPart = obj.keySet().toString().split(",");
            String tempObj = tempObjPart[1].replace("]","").substring(1);
            System.out.println(tempObj);
            data.add(obj.get(tempObj).getAsJsonObject());
            String prePart = obj.get(tempObj).getAsJsonObject().keySet().toString().replace("[","") ;

            String [] parts = prePart.split(",");

            for (int i = 0; i<obj.get(tempObj).getAsJsonObject().size(); i++){
                keyset.add(parts[i]);
            }


            open.add(obj.get(tempObj).getAsJsonObject().get(keyset.get(0)).getAsJsonObject().get(dSeries).toString());
            for (int i = 1; i<keyset.size();i++){
                    open.add(obj.get(tempObj).getAsJsonObject().get(keyset.get(i).substring(1)).getAsJsonObject().get(dSeries).toString());

            }
            open.add(obj.get(tempObj).getAsJsonObject().get(keyset.get(keyset.size()-1).substring(1,20)).getAsJsonObject().get(dSeries).toString());

        }catch (IOException | NullPointerException e){

        }
        }

        protected ArrayList<String> getDate(){
            return this.keyset;
        }
        protected ArrayList<String> getOpen(){

        return open;
    }


}
