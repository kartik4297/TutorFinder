package com.example.kartik.tutorfinder;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TutorTab extends Fragment {
    ArrayList<Tutors> tutors = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public TutorTab() {

        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tutor_tab, container, false);
        recyclerView =(RecyclerView)v.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new RecyclerAdapter(tutors,getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        new BackgroundWork().execute();
  return v;
    }


    public class BackgroundWork extends AsyncTask<Void,Tutors,Void> {


        private RecyclerAdapter adapter ;
        String json_string="http://tutorsfind.000webhostapp.com/Mobile/all_tutors.php";

        @Override
        protected void onPreExecute() {

 adapter= (RecyclerAdapter) recyclerView.getAdapter();


        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(json_string);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
             //just demonstration no need of connect method
                httpURLConnection.connect();
                InputStream inputstream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(line+"\n");
                }
                httpURLConnection.disconnect();
                String json_string=stringBuilder.toString().trim();
                JSONObject jsonObject = new JSONObject(json_string);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                Log.d("JSON STRING",json_string);
                int count=0;
                while(count<jsonArray.length())
                {
                    JSONObject jo = jsonArray.getJSONObject(count);

                    Tutors tutor = new Tutors(jo.getString("name"), jo.getString("gender"),jo.getString("mobile"),jo.getString("email"),jo.getString("address"),jo.getInt("age"));
                    // tutors.add(tutor);
                    publishProgress(tutor);
                    count++;
                     Log.d("TUTOR name",tutor.getName());
                    //  last_index=5;
                    //System.out.println("TUTOR name"+tutor.getName());


                }




            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Tutors... values) {
            tutors.add(values[0]);
            adapter.notifyDataSetChanged();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}

