package mx.edu.ittepic.swconsumo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by carla on 22/03/18.
 */

public class GetJson extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    @Override
    protected Void doInBackground(Void... voids) {
        try {

            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=Tepic,mx&APPID=2b05b51c405c3834f1a66d2765eacc38");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data += line;
            }

            JSONObject atmosphere= new JSONObject(data.substring(data.length()-125,data.length()-56));
            JSONObject astronomy= new JSONObject(data.substring(data.length()-43,data.length()-3));

            //atmosphere a.substring(a.length-125,a.length-56) humidity pressure
            //astronomy a.substring(a.length-43,a.length-3)    sunrise sunset


            singleParsed = "Humedad:" + atmosphere.get("humidity") + "\n"+
                    "Presión:" + atmosphere.get("pressure") + "\n"+
                    "Amanecer:" + astronomy.get("sunrise") + "\n"+
                    "Anochecer:" + astronomy.get("sunset") + "\n";
            dataParsed = dataParsed + singleParsed +"\n" ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.data.setText("Tiempo:\n"+this.dataParsed);
    }
}

