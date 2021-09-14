package cits.application.vendor.atgsample;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class getdata extends AsyncTask<String,String,Void> {

    public List<senddata> listsend = new ArrayList<>();
    BufferedInputStream inputStream;
    JSONObject jsonObject;
    JSONObject jsonObject1;
    String result;
    JSONArray jsonArray1;
    Activity activity;
    Context context;
    private String Link = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&per_page=20&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s";

    public getdata(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(Link);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            result = readStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readStream() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try{
            while((line=bufferedReader.readLine())!=null) {
                stringBuilder.append(line);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            jsonObject = new JSONObject(result);
            jsonObject1 = jsonObject.getJSONObject("photos");
            jsonArray1 = jsonObject1.getJSONArray("photo");
            for (int i = 0; i < jsonArray1.length(); i++) {
                senddata senddata = new senddata();
                JSONObject jsonObject3 = jsonArray1.getJSONObject(i);
                senddata.setImageurl(jsonObject3.getString("url_s"));
                listsend.add(senddata);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
            RecyclerView recyclerView;
            recyclerView = this.activity.findViewById(R.id.list1);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            recycler adapter = new recycler(listsend, context);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
        ProgressBar progressBar;
        progressBar = this.activity.findViewById(R.id.progressBarpro);
        progressBar.setVisibility(View.GONE);
        }
    }
