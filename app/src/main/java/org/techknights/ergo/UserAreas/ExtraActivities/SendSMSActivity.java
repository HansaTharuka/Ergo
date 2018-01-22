package org.techknights.ergo.UserAreas.ExtraActivities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.techknights.ergo.Manifest;
import org.techknights.ergo.R;

public class SendSMSActivity extends AppCompatActivity {
    Button btnStart;
    EditText varMsg,varPhoneNo;
    String PhonenofrommemberList;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        Intent intent = getIntent();
        PhonenofrommemberList = intent.getStringExtra("memberPhoneNo");
        Toast.makeText(getApplicationContext(),""+PhonenofrommemberList, Toast.LENGTH_LONG).show();

        btnStart=(Button)findViewById(R.id.idbtnStart);
        varMsg=(EditText) findViewById(R.id.idTextMsg);
        varPhoneNo=(EditText) findViewById(R.id.idTextPhoneNo);

        varPhoneNo.setText(PhonenofrommemberList);
    }

    public void sendSms(View view){
        int permissionCheck= ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS);
        if(permissionCheck== PackageManager.PERMISSION_GRANTED){
            MyMessage();
            //Toast.makeText(this,"Sent",Toast.LENGTH_SHORT).show();

        }else{
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);

        }

    }

    public void MyMessage(){
        String myNumber=PhonenofrommemberList;
        String myMsg =varMsg.getText().toString().trim();
        if(myNumber==null||myNumber.equals("")||myMsg==null||myMsg.equals("")){
            Toast.makeText(this,"Fields Cant be Empty",Toast.LENGTH_SHORT).show();
        }else {

            if (TextUtils.isDigitsOnly(myNumber)){
                SmsManager smsManager =SmsManager.getDefault();
                smsManager.sendTextMessage(myNumber,null,myMsg,null,null);
                Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show();

            }else {

                Toast.makeText(this,"Please enter numbers only",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_SEND_SMS:{

                if (grantResults.length>=0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    MyMessage();

                }else {
                    Toast.makeText(this,"You don't have required permission to make the action",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
