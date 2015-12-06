package epiandroid.alexlevy0.epiandroid.fragments;

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

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class PlanningFragment extends ListFragment {
    private JsonObjectRequest request;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getActivity().getIntent().getExtras();
        String token = args.getString("token");

        String dt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(sdf.parse(dt));
//        } catch (ParseException e) {
//            Log.e("ERROR ---->", e.getMessage());
//            e.printStackTrace();
//        }
//        c.add(Calendar.DATE, 1);
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        String output = sdf1.format(c.getTime());
//        Log.e("OUTPUT ---->", output);

        final JSONObject jsonBody = new JSONObject();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority(getString(R.string.url_api2))
                .appendPath("planning")
                .appendQueryParameter("token", "q90psp6603krffartm2pa6cjb4")
                .appendQueryParameter("start", dt)
                .appendQueryParameter("end", "2015-12-10" /*output*/);
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
