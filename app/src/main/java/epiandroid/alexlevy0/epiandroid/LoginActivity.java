package epiandroid.alexlevy0.epiandroid;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import epiandroid.alexlevy0.epiandroid.utils.NetworkSingleton;

public class LoginActivity extends Activity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private JsonObjectRequest request;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

//        String test = "{\n" +
//                "                \"login\": \"levy_b\",\n" +
//                "                \"message\": \"Succès\",\n" +
//                "                \"access_token\": \"lubtp0i3p67pe9lp1p77bscdm0\"\n" +
//                "        }";
//        try {
//            JSONObject object = new JSONObject(test);
//            launchApp(object);
//        } catch (JSONException e) {
//            //TODO
//        }


        try {
            setLoginView();
        } catch (Exception e){
            Log.e("SetLoginView ERROR", e.getMessage(), e);
        }
    }

    private boolean setLoginView() {
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String userPwd = password.getText().toString();
                Toast.makeText(LoginActivity.this, "Ca vient, ca vient...", Toast.LENGTH_LONG).show();

                if (userName.isEmpty() || userPwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Verifier les champs login/password", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    final JSONObject jsonBody = new JSONObject();
                    jsonBody.put("login", userName);
                    jsonBody.put("password", userPwd);

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https").authority(getString(R.string.url_api))
                        .appendPath("login").appendQueryParameter("format", "json");
                    request = new JsonObjectRequest(Request.Method.POST, builder.build().toString(), jsonBody,
                            new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                                launchApp(response);
                                VolleyLog.v("Response:%n %s", response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR LOGIN", error.getMessage());
                                error.printStackTrace();
                            }
                        });

                    NetworkSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

                } catch (JSONException e){
                    Log.d("MYAPP", "unexpected JSON exception", e);
                }
            }
        });
        return true;
    }

    private void launchApp(JSONObject response) {
        try {
            final String token = response.getString("access_token");
            final String login = response.getString("login");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("login", login);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
