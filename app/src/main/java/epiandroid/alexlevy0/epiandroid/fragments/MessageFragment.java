package epiandroid.alexlevy0.epiandroid.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.MessageAdapter;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.MyObject;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.SimpleDividerItemDecoration;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class MessageFragment extends Fragment {

    private RecyclerView        mRecyclerView;
    private MessageAdapter      mAdapter;
    private LinearLayoutManager layoutManager;
    private JsonArrayRequest    request;
    private View                rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.message_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        Bundle args = getActivity().getIntent().getExtras();
        String token = args.getString("token");

        final JSONArray jsonBody = new JSONArray();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("epitech-api.herokuapp.com")
                .appendPath("messages").appendQueryParameter("token", "lubtp0i3p67pe9lp1p77bscdm0");
        request = new JsonArrayRequest(Request.Method.GET, builder.build().toString(), jsonBody,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("SUCESS---->", "CALLBACK DE SUCESS DE LA REQUET");
                        VolleyLog.v("Response:%n %s", response.toString());
                        mAdapter = new MessageAdapter(response);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRROR---->", "CALLBACK DERREUR DE LA REQUET");
                error.printStackTrace();
            }
        });
        NetworkSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
        //mAdapter = new MessageAdapter(cities);
        //mAdapter = new MessageAdapter(JSON);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
