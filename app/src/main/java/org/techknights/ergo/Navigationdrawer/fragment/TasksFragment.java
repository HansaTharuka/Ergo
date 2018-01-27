package org.techknights.ergo.Navigationdrawer.fragment;

/**
 * Created by Hansa on 1/14/2018.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.techknights.ergo.Login.helper.SQLiteHandler;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ApiClient;
import org.techknights.ergo.Retrofit.ListViews.ProjectOfUser;
import org.techknights.ergo.Retrofit.ListViews.TasksofUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TasksFragment extends Fragment {

    private TextView mName;
    private TextView mDescription;
    private TextView mStart_date;
    private TextView mEnd_date;
    private ProgressDialog mRegProgress;
    private SQLiteHandler db;

    private List<TasksofUser> tasksList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        mRegProgress = new ProgressDialog(getContext());
        //mRegProgress.setTitle("");
        mRegProgress.setMessage("Loading Your Data");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();

        tasksList = new ArrayList();


        db = SQLiteHandler.getInstance(this.getActivity());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid");
        String userid = user.get("userid");


        mName = view.findViewById(R.id.tasksdiscriptionsinglehead);
        mDescription = view.findViewById(R.id.tasksdiscriptionsingle1);
        mStart_date= view.findViewById(R.id.tasksstartdatesingle1);
        mEnd_date= view.findViewById(R.id.tasksenddatesingle1);

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<TasksofUser>> call = client.getTask("Bearer " + uid, userid);
        call.enqueue(new Callback<ArrayList<TasksofUser>>()

        {
            @Override
            public void onResponse
                    (Call<ArrayList<TasksofUser>> call, Response<ArrayList<TasksofUser>> response) {
                // Toast.makeText(getContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                tasksList = response.body();

                mRegProgress.dismiss();
                if (tasksList != null && tasksList.size()>0) {

                    mName.setText(tasksList.get(tasksList.size()-1).getName());
                    mDescription.setText(tasksList.get(tasksList.size()-1).getDescription());
                    mStart_date.setText(tasksList.get(tasksList.size()-1).getStart_date());
                    mEnd_date.setText(tasksList.get(tasksList.size()-1).getEnd_date());

                } else {

                    Toast.makeText(getContext(), "No Tasks in project", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(),groupMembersList.get(0).getName(), Toast.LENGTH_LONG).show();
                //peopleCount.setText("" + groupMembersList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<TasksofUser>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        return view;
    }

}

