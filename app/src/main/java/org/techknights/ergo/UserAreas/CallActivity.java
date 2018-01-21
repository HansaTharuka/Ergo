package org.techknights.ergo.UserAreas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.techknights.ergo.Manifest;
import org.techknights.ergo.R;

public class CallActivity extends AppCompatActivity {

    Button btn;
    EditText numTxt;
    String sNum;
    String PhonenofrommemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Intent intent = getIntent();
        PhonenofrommemberList = intent.getStringExtra("memberPhoneNo");
        btn=(Button)findViewById(R.id.idbtnCall);
        numTxt=(EditText)findViewById(R.id.idNumtxt);

        numTxt.setText(PhonenofrommemberList);
    }
    public void btnCall(View v){

        Intent i=new Intent(Intent.ACTION_CALL);
        sNum=numTxt.getText().toString();
        if(sNum.trim().isEmpty()){
            i.setData(Uri.parse("tel:077123456"));
        }else {
            i.setData(Uri.parse("tel:077123456"));
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest
                .permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"Please grant permission to call",Toast.LENGTH_SHORT).show();
            requestPermission();
        }else {
            startActivity(i);
        }
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CALL_PHONE},1);
    }
}
