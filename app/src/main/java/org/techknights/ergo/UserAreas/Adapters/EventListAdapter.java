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
import org.techknights.ergo.Retrofit.ListViews.EventsOfUser;
import org.techknights.ergo.Retrofit.ListViews.ProjectOfUser;
import org.techknights.ergo.UserAreas.SingleViews.EventSingleActivity;
import org.techknights.ergo.UserAreas.SingleViews.ProjectSingleActivity;

import java.util.List;

/**
 * Created by Hansa on 1/22/2018.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private List<EventsOfUser> eventsList;
    private Context context;

    public EventListAdapter(List<EventsOfUser> eventsList, Context context) {
        this.eventsList = eventsList;
        this.context = context;
    }


    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.event_single_layout, parent, false);
        return new EventListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventListAdapter.ViewHolder holder, int position) {
        final EventsOfUser event = eventsList.get(position);
//""+groupMember.getProfile_pic()

        //////////////Remove This If Error////////////////

        if (event != null) {
            holder.tvNameEvent.setText(event.getDescription().toString());
            holder.tvEventDiscription.setText(event.getStart_date().toString());


            holder.rlEventSingle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //view.getContext().startActivity(new Intent(view.getContext(),ProjectGroupMemberSingleProfileActivity.class));
                    Intent intent = new Intent(view.getContext(), EventSingleActivity.class);
                    intent.putExtra("EventId", event.getId());
                    view.getContext().startActivity(intent);
                    // Toast.makeText(context,"Loading "+event.getDescription()+" Event",Toast.LENGTH_SHORT).show();

                }
            });


        }else
        {
            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public int getItemCount() {
        return eventsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameEvent, tvEventDiscription;
        private RelativeLayout rlEventSingle;


        public ViewHolder(View itemView) {
            super(itemView);

            tvNameEvent = itemView.findViewById(R.id.event_single_name);
            tvEventDiscription = itemView.findViewById(R.id.event_single_description);
            rlEventSingle = itemView.findViewById(R.id.event_single_layout_item);


        }
    }


}
