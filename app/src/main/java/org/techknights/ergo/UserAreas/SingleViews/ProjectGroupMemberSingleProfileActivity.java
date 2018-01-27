package org.techknights.ergo.UserAreas.SingleViews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.techknights.ergo.Login.helper.SQLiteHandler;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ApiClient;
import org.techknights.ergo.Retrofit.SingleViews.ProfileData;
import org.techknights.ergo.UserAreas.ExtraActivities.CallActivity;
import org.techknights.ergo.UserAreas.ExtraActivities.SendSMSActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectGroupMemberSingleProfileActivity extends AppCompatActivity {

    private List<ProfileData> profileDataList;
    //    private RecyclerView mRecyclerViewGroupMembers;
//    private RecyclerView.Adapter mGroupMembersAdapter;
    private SQLiteHandler db;

    private ImageView mProfileImageView1;
    private ImageView imageOfSingleuser;
    private TextView mProfileName1;
    private TextView mProfileEmail1;
    private TextView mProfileDescription1;

    private Button mProfileSendSmsbtn1;
    private Button mProfileCallbtn1;
    private Button mProfileEmailbtn1;
    private ProgressDialog mRegProgress;

    public String PhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_group_member_single_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegProgress = new ProgressDialog(this);

        //mRegProgress.setTitle("");
        mRegProgress.setMessage("Loading Profile");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();


        mProfileImageView1 = (ImageView) findViewById(R.id.ivContactItem1);
        imageOfSingleuser = (ImageView) findViewById(R.id.imageofsingleuser);
        mProfileName1 = (TextView) findViewById(R.id.tvNameprofile);
        mProfileEmail1 = (TextView) findViewById(R.id.textViewemail1);
        mProfileDescription1 = (TextView) findViewById(R.id.textViewdescription1);
        mProfileSendSmsbtn1=(Button) findViewById(R.id.sendsmsbutton1);
        mProfileCallbtn1=(Button) findViewById(R.id.callbutton1);
        mProfileEmailbtn1=(Button) findViewById(R.id.emailbutton1);
        profileDataList = new ArrayList<>();


        db = SQLiteHandler.getInstance(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid"); //this is important for get user detail loged user token
        //String userid=user.get("userid");
        Intent intent = getIntent();
        String idfrommemberList = intent.getStringExtra("memberId");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<ProfileData>> call2 = client.getProfileData("Bearer " + uid, idfrommemberList);
        call2.enqueue(new Callback<ArrayList<ProfileData>>() {
            @Override
            public void onResponse(Call<ArrayList<ProfileData>> call, Response<ArrayList<ProfileData>> response) {

                //Toast.makeText(getApplicationContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                profileDataList = response.body();

                mRegProgress.dismiss();

                if (profileDataList != null) {
                    mProfileName1.setText(profileDataList.get(0).getName());
                    mProfileEmail1.setText(profileDataList.get(0).getEmail());
                    mProfileDescription1.setText("Hello I am Programmer");
//                    Toast.makeText(getApplicationContext(),profileDataList.get(0).getProfile_pic().toString(), Toast.LENGTH_LONG)
//                           .show();
                    //Picasso.with(getApplicationContext()).load(profileDataList.get(0).getProfile_pic()).into(mProfileImageView1);
                    Picasso.with(getApplicationContext()).load("http://dreamatico.com/data_images/people/people-8.jpg").into(imageOfSingleuser);

                    mProfileSendSmsbtn1.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), SendSMSActivity.class);
                            i.putExtra("memberPhoneNo", profileDataList.get(0).getPhone_number());
                            i.putExtra("memberNameSent", profileDataList.get(0).getName());
//                            Toast.makeText(getApplicationContext(),""+profileDataList.get(0).getName(), Toast.LENGTH_LONG).show();
                            startActivity(i);
                            //finish();
                        }
                    });
                    mProfileCallbtn1.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), CallActivity.class);
                            i.putExtra("memberPhoneNo", profileDataList.get(0).getPhone_number());

                            startActivity(i);
                            //finish();
                        }
                    });

//                    Toast.makeText(getApplicationContext(),""+profileDataList.get(0).getName(), Toast.LENGTH_LONG)
//                            .show();

                } else {
                    Toast.makeText(getApplicationContext(), "No group Members To show", Toast.LENGTH_LONG)
                            .show();
                }

//                Toast.makeText(getApplicationContext(),"100000000000"+profileDataList.get(0).getName(), Toast.LENGTH_LONG)
//                        .show();
            }


            @Override
            public void onFailure(Call<ArrayList<ProfileData>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void btnEmailClick(View v){

        Intent i=new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("email"));
        String[]s={""+profileDataList.get(0).getEmail()};
       i.putExtra(Intent.EXTRA_EMAIL,s);
        i.putExtra(Intent.EXTRA_SUBJECT,"Sent via Ergo Platform");
        i.putExtra(Intent.EXTRA_TEXT,"Type Your Email Body");
        i.setType("message/rfc822");
        Intent chooser=Intent.createChooser(i,"Launch Email");
        startActivity(chooser);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
