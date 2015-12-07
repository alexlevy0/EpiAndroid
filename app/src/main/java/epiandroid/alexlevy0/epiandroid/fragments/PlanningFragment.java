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
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.PlanningAdapter;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.SimpleDividerItemDecoration;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class PlanningFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PlanningAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private JsonArrayRequest request;
    private View rootView;

    /* Calendar */

    /* Calendar End */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.planning_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPlanning);
        layoutManager = new LinearLayoutManager(getActivity());
        Bundle args = getActivity().getIntent().getExtras();
        String token = args.getString("token");
        String db = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String de = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        this.rootView = rootView;
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        final JSONArray jsonBody = new JSONArray();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("epitech-api.herokuapp.com")
                .appendPath("planning").appendQueryParameter("token", token)
                .appendQueryParameter("start", db)
                .appendQueryParameter("end", "2015-12-10");
        request = new JsonArrayRequest(Request.Method.GET, builder.build().toString(), jsonBody,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

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
