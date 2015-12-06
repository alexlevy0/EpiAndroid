package epiandroid.alexlevy0.epiandroid.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.MessageAdapter;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.MyObject;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.PlanningAdapter;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.SimpleDividerItemDecoration;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class PlanningFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PlanningAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private JsonObjectRequest request;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.planning_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPlanning);

        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        //ajouterVilles();
        Bundle args = getActivity().getIntent().getExtras();
        String token = args.getString("token");

        final JSONObject jsonBody = new JSONObject();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("epitech-api.herokuapp.com")
                .appendPath("modules").appendQueryParameter("token", "lubtp0i3p67pe9lp1p77bscdm0");
        request = new JsonObjectRequest(Request.Method.GET, builder.build().toString(), jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SUCCES---->", "OK ");
                        VolleyLog.v("Response:%n %s", response.toString());
                        mAdapter = new PlanningAdapter(response);
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

    /*
    @Override;
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getActivity().getIntent().getExtras();
        String token = args.getString("token");

        String dt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            Log.e("ERROR ---->", e.getMessage());
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String output = sdf1.format(c.getTime());
        Log.e("OUTPUT ---->", output);

        final JSONObject jsonBody = new JSONObject();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority(getString(R.string.url_api2))
                .appendPath("planning")
                .appendQueryParameter("token", "q90psp6603krffartm2pa6cjb4")
                .appendQueryParameter("start", dt)
                .appendQueryParameter("end", "2015-12-10");
        request = new JsonObjectRequest(Request.Method.GET, builder.build().toString(), jsonBody,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("onResponse ---->", response.toString());
                showResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRROR---->", "CALLBACK DERREUR DE LA REQUET");
                error.printStackTrace();
            }
        });
        NetworkSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }
    */

    private void showResponse(JSONObject response) {
        try {
            Log.e("RESPONSE ---->", response.toString());
            final JSONObject obj = response;
            final int n = obj.length();
            String[] values = new String[n];
            for (int i = 0; i < n; ++i) {
                Log.d("TYPE_TITLE ----->", obj.getString("type_title"));
                Log.d("START ----->", obj.getString("start"));
                Log.d("TITLE_MODULE ----->", obj.getString("titlemodule"));

                values[i] = obj.getString("type_title");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, values);
//            setListAdapter(adapter);
        } catch (JSONException e) {
            Log.e("JSON ERROR", e.getMessage());
        }
    }
}
