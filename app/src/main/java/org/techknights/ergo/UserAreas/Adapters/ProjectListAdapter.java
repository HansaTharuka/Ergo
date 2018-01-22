package org.techknights.ergo.UserAreas.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ListViews.ProjectOfUser;
import org.techknights.ergo.UserAreas.SingleViews.ProjectSingleActivity;

import java.util.List;

/**
 * Created by Hansa on 1/21/2018.
 */

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    private List<ProjectOfUser> projectsList;
    private Context context;

    public ProjectListAdapter(List<ProjectOfUser> projectsList, Context context) {
        this.projectsList = projectsList;
        this.context = context;
    }
    @Override
    public ProjectListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.project_single_layout,parent,false);
        return new ProjectListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProjectListAdapter.ViewHolder holder, int position) {
        final ProjectOfUser project = projectsList.get(position);
//""+groupMember.getProfile_pic()


        holder.tvNameProject.setText(project.getName().toString());
        holder.tvProjectDiscription.setText(project.getCategory().toString());


        holder.rlProjectSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.getContext().startActivity(new Intent(view.getContext(),ProjectGroupMemberSingleProfileActivity.class));
                Intent intent = new Intent(view.getContext(),ProjectSingleActivity.class);
                intent.putExtra("ProjectId", project.getId());
                view.getContext().startActivity(intent);
                //Toast.makeText(context,"Loading "+project.getName()+" Project",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameProject, tvProjectDiscription;
        private RelativeLayout rlProjectSingle;



        public ViewHolder(View itemView) {
            super(itemView);

            tvNameProject = itemView.findViewById(R.id.project_single_name);
            tvProjectDiscription = itemView.findViewById(R.id.project_single_description);
            rlProjectSingle=itemView.findViewById(R.id.project_single_layout_item);


        }
    }



}
