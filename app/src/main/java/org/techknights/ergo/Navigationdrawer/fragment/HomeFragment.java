package org.techknights.ergo.Navigationdrawer.fragment;

/**
 * Created by Hansa on 1/14/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.techknights.ergo.Login.helper.SQLiteHandler;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ApiClient;
import org.techknights.ergo.Retrofit.GroupMembers;
import org.techknights.ergo.Retrofit.TasksofUser;
import org.techknights.ergo.UserAreas.ProjectGroupMembersActivity;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.techknights.ergo.UserAreas.TasksListActivity;
import org.techknights.ergo.model.User;


public class HomeFragment extends Fragment {

    CardView cardViewPeopleCount;
    CardView cardViewTasksCount;

    private SQLiteHandler db;
    private TextView peopleCount;
    private TextView tasksCount;
    private List<GroupMembers> groupMembersList;
    private List<TasksofUser> tasksList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        groupMembersList = new ArrayList();
        tasksList = new ArrayList();
        // Locate the button in activity_main.xml
        cardViewPeopleCount = (CardView) view.findViewById(R.id.cardViewHome1);
        cardViewTasksCount = (CardView) view.findViewById(R.id.cardViewHome2);

        cardViewPeopleCount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(getContext(), ProjectGroupMembersActivity.class);
                startActivity(myIntent);
            }
        });
        cardViewTasksCount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(getContext(), TasksListActivity.class);
                startActivity(myIntent);
            }
        });
        db = SQLiteHandler.getInstance(this.getActivity());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid");
        String userid = user.get("userid");
        // Toast.makeText(this.getActivity(), "154124" + user.getName(), Toast.LENGTH_LONG).show();

        peopleCount = view.findViewById(R.id.peopleCountTextView1);
        tasksCount = view.findViewById(R.id.taskCountTextView);


        //Toast.makeText(getContext(), ""+ userid, Toast.LENGTH_LONG).show();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<GroupMembers>> call = client.getPeople("Bearer " + uid, userid);
        //------------------------------------------------------group members Area----------------------------------------//

        call.enqueue(new Callback<ArrayList<GroupMembers>>()

        {
            @Override
            public void onResponse
                    (Call<ArrayList<GroupMembers>> call, Response<ArrayList<GroupMembers>> response) {
                // Toast.makeText(getContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                groupMembersList = response.body();

                if (groupMembersList != null) {
                    peopleCount.setText("" + groupMembersList.size());
                } else {

                    Toast.makeText(getContext(), "No group members in project", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(),groupMembersList.get(0).getName(), Toast.LENGTH_LONG).show();
                //peopleCount.setText("" + groupMembersList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<GroupMembers>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        //------------------------------------------------------Tasks Area----------------------------------------//


        Call<ArrayList<TasksofUser>> calltasks = client.getTask("Bearer " + uid, userid);
        calltasks.enqueue(new Callback<ArrayList<TasksofUser>>()

        {
            @Override
            public void onResponse
                    (Call<ArrayList<TasksofUser>> call, Response<ArrayList<TasksofUser>> response) {
                // Toast.makeText(getContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                tasksList = response.body();

                if (tasksList != null) {
                    tasksCount.setText("" + tasksList.size());
                } else {

                    Toast.makeText(getContext(), "No group members in project", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(),tasksList.get(0).getName(), Toast.LENGTH_LONG).show();
                //peopleCount.setText("" + tasksList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<TasksofUser>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }


}
