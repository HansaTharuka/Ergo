package org.techknights.ergo.UserAreas.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.techknights.ergo.R;
import org.techknights.ergo.Retrofit.ListViews.EventsOfUser;
import org.techknights.ergo.Retrofit.ListViews.MilestonesOfUser;
import org.techknights.ergo.UserAreas.SingleViews.EventSingleActivity;
import org.techknights.ergo.UserAreas.SingleViews.MilestoneSingleActivity;

import java.util.List;

/**
 * Created by Hansa on 1/22/2018.
 */

public class MilestoneListAdapter extends RecyclerView.Adapter<MilestoneListAdapter.ViewHolder> {


    private List<MilestonesOfUser> milestonesList;
    private Context context;

    public MilestoneListAdapter(List<MilestonesOfUser> milestonesList, Context context) {
        this.milestonesList = milestonesList;
        this.context = context;
    }

    @Override
    public MilestoneListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.milestone_single_layout, parent, false);
        return new MilestoneListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MilestoneListAdapter.ViewHolder holder, int position) {
        final MilestonesOfUser milestone = milestonesList.get(position);
//""+groupMember.getProfile_pic()


        holder.tvNameEvent.setText(milestone.getDescription().toString());
        holder.tvEventDiscription.setText(milestone.getStart_date().toString());


        holder.rlEventSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.getContext().startActivity(new Intent(view.getContext(),ProjectGroupMemberSingleProfileActivity.class));
                Intent intent = new Intent(view.getContext(), MilestoneSingleActivity.class);
                intent.putExtra("MilestoneId", milestone.getId());
                view.getContext().startActivity(intent);
                // Toast.makeText(context,"Loading "+event.getDescription()+" Event",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return milestonesList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameEvent, tvEventDiscription;
        private RelativeLayout rlEventSingle;






        public ViewHolder(View itemView) {
            super(itemView);

            tvNameEvent = itemView.findViewById(R.id.milestone_single_name);
            tvEventDiscription = itemView.findViewById(R.id.milestone_single_description);
            rlEventSingle=itemView.findViewById(R.id.milestone_single_layout_item);


        }
    }
}


