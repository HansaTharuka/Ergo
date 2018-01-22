package org.techknights.ergo.UserAreas.Lists;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import org.techknights.ergo.Login.helper.SQLiteHandler;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ApiClient;
import org.techknights.ergo.Retrofit.ListViews.EventsOfUser;
import org.techknights.ergo.Retrofit.ListViews.ProjectOfUser;
import org.techknights.ergo.Retrofit.ListViews.TasksofUser;
import org.techknights.ergo.UserAreas.Adapters.EventListAdapter;
import org.techknights.ergo.UserAreas.Adapters.ProjectListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListActivity extends AppCompatActivity {

    private List<EventsOfUser> eventsList;
    private RecyclerView mRecyclerViewEvents;
    private RecyclerView.Adapter mEventsAdapter;
    private SQLiteHandler db;

    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);

        //mRegProgress.setTitle("");
        mRegProgress.setMessage("Loading Events");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        eventsList = new ArrayList<>();
        mRecyclerViewEvents = findViewById(R.id.recyclerViewEvents);
        mRecyclerViewEvents.setHasFixedSize(false);
        mRecyclerViewEvents.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        db = SQLiteHandler.getInstance(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid");
        String userid = user.get("userid");

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);


        Call<ArrayList<EventsOfUser>> call4 = client.getEventsOfUser("Bearer " + uid, userid);

        call4.enqueue(new Callback<ArrayList<EventsOfUser>>() {
            @Override
            public void onResponse(Call<ArrayList<EventsOfUser>> call, Response<ArrayList<EventsOfUser>> response) {
                //Toast.makeText(getApplicationContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                eventsList = response.body();
                mRegProgress.dismiss();
                if (eventsList != null) {
                    mEventsAdapter = new EventListAdapter(eventsList, getApplicationContext());
                    mRecyclerViewEvents.setAdapter(mEventsAdapter);
                    // Toast.makeText(getApplicationContext(),groupMembersList.get(0).getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No group members to show", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<EventsOfUser>> call, Throwable t) {
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
