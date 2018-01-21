package org.techknights.ergo.UserAreas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import org.techknights.ergo.Login.helper.SQLiteHandler;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ApiClient;
import org.techknights.ergo.Retrofit.GroupMembers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectGroupMembersActivity extends AppCompatActivity {

    private List<GroupMembers> groupMembersList;
    private RecyclerView mRecyclerViewGroupMembers;
    private RecyclerView.Adapter mGroupMembersAdapter;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_group_members);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupMembersList = new ArrayList<>();
        mRecyclerViewGroupMembers = findViewById(R.id.recyclerViewGroupMembers);
        mRecyclerViewGroupMembers.setHasFixedSize(false);
        mRecyclerViewGroupMembers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        db = SQLiteHandler.getInstance(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid");
        String userid = user.get("userid");
        //Toast.makeText(getApplicationContext(), ""+ userid, Toast.LENGTH_LONG).show();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<GroupMembers>> call = client.getPeople("Bearer " + uid, userid);

        call.enqueue(new Callback<ArrayList<GroupMembers>>() {
            @Override
            public void onResponse(Call<ArrayList<GroupMembers>> call, Response<ArrayList<GroupMembers>> response) {
                //Toast.makeText(getApplicationContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                groupMembersList = response.body();
                if (groupMembersList != null) {
                    mGroupMembersAdapter = new ProjectGroupMembersAdapter(groupMembersList, getApplicationContext());
                    mRecyclerViewGroupMembers.setAdapter(mGroupMembersAdapter);
                    // Toast.makeText(getApplicationContext(),groupMembersList.get(0).getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No group members to show", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<GroupMembers>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //Toast.makeText(getApplicationContext(),groupMembersList.get(0).getName(), Toast.LENGTH_LONG).show();


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
