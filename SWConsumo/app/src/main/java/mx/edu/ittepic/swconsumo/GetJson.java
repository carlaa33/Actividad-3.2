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
            URL url = new URL("https://query.yahooapis.com/v1/public/yql?q=select%20astronomy,atmosphere%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Tepic%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data += line;
            }
            Log.e("at",data.substring(data.length()-125,data.length()-56));
            Log.e("hola",data.substring(data.length()-125,data.length()-56));

            JSONObject atmosphere= new JSONObject(data.substring(data.length()-129,data.length()-60));
            JSONObject astronomy= new JSONObject(data.substring(data.length()-47,data.length()-7));
            Log.e("at",atmosphere.toString());
            Log.e("as",astronomy.toString());
            Log.e("hola","hola");
            singleParsed = "Humedad:" + atmosphere.get("humidity") + "\n"+
                    "Presión:" + atmosphere.get("pressure") + "\n"+
                    "Amanecer:" + astronomy.get("sunrise") + "\n"+
                    "Anochecer:" + astronomy.get("sunset") + "\n";
            dataParsed = dataParsed + singleParsed +"\n" ;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("MalformedURLException",e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IO",e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON",e.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.data.setText("Tiempo:\n"+this.dataParsed);
    }
}

