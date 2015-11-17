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

/**
 * Created by alexl on 16/11/2015.
 */
public class LoginActivity extends Activity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private JsonObjectRequest request;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        try {
            setLoginView();
        } catch (Exception e){
            Log.e("SetLoginView ERROR", e.getMessage(), e);
        }
    }

    private boolean setLoginView(){
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String userPwd = password.getText().toString();
                if (userName == null || userName.isEmpty() || userPwd == null || userPwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Verifier les champs login/password", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(LoginActivity.this, "Ca vient, ca vient...", Toast.LENGTH_LONG).show();
                try {
                    final JSONObject jsonBody = new JSONObject();
                    jsonBody.put("login", userName);
                    jsonBody.put("password", userPwd);

//                Uri.Builder builder = new Uri.Builder();
//                builder.scheme("https").authority(getString(R.string.url_api))
//                        .appendPath("login");
                    request = new JsonObjectRequest(Request.Method.POST, "https://epitech-api.herokuapp.com/login", jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            launchApp(response);
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
                    Log.e("MYAPP", "unexpected JSON exception", e);
                    // Do something to recover ... or kill the app.
                }
            }
        });
        return true;
    }

    private void launchApp(JSONObject response) {
        Intent intent = new Intent(this, MainActivity.class);

        try {
            String token = response.getString("token");
            intent.putExtra("token", token);
            Log.d("TOKEN", token);
        } catch (JSONException e) {
            //TODO
        }
        startActivity(intent);
        finish();
    }
}
