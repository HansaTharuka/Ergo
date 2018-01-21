package org.techknights.ergo.UserAreas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.techknights.ergo.Navigationdrawer.other.CircleTransform;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.GroupMembers;
import org.techknights.ergo.Retrofit.TasksofUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hansa on 1/21/2018.
 */

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.ViewHolder> {

    private List<TasksofUser> tasksList;
    private Context context;


    public TasksListAdapter(List<TasksofUser> tasksList, Context context) {
        this.tasksList = tasksList;
        this.context = context;
    }

    @Override
    public TasksListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.task_single_layout,parent,false);
        return new TasksListAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(TasksListAdapter.ViewHolder holder, int position) {
        final TasksofUser task = tasksList.get(position);
//""+groupMember.getProfile_pic()


        holder.tvNametask.setText(task.getName().toString());
        holder.tvDiscriptiontask.setText(task.getDescription().toString());


        holder.rlTaskSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.getContext().startActivity(new Intent(view.getContext(),ProjectGroupMemberSingleProfileActivity.class));
                Intent intent = new Intent(view.getContext(),TaskSingleActivity.class);
                intent.putExtra("TaskId", task.getId());
                view.getContext().startActivity(intent);
                Toast.makeText(context,"Loading "+task.getName()+"'s Task",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNametask, tvDiscriptiontask;
        private RelativeLayout rlTaskSingle;



        public ViewHolder(View itemView) {
            super(itemView);

            tvNametask = itemView.findViewById(R.id.task_single_name);
            tvDiscriptiontask = itemView.findViewById(R.id.task_single_description);
            rlTaskSingle=itemView.findViewById(R.id.task_single_layout_item);


        }
    }
}
