package epiandroid.alexlevy0.epiandroid.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.R;
import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class LogFragment extends Fragment {
    private JsonObjectRequest request;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.log_fragment, container, false);

        // Requete
        Bundle args = getActivity().getIntent().getExtras();
        String token = args.getString("token");
        final JSONObject jsonBody = new JSONObject();
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https").authority(getString(R.string.url_api2))
                .appendPath("infos").appendQueryParameter("token", "lubtp0i3p67pe9lp1p77bscdm0");
        request = new JsonObjectRequest(Request.Method.GET, builder.build().toString(), jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("GOOOOOOOOOOOOOD----->", "GOod");
                        Log.d("Test %n %s", response.toString());
                        showResponse(response, rootView);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRROR---->", "CALLBACK DERREUR DE LA REQUET");
                error.printStackTrace();
            }
        });
        NetworkSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

        return rootView;
    }

    private void showResponse(JSONObject response, View rootView) {
        try {
            final JSONArray logtime = response.getJSONArray("current");
            final int n = logtime.length();
            final JSONObject current = logtime.getJSONObject(n - 1);
            Log.d("LOG  --->", current.getString("active_log"));
            Log.d("CM   --->", current.getString("code_module"));

            textView = (TextView) rootView.findViewById(R.id.logintime);
            textView.setText("Temps de log pour la semaine : " + current.getString("active_log") + "H");
        } catch (JSONException e) {
            Log.e("JSON ERROR", e.getMessage());
        }

    }
}
