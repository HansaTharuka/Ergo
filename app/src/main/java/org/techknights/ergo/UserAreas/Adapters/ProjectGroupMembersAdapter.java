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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.techknights.ergo.Navigationdrawer.other.CircleTransform;
import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ListViews.GroupMembers;
import org.techknights.ergo.UserAreas.SingleViews.ProjectGroupMemberSingleProfileActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hansa on 1/19/2018.
 */

public class ProjectGroupMembersAdapter extends RecyclerView.Adapter<ProjectGroupMembersAdapter.ViewHolder>{

    private List<GroupMembers> groupMembersList;
    private Context context;

    public ProjectGroupMembersAdapter(List<GroupMembers> groupMembersList, Context context) {
        this.groupMembersList = groupMembersList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.users_single_layout,parent,false);
        return new ProjectGroupMembersAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GroupMembers groupMember = groupMembersList.get(position);
//""+groupMember.getProfile_pic()
        Glide.with(context).load(groupMember.getProfile_pic().toString())
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.circleImageView);

        holder.tvName.setText(groupMember.getName().toString());
        holder.tvEmail.setText(groupMember.getEmail().toString());


        holder.rlUserSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.getContext().startActivity(new Intent(view.getContext(),ProjectGroupMemberSingleProfileActivity.class));
                Intent intent = new Intent(view.getContext(),ProjectGroupMemberSingleProfileActivity.class);
                intent.putExtra("memberId", groupMember.getId());
                view.getContext().startActivity(intent);
                //Toast.makeText(context,"Loading "+groupMember.getName()+"'s Profile",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return groupMembersList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView circleImageView;
        private TextView tvName, tvEmail;
        private RelativeLayout rlUserSingle;


        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.group_member_single_image);
            tvName = itemView.findViewById(R.id.group_member_single_name);
            tvEmail = itemView.findViewById(R.id.group_member_single_email);
            rlUserSingle=itemView.findViewById(R.id.user_single_layout_item);

        }
    }
}
