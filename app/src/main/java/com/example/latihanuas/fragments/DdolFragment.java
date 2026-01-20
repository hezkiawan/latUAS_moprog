package com.example.latihanuas.fragments;

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


        return view;
    }

    private void fetchDdol() {
        //TODO 2A: fetch daily dose of laughter, masukkan setup dan punchline ke textview masing2
        String url = "https://official-joke-api.appspot.com/random_joke";


    }
}