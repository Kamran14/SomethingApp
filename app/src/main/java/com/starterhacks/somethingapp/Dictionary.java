package com.starterhacks.somethingapp;

/**
 * Created by Kam on 2018-03-04.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import android.os.AsyncTask;
import android.widget.Toast;


public class Dictionary {


    String wordDef = "";
    public String main(String theWOrd){
        new CallbackTask().execute(dictionaryEntries(theWOrd));
        return wordDef;
    }
    protected void onCreate(String theWord){
        new CallbackTask().execute(dictionaryEntries(theWord));
    }

    private String dictionaryEntries(String myWord){
        final String language = "en";
        final String word = myWord;
        final String word_id = word.toLowerCase();

        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }

    private class CallbackTask extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params){

            final String app_id = "8578d332";
            final String app_key = "b38d64301e845257a3731c5ece7849c4";
            try{
                URL myUrl = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) myUrl.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("app_id", app_id);
                urlConnection.setRequestProperty("app_key", app_key);

                BufferedReader myReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while((line = myReader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
//                Toast.makeText(, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                return stringBuilder.toString();

            } catch (Exception e){
                e.printStackTrace();
                return e.toString();
            }
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //Toast.makeText(null, result, Toast.LENGTH_SHORT).show();
            wordDef = result;
            System.out.println(result);
        }
    }
}
