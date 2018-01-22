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
import org.techknights.ergo.Retrofit.SingleViews.TaskData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskSingleActivity extends AppCompatActivity {

    private List<TaskData> taskDataList;
    private SQLiteHandler db;

    private TextView mTaskName1;
    private TextView mTaskDiscription1;
    private TextView mTaskStatus1;
    private TextView mTaskStartDate1;
    private TextView mTaskEndDate1;
    private ProgressDialog mRegProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_single);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);

        //mRegProgress.setTitle("");
        mRegProgress.setMessage("Loading Task Details");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        mTaskName1 = (TextView) findViewById(R.id.taskname1);
        mTaskDiscription1 = (TextView) findViewById(R.id.taskdiscription1);
        mTaskStatus1 = (TextView) findViewById(R.id.taskstatus1);
        mTaskStartDate1 = (TextView) findViewById(R.id.taskstartdate1);
        mTaskEndDate1 = (TextView) findViewById(R.id.taskenddate1);
        taskDataList = new ArrayList<>();
        db = SQLiteHandler.getInstance(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid"); //this is important for get user detail loged user token

        Intent intent = getIntent();
        String taskidfrommemberList = intent.getStringExtra("TaskId");

        // Toast.makeText(getApplicationContext(),""+taskidfrommemberList, Toast.LENGTH_LONG).show();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<TaskData>> call3 = client.getTaskData("Bearer " + uid, taskidfrommemberList);

        call3.enqueue(new Callback<ArrayList<TaskData>>() {
            @Override
            public void onResponse(Call<ArrayList<TaskData>> call, Response<ArrayList<TaskData>> response) {
                //Toast.makeText(getApplicationContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                taskDataList = response.body();


                mRegProgress.dismiss();

                if (taskDataList != null) {
                    mTaskName1.setText(taskDataList.get(0).getName());
                    mTaskDiscription1.setText(taskDataList.get(0).getDescription());
                    mTaskStatus1.setText(taskDataList.get(0).getStatus());
                    mTaskStartDate1.setText(taskDataList.get(0).getStart_date());
                    mTaskEndDate1.setText(taskDataList.get(0).getEnd_date());
                    // Toast.makeText(getApplicationContext(),taskDataList.get(0).getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No group members to show", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<TaskData>> call3, Throwable t) {
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
