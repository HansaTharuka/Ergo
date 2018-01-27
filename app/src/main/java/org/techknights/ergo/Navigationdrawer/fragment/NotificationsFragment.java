package org.techknights.ergo.Navigationdrawer.fragment;

/**
 * Created by Hansa on 1/14/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.techknights.ergo.R;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private TextView mName;
    private TextView mDescription;
    private TextView mReceived_date;
    //private ProgressDialog mRegProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

//        mRegProgress = new ProgressDialog(getContext());
//        //mRegProgress.setTitle("");
//        mRegProgress.setMessage("Loading Your Data");
//        mRegProgress.setCanceledOnTouchOutside(false);
//        mRegProgress.show();


        mName = view.findViewById(R.id.notificationdiscriptionsinglehead);
        mDescription = view.findViewById(R.id.notificationsdiscriptionsingle1);
        mReceived_date = view.findViewById(R.id.notificationsstartdatesingle1);


        SharedPreferences settings = this.getActivity().getSharedPreferences("PRESS", 0);
        String wordsString = settings.getString("words", "");
        String[] itemsWords = wordsString.split(",");
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < itemsWords.length; i++) {

            items.add(itemsWords[i]);

        }


        for (int i = 0; i< items.size(); i++) {
            Log.d("listItem", items.get(i));

               mName.setText(items.get(0));
                mDescription.setText(items.get(1));
               mReceived_date.setText(items.get(2));
//                mRegProgress.dismiss();
             //   Toast.makeText(getContext(), "No Notifications " + items.get(0), Toast.LENGTH_LONG).show();


        }


        return view;
    }

}

