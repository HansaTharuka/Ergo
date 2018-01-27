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
import org.techknights.ergo.Login.helper.SessionManager;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ApiClient;
import org.techknights.ergo.Retrofit.ListViews.MilestonesOfUser;
import org.techknights.ergo.Retrofit.ListViews.ProjectOfUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProjectsFragment extends Fragment {

    private TextView mName;
    private TextView mDescription;
    private TextView mStart_date;
    private TextView mEnd_date;
    private ProgressDialog mRegProgress;
    private SQLiteHandler db;

    private List<ProjectOfUser> projectsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);


        mRegProgress = new ProgressDialog(getContext());
        //mRegProgress.setTitle("");
        mRegProgress.setMessage("Loading Your Data");
        mRegProgress.setCanceledOnTouchOutside(false);
        mRegProgress.show();
        projectsList = new ArrayList();

        db = SQLiteHandler.getInstance(this.getActivity());
        HashMap<String, String> user = db.getUserDetails();
        String uid = user.get("uid");
        String userid = user.get("userid");

        mName = view.findViewById(R.id.projectsdiscriptionsinglehead);
        mDescription = view.findViewById(R.id.projectsdiscriptionsingle1);
        mStart_date= view.findViewById(R.id.projectsstartdatesingle1);
        mEnd_date= view.findViewById(R.id.projectsenddatesingle1);

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl("https://kinna.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        //remote localhost
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<ProjectOfUser>> call = client.getProjectsOfUser("Bearer " + uid, userid);
        call.enqueue(new Callback<ArrayList<ProjectOfUser>>()

        {
            @Override
            public void onResponse
                    (Call<ArrayList<ProjectOfUser>> call, Response<ArrayList<ProjectOfUser>> response) {
                // Toast.makeText(getContext(),"OK " + response.body().get(0).getName(), Toast.LENGTH_LONG).show();
                projectsList = response.body();

                mRegProgress.dismiss();
                if (projectsList != null && projectsList.size()>0) {

                    mName.setText(projectsList.get(projectsList.size()-1).getName());
                    mDescription.setText(projectsList.get(projectsList.size()-1).getCategory());
                    mStart_date.setText(projectsList.get(projectsList.size()-1).getStart_date());
                    mEnd_date.setText(projectsList.get(projectsList.size()-1).getEnd_date());

                } else {

                    Toast.makeText(getContext(), "No Projects in project", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getContext(),groupMembersList.get(0).getName(), Toast.LENGTH_LONG).show();
                //peopleCount.setText("" + groupMembersList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<ProjectOfUser>> call, Throwable t) {
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }


}
