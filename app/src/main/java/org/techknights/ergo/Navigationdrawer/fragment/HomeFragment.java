package org.techknights.ergo.Navigationdrawer.fragment;

/**
 * Created by Hansa on 1/14/2018.
 */

import android.app.ProgressDialog;
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
import org.techknights.ergo.Retrofit.ListViews.EventsOfUser;
import org.techknights.ergo.Retrofit.ListViews.GroupMembers;
import org.techknights.ergo.Retrofit.ListViews.MilestonesOfUser;
import org.techknights.ergo.Retrofit.ListViews.ProjectOfUser;
import org.techknights.ergo.Retrofit.ListViews.TasksofUser;
import org.techknights.ergo.UserAreas.Lists.EventListActivity;
import org.techknights.ergo.UserAreas.Lists.MilestoneListActivity;
import org.techknights.ergo.UserAreas.Lists.ProjectGroupMembersActivity;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.techknights.ergo.UserAreas.Lists.ProjectListActivity;
import org.techknights.ergo.UserAreas.Lists.TasksListActivity;


public class HomeFragment extends Fragment {

    CardView cardViewPeopleCount;
    CardView cardViewTasksCount;
    CardView cardViewProjectsCount;
    CardView cardViewEventsCount;
    CardView cardViewMilestonesCount;

    private SQLiteHandler db;
    private TextView peopleCount;
    private TextView tasksCount;
    private TextView projectsCount;
    private TextView eventsCount;
    private TextView milestonesCount;
    private List<GroupMembers> groupMembersList;
    private List<TasksofUser> tasksList;
    private List<ProjectOfUser> userProjectList;
    private List<EventsOfUser> userEventList;
    private List<MilestonesOfUser> userMilestoneList;
    private ProgressDialog mRegProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        mRegProgress = new ProgressDialog(getContext());
        //mRegProgress.setTitle("");
        mRegProgress.setMessage("Loading Your Data");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        groupMembersList = new ArrayList();
        tasksList = new ArrayList();
        userEventList = new ArrayList();
        userMilestoneList = new ArrayList();
        // Locate the button in activity_main.xml
        cardViewPeopleCount = (CardView) view.findViewById(R.id.cardViewHome1A);
        cardViewTasksCount = (CardView) view.findViewById(R.id.cardViewHome2A);
        cardViewProjectsCount = (CardView) view.findViewById(R.id.cardViewHome3A);
        cardViewEventsCount=(CardView) view.findViewById(R.id.cardViewHome4A);
        cardViewMilestonesCount=(CardView) view.findViewById(R.id.cardViewHome5A);

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

        cardViewProjectsCount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(getContext(), ProjectListActivity.class);
                startActivity(myIntent);
            }
        });

        cardViewEventsCount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(getContext(), EventListActivity.class);
                startActivity(myIntent);
            }
        });
        cardViewMilestonesCount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(getContext(), MilestoneListActivity.class);
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
        projectsCount=view.findViewById(R.id.projectCountTextView);
        eventsCount=view.findViewById(R.id.eventCountTextView);
        milestonesCount=view.findViewById(R.id.milestoneCountTextView);


        //Toast.makeText(getContext(), ""+ userid, Toast.LENGTH_LONG).show();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        //------------------------------------------------------group members Area----------------------------------------//
        Call<ArrayList<GroupMembers>> call = client.getPeople("Bearer " + uid, userid);
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


        //-------------------------------------Project Area----------------------------------
        Call<ArrayList<ProjectOfUser>> callprojects = client.getProjectsOfUser("Bearer " + uid, userid);
        callprojects.enqueue(new Callback<ArrayList<ProjectOfUser>>()

        {
            @Override
            public void onResponse
                    (Call<ArrayList<ProjectOfUser>> call, Response<ArrayList<ProjectOfUser>> response) {
                // Toast.makeText(getContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                userProjectList = response.body();

                if (userProjectList != null) {
                    projectsCount.setText("" + userProjectList.size());
                } else {

                    Toast.makeText(getContext(), "No Projects in project", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(),tasksList.get(0).getName(), Toast.LENGTH_LONG).show();
                //peopleCount.setText("" + tasksList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectOfUser>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



            //-------------------------------------Event Area----------------------------------
        Call<ArrayList<EventsOfUser>> callevents = client.getEventsOfUser("Bearer " + uid, userid);
        callevents.enqueue(new Callback<ArrayList<EventsOfUser>>()

        {
            @Override
            public void onResponse
                    (Call<ArrayList<EventsOfUser>> call, Response<ArrayList<EventsOfUser>> response) {
                // Toast.makeText(getContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                userEventList = response.body();


                mRegProgress.dismiss();

                if (userEventList != null) {
                    eventsCount.setText("" + userEventList.size());
                } else {

                    Toast.makeText(getContext(), "No Events in project", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(),tasksList.get(0).getName(), Toast.LENGTH_LONG).show();
                //peopleCount.setText("" + tasksList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<EventsOfUser>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        //-------------------------------------Milestone Area----------------------------------


        Call<ArrayList<MilestonesOfUser>> callmilestones = client.getMilestonesOfUser("Bearer " + uid, userid);
        callmilestones.enqueue(new Callback<ArrayList<MilestonesOfUser>>()

        {
            @Override
            public void onResponse
                    (Call<ArrayList<MilestonesOfUser>> call, Response<ArrayList<MilestonesOfUser>> response) {
                // Toast.makeText(getContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                userMilestoneList = response.body();


                mRegProgress.dismiss();

                if (userMilestoneList != null) {
                    milestonesCount.setText("" + userMilestoneList.size());
                } else {

                    Toast.makeText(getContext(), "No Events in project", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(),tasksList.get(0).getName(), Toast.LENGTH_LONG).show();
                //peopleCount.setText("" + tasksList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<MilestonesOfUser>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });





        return view;
    }


}
