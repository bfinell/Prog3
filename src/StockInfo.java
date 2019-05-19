import com.google.gson.*;

import java.lang.reflect.Array;
import  java.util.Calendar;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.apache.commons.math3.*;

import com.sun.media.sound.InvalidFormatException;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.ini4j.*;

import javax.lang.model.util.ElementScanner6;

public class StockInfo {
   private String symbol,symbol2;
   private String size;
   private String dSeries;
   private String tSeries;
   private String interval;
   private String apiKey;
   private ArrayList<JsonObject> data = new ArrayList<>();
   private ArrayList<String> keyset = new ArrayList<>();
    private ArrayList<String> keyset2 = new ArrayList<>();
    private ArrayList<String> open = new ArrayList<>();
   private ArrayList<String> open2 = new ArrayList<>();
   private String start,end;
   private String finalURL,finalURL2;

//,
    public StockInfo(String dSeries, String tSeries, String symbol,String symbol2, String interval, String size,String apiKey,String start, String end)throws InvalidFormatException{
            this.dSeries=dSeries;
            this.tSeries=tSeries;
            this.symbol=symbol;
            this.symbol2=symbol2;
            this.interval=interval;
            this.size=size;
            this.apiKey = apiKey;
            this.start = (start);
            this.end = (end);
            System.out.println(start + "  end  " + end);

        }
      /*  private Calendar calendarFromString(String string)throws InvalidFormatException{
            String[] s = string.split("\\.");
            Calendar calendar;
            if (s.length == 3){
                calendar = new Calendar.Builder()
                        .setDate(Integer.parseInt(s[2]),Integer.parseInt(s[1])-1, Integer.parseInt(s[0]))
                        .build();
            }else throw new InvalidFormatException("Date format not valid!");
            return calendar;
        }*/
// https://www.alphavantage.co/query?function=Time_SERIES_INTRADAY&symbol=MSFT&outputsize=full&apikey=ZR69NHOOT7AMCZH8
//https://www.alphavantage.co/query?function=Time_SERIES_INTRADAY&symbol=MSFT&interval=60min&outputsize=full&apikey=ZR69NHOOT7AMCZH8
        private void URLBuilder(){
            if(symbol2!=""){
                if (this.tSeries.equals("TIME_SERIES_INTRADAY")){
                    finalURL2= "https://www.alphavantage.co/query?function=" +
                            tSeries+"&symbol=" +
                            symbol2+"&interval=" +
                            interval+"&outputsize=" +
                            size+"&apikey="+apiKey;
                }
                else {
                    finalURL2 =
                            "https://www.alphavantage.co/query?function=" +
                                    tSeries+"&symbol=" +
                                    symbol2+"&outputsize=" +
                                    size+"&apikey="+apiKey;//=ZR69NHOOT7AMCZH8";
                }
            }
        if (this.tSeries.equals("TIME_SERIES_INTRADAY")){
             finalURL= "https://www.alphavantage.co/query?function=" +
                    tSeries+"&symbol=" +
                    symbol+"&interval=" +
                    interval+"&outputsize=" +
                    size+"&apikey="+apiKey;
        }
          else {
              finalURL =
                      "https://www.alphavantage.co/query?function=" +
                              tSeries+"&symbol=" +
                              symbol+"&outputsize=" +
                              size+"&apikey="+apiKey;//=ZR69NHOOT7AMCZH8";

        }

    }
    public void getData(){
            URLBuilder();
        try {
            URL url = new URL(finalURL);
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

        if ((finalURL2!=null)){
            System.out.println(finalURL2);
            try{
                URL url = new URL(finalURL2);
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
                    keyset2.add(parts[i]);
                }
                open2.add(obj.get(tempObj).getAsJsonObject().get(keyset2.get(0)).getAsJsonObject().get(dSeries).toString());
                for (int i = 1; i<keyset2.size();i++){
                    open2.add(obj.get(tempObj).getAsJsonObject().get(keyset2.get(i).substring(1)).getAsJsonObject().get(dSeries).toString());

                }
                open2.add(obj.get(tempObj).getAsJsonObject().get(keyset2.get(keyset2.size()-1).substring(1,20)).getAsJsonObject().get(dSeries).toString());

            }catch (IOException | NullPointerException e){

            }
        }

    }
        protected int getStart(){
        String temp = start.replace(".","");
        return Integer.parseInt(temp);
        }
        protected int getEnd(){
        String temp = end.replace(".","");
            System.out.println(temp);
        return Integer.parseInt(temp);
        }

        protected double pearsonCorrelation(){

            double[] a=new double[open.size()];
            double[] b=new double[open2.size()];
            for (int i = 0; i<open.size(); i++){
                a[i]=Double.parseDouble(open.get(i).replace("\"",""));
                b[i]=Double.parseDouble(open2.get(i).replace("\"", ""));
            }
            double corr = new PearsonsCorrelation().correlation(a,b);
            return corr;
        }
        protected ArrayList<String> getDate(){return this.keyset;}
        protected ArrayList<String> getOpen(){return open;}
        protected ArrayList<String> getOpen2(){return open2;}


}
