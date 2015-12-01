package epiandroid.alexlevy0.epiandroid.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ListResourceBundle;

import epiandroid.alexlevy0.epiandroid.MainActivity;
import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class ModulesFragment extends ListFragment {
        private JsonObjectRequest request;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getActivity().getIntent().getExtras();
        String token = args.getString("token");

//        try {
            final JSONObject jsonBody = new JSONObject();
//            jsonBody.put("token", token);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https").authority(getString(R.string.url_api2))
                    .appendPath("modules").appendQueryParameter("token", "lubtp0i3p67pe9lp1p77bscdm0");
            request = new JsonObjectRequest(Request.Method.GET, builder.build().toString(), jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            showResponse(response);
                            VolleyLog.v("Response:%n %s", response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ERRROR---->", "CALLBACK DERREUR DE LA REQUET");
                    error.printStackTrace();
                }
            });

            NetworkSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

//        } catch (JSONException e){
//            Log.d("MYAPP", "unexpected JSON exception", e);
//        }
    }

    private void showResponse(JSONObject response) {
        try {
            final JSONObject obj = response;
            final JSONArray modules = obj.getJSONArray("modules");
            final int n = modules.length();
            String[] values = new String[n];
            for (int i = 0; i < n; ++i) {
                final JSONObject person = modules.getJSONObject(i);
                Log.d("TITLE --->", person.getString("title"));
                Log.d("YEAR --->", person.getString("scolaryear"));
                Log.d("GRADE --->", person.getString("grade"));
                values[i] = person.getString("title");
                //TODO
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
        } catch (JSONException e) {
            Log.e("JSON ERROR", e.getMessage());
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
    }


}
