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
import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.MessageAdapter;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.PlanningAdapter;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.SimpleDividerItemDecoration;
import epiandroid.alexlevy0.epiandroid.recyclerviewutils.TrombiAdapter;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class TrombiFragment extends Fragment {

    private TrombiAdapter trombiAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.trombi_fragment, container, false);
        final RecyclerView tRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTrombi);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        tRecyclerView.setLayoutManager(layoutManager);

        tRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
//        final Bundle args = getActivity().getIntent().getExtras();
//        final String token = args.getString("token");

        final JSONObject jsonObject = new JSONObject();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority("epitech-api.herokuapp.com")
                .appendPath("trombi")
                .appendQueryParameter("token", "lubtp0i3p67pe9lp1p77bscdm0")
                .appendQueryParameter("year", "2013")
                .appendQueryParameter("location", "FR/LYN")
                .appendQueryParameter("promo", "tek3");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, builder.build().toString(), jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SUCCES---->", "OK ");
                        VolleyLog.v("Response:%n %s", response.toString());
                        try {
                            trombiAdapter = new TrombiAdapter(response.getJSONArray("items"));
                            tRecyclerView.setAdapter(trombiAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRROR---->", "CALLBACK DERREUR DE LA REQUET");
                error.printStackTrace();
            }
        });

        NetworkSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
        tRecyclerView.setAdapter(trombiAdapter);
        return rootView;
    }


}