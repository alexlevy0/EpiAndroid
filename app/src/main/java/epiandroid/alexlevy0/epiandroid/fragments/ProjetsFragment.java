package epiandroid.alexlevy0.epiandroid.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class ProjetsFragment extends ListFragment {
    private JsonObjectRequest request;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getActivity().getIntent().getExtras();
        String token = args.getString("token");

        makeJsonArrayRequest();
    }

    private void makeJsonArrayRequest() {

        final JSONArray jsonBody = new JSONArray();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority(getString(R.string.url_api2))
                .appendPath("projects")
                .appendQueryParameter("token", "q90psp6603krffartm2pa6cjb4");

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, builder.build().toString(), jsonBody,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("JsonArrayRequest ---->", response.toString());
                        try {
                            final int n = response.length();
                            String[] values = new String[n];
                            for (int i = 0; i < response.length(); i++) {
                                response.getString(i);
                                JSONObject projet = new JSONObject(response.get(i).toString());
                                String PROJET_NAME = projet.getString("project");

                                //TODO SegV sur la liste
                                if (!PROJET_NAME.equals("null") && response.get(i).toString() != null)
                                    values[i] = PROJET_NAME;

//                                String REGISTERED = projet.getString("registered");
//                                if (REGISTERED.equals("1"))
//                                    values[i] = PROJET_NAME;
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    android.R.layout.simple_list_item_1, values);
                            setListAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("PUTAIAN", "Error: " + error.getMessage());
                        Toast.makeText(getActivity().getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
        });
        NetworkSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(req);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
    }
}

