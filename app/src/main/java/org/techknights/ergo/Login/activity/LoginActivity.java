package org.techknights.ergo.Login.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.techknights.ergo.Navigationdrawer.activity.MainFragmentControlActivity;
import org.techknights.ergo.R;
import org.techknights.ergo.Login.app.AppConfig;
import org.techknights.ergo.Login.app.AppController;
import org.techknights.ergo.Login.helper.SessionManager;
import org.techknights.ergo.Login.helper.SQLiteHandler;


public class LoginActivity extends Activity {
  private static final String TAG = RegisterActivity.class.getSimpleName();
  private Button btnLogin;
  //private Button btnLinkToRegister;
  private EditText inputEmail;
  private EditText inputPassword;
  private ProgressDialog pDialog;
  private SessionManager session;
  private SQLiteHandler db;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    inputEmail = (EditText) findViewById(R.id.email);
    inputPassword = (EditText) findViewById(R.id.password);
    btnLogin = (Button) findViewById(R.id.btnLogin);
   // btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

    // Progress dialog
    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    // SQLite database handler
    db = SQLiteHandler.getInstance(getApplicationContext());

    // Session manager
    session = new SessionManager(getApplicationContext());

    // Check if user is already logged in or not
    if (session.isLoggedIn()) {
      // User is already logged in. Take him to main activity
      Intent intent = new Intent(LoginActivity.this, MainFragmentControlActivity.class);
      startActivity(intent);
      finish();
    }

    // Login button Click Event
    btnLogin.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Check for empty data in the form
        if (!email.isEmpty() && !password.isEmpty()) {
          // login user
          checkLogin(email, password);
        } else {
          // Prompt user to enter credentials
          Toast.makeText(getApplicationContext(),
                  "Please enter the credentials!", Toast.LENGTH_LONG)
                  .show();
        }
      }

    });

    // Link to Register Screen
//    btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
//
//      public void onClick(View view) {
//        Intent i = new Intent(getApplicationContext(),
//                RegisterActivity.class);
//        startActivity(i);
//        finish();
//      }
//    });

  }

  /**
   * function to verify login details in mysql db
   * */
  private void checkLogin(final String email, final String password) {
    // Tag used to cancel the request
    String tag_string_req = "req_login";

    pDialog.setMessage("Logging in ...");
    showDialog();

    StringRequest strReq = new StringRequest(Method.POST,
            AppConfig.URL_LOGIN, new Response.Listener<String>() {

      @Override
      public void onResponse(String response) {
        Log.d(TAG, "Login Response: " + response.toString());
        hideDialog();

        try {
          JSONObject jObj = new JSONObject(response);
          boolean error = jObj.getBoolean("error");

          // Check for error node in json
          if (!error) {
            // user successfully logged in
            // Create login session
            session.setLogin(true);

            // Now store the user in SQLite
            String uid = jObj.getString("uid");

            JSONObject user = jObj.getJSONObject("user");
            String name = user.getString("name");
            String email = user.getString("email");
            String userid = user.getString("userid");

            // Inserting row in users table
            db.addUser(name, email, uid, userid);

            // Launch main activity
            Intent intent = new Intent(LoginActivity.this,
                    MainFragmentControlActivity.class);
            startActivity(intent);
            finish();
          } else {
            // Error in login. Get the error message
            String errorMsg = jObj.getString("error_msg");
            Toast.makeText(getApplicationContext(),
                    errorMsg, Toast.LENGTH_LONG).show();
          }
        } catch (JSONException e) {
          // JSON error
          e.printStackTrace();
          Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

      }
    }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Login Error: " + error.getMessage());
        Toast.makeText(getApplicationContext(),
                error.getMessage(), Toast.LENGTH_LONG).show();
        hideDialog();
      }
    }) {

      @Override
      protected Map<String, String> getParams() {
        // Posting parameters to login url
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return params;
      }

    };

    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
  }

  private void showDialog() {
    if (!pDialog.isShowing())
      pDialog.show();
  }

  private void hideDialog() {
    if (pDialog.isShowing())
      pDialog.dismiss();
  }

  boolean twice =false;
  @Override
  public void onBackPressed() {

    Log.d(TAG,"Click");
    //super.onBackPressed();

    if(twice==true){
      Intent intent=new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
      finish();
      System.exit(0);

    }

    twice =true;
    Log.d(TAG,"Twice"+twice);

    Toast.makeText(LoginActivity.this,"Please press back again to exit",Toast.LENGTH_SHORT).show();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {

        twice =false;
        Log.d(TAG,"Twice"+twice);
      }
    },3000);

  }
}

