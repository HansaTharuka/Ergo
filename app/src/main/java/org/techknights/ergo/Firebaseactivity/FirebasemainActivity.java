package org.techknights.ergo.Firebaseactivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import  org.techknights.ergo.R;
import org.techknights.ergo.Firebaseutil.NotificationUtils;
import org.techknights.ergo.Firebaseapp.Config;

public class FirebasemainActivity extends AppCompatActivity {

  private static final String TAG = FirebasemainActivity.class.getSimpleName();
  private BroadcastReceiver mRegistrationBroadcastReceiver;
  private TextView txtRegId, txtMessage;

  private static final int REQUEST_READ_CONTACTS =1 ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_firebasemain);



    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
    // Here, thisActivity is the current activity
    if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.GET_TASKS)
        != PackageManager.PERMISSION_GRANTED) {

      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(this,
          Manifest.permission.READ_CONTACTS)) {

        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

      } else {

        // No explanation needed, we can request the permission.

        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CONTACTS },
            REQUEST_READ_CONTACTS);

        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    }

    }

    txtRegId = (TextView) findViewById(R.id.txt_reg_id);
    txtMessage = (TextView) findViewById(R.id.txt_push_message);

    mRegistrationBroadcastReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {

        // checking for type intent filter
        if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
          // gcm successfully registered
          // now subscribe to `global` topic to receive app wide notifications
          FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

          displayFirebaseRegId();

        } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
          // new push notification is received

          String message = intent.getStringExtra("message");

          Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

          txtMessage.setText(message);
        }
      }
    };

    displayFirebaseRegId();
  }

  // Fetches reg id from shared preferences
  // and displays on the screen
  private void displayFirebaseRegId() {
    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
    String regId = pref.getString("regId", null);

    Log.e(TAG, "Firebase reg id: " + regId);

    if (!TextUtils.isEmpty(regId))
      txtRegId.setText("Firebase Reg Id: " + regId);
    else
      txtRegId.setText("Firebase Reg Id is not received yet!");
  }

  @Override
  protected void onResume() {
    super.onResume();

    // register GCM registration complete receiver
    LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
        new IntentFilter(Config.REGISTRATION_COMPLETE));

    // register new push message receiver
    // by doing this, the activity will be notified each time a new message arrives
    LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
        new IntentFilter(Config.PUSH_NOTIFICATION));

    // clear the notification area when the app is opened
    NotificationUtils.clearNotifications(getApplicationContext());
  }

  @Override
  protected void onPause() {
    LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    super.onPause();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
      String permissions[], int[] grantResults) {
    switch (requestCode) {
      case REQUEST_READ_CONTACTS: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          // permission was granted, yay! Do the
          // contacts-related task you need to do.

        } else {

          // permission denied, boo! Disable the
          // functionality that depends on this permission.
        }
        return;
      }

      // other 'case' lines to check for other
      // permissions this app might request
    }
  }
}
