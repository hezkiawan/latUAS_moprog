package com.example.latihanuas.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.latihanuas.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DdolFragment extends Fragment {
    private TextView txtSetup, txtPunchline;
    private Button btnGetAnother, btnReveal;
    private RequestQueue requestQueue;


    public DdolFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ddol, container, false);
        txtSetup = view.findViewById(R.id.txtSetup);
        txtPunchline = view.findViewById(R.id.txtPunchline);
        btnReveal = view.findViewById(R.id.btnReveal);
        btnGetAnother = view.findViewById(R.id.btnGetAnother);
        requestQueue = Volley.newRequestQueue(getContext());


        fetchDdol();
        btnGetAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDdol();
            }
        });

        //TODO 2B: buat tiap button reveal diklik, txtPunchline muncul.
        btnReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtPunchline.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void fetchDdol() {
        //TODO 2A: fetch daily dose of laughter, masukkan setup dan punchline ke textview masing2
        String url = "https://official-joke-api.appspot.com/random_joke";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, // METHOD
                url, // WHERE
                null, // BODY (not sending any data
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // 1. PARSING
                            // udah langsung JSON object, ga perlu buffered reader
                            String setup = response.getString("setup");
                            String punchline = response.getString("punchline");

                            // 2. Update UI
                            txtSetup.setText(setup);
                            txtPunchline.setText(punchline);

                            // 3. visibility
                            txtPunchline.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                // error listener
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtSetup.setText("Failed to retrieve joke");
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchDdolAsync(){
        new FetchJokeTask().execute();
    }

    // USING ASYNCTASK CLASS TO FETCH DATA
    private class FetchJokeTask extends AsyncTask<Void, Void, String>{

        // phase 1
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;

            try {
                // 1. define destination
                URL url = new URL("https://official-joke-api.appspot.com/random_joke");

                // 2. open the HTTP pipe
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);

                // 3. buat inputstream untuk baca data mengalir dalam bentuk byte
                InputStream inputStream = urlConnection.getInputStream();
                // 4. ubah ke char dengan inputStreamReader
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                // 5 . baca data line by line dengan bufferedReader
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                // 6. baca data line by line dan append ke string builder result
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    result.append(line);
                }

                // 7. tutup readers
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                // disconnect connection
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }

            return result.toString();
        }

        // phase 2 : hasil fetch buat update UI
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // Error handling kalau fetch gagal
            if(s == null){
                txtSetup.setText("Error: Failed to connect.");
                return;
            }

            try {
                // 1. convert string ke JSONObject
                // The string looks like: {"type":"general", "setup":"...", ...}
                JSONObject jsonObject = new JSONObject(s);

                // 2. extract values
                String setup = jsonObject.getString("setup");
                String punchline = jsonObject.getString("punchline");

                // 3. update UI
                txtSetup.setText(setup);
                txtPunchline.setText(punchline);

                // 4. Hide punchline dulu
                txtPunchline.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                txtSetup.setText("Error: Could not parse data.");
            }
        }
    }
}