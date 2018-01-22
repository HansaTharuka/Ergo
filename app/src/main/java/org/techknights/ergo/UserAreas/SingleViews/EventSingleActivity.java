package org.techknights.ergo.UserAreas.SingleViews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.techknights.ergo.Login.helper.SQLiteHandler;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ApiClient;
import org.techknights.ergo.Retrofit.SingleViews.EventData;
import org.techknights.ergo.Retrofit.SingleViews.ProjectData;
import org.techknights.ergo.Retrofit.SingleViews.TaskData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventSingleActivity extends AppCompatActivity {


    private List<EventData> eventDataList;
    private SQLiteHandler db;

    private TextView mEventName1;
    private TextView mEventStartDate1;
    private TextView mEventEndDate1;
    private ProgressDialog mRegProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_single);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);

        //mRegProgress.setTitle("");
        mRegProgress.setMessage("Loading Event Details");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();


        mEventName1 = (TextView) findViewById(R.id.eventname1);
        mEventStartDate1 = (TextView) findViewById(R.id.eventstartdate1);
        mEventEndDate1 = (TextView) findViewById(R.id.eventenddate1);
        eventDataList = new ArrayList<>();

        db = SQLiteHandler.getInstance(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid"); //this is important for get user detail loged user token


        Intent intent = getIntent();
        String eventidfrommemberList = intent.getStringExtra("EventId");
//        Toast.makeText(getApplicationContext(),"hello", Toast.LENGTH_LONG)
//                .show();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<EventData>> call3 = client.getEventData("Bearer " + uid, eventidfrommemberList);

        call3.enqueue(new Callback<ArrayList<EventData>>() {
            @Override
            public void onResponse(Call<ArrayList<EventData>> call, Response<ArrayList<EventData>> response) {
                //Toast.makeText(getApplicationContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                eventDataList = response.body();

                mRegProgress.dismiss();

                if (eventDataList != null) {
                    mEventName1.setText(eventDataList.get(0).getDescription());
                    mEventStartDate1.setText(eventDataList.get(0).getStart_date());
                    mEventEndDate1.setText(eventDataList.get(0).getEnd_date());
//                    Toast.makeText(getApplicationContext(),"hello"+eventDataList.get(0).getDescription(), Toast.LENGTH_LONG)
//                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "No Events to show", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<EventData>> call3, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


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
