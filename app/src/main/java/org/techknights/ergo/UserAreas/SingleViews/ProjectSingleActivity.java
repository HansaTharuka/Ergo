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
import org.techknights.ergo.Retrofit.SingleViews.ProjectData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectSingleActivity extends AppCompatActivity {

    private List<ProjectData> projectDataList;
    private SQLiteHandler db;

    private TextView mProjectName1;
    private TextView mProjectCatogary1;
    //private TextView mProjectDiscription1;
    private TextView mProjectStartDate1;
    private TextView mProjectEndDate1;

    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_single);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);

        //mRegProgress.setTitle("");
        mRegProgress.setMessage("Loading Project Details");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        mProjectName1 = (TextView) findViewById(R.id.projectname1);
        mProjectCatogary1 = (TextView) findViewById(R.id.projectcatagory1);
       // mProjectDiscription1 = (TextView) findViewById(R.id.projectst);
        mProjectStartDate1 = (TextView) findViewById(R.id.projectstartdate1);
        mProjectEndDate1 = (TextView) findViewById(R.id.projectenddate1);
        projectDataList = new ArrayList<>();

        db = SQLiteHandler.getInstance(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid"); //this is important for get user detail loged user token

        Intent intent = getIntent();
        String projectidfrommemberList = intent.getStringExtra("ProjectId");

        //Toast.makeText(getApplicationContext(),""+taskidfrommemberList, Toast.LENGTH_LONG).show();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<ProjectData>> call3 = client.getProjectData("Bearer " + uid, projectidfrommemberList);

        call3.enqueue(new Callback<ArrayList<ProjectData>>() {
            @Override
            public void onResponse(Call<ArrayList<ProjectData>> call, Response<ArrayList<ProjectData>> response) {
                //Toast.makeText(getApplicationContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                projectDataList = response.body();

                mRegProgress.dismiss();

                if (projectDataList != null) {
                    mProjectName1.setText(projectDataList.get(0).getName());
                    mProjectCatogary1.setText(projectDataList.get(0).getCategory());
                   // mProjectDiscription1.setText(projectDataList.get(0).getCategory());
                    mProjectStartDate1.setText(projectDataList.get(0).getStart_date());
                    mProjectEndDate1.setText(projectDataList.get(0).getEnd_date());
                     //Toast.makeText(getApplicationContext(),"hello"+projectDataList.get(0).getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No Project to show", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<ProjectData>> call3, Throwable t) {
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
