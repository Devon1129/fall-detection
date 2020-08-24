package com.example.walabotsmartsecuritydefense.ui.monitoring;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.ExpandableListRoomDataPump;
import com.example.walabotsmartsecuritydefense.R;
import com.example.walabotsmartsecuritydefense.adapter.RoomExpandableListAdapter;
import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.table.monitoring.Device;
import com.example.walabotsmartsecuritydefense.table.monitoring.Room;
import com.example.walabotsmartsecuritydefense.table.monitoring.Zone;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonitoringFragment extends Fragment {

    private MonitoringViewModel monitoringViewModel;

    protected CloudManager cloudManager;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){ //zoneAsync
                case 1:
                    expandableListDetail = ExpandableListRoomDataPump.getData();
                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new RoomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                    setListViewHeightBasedOnChildren(expandableListView);
                    break;
                case 2: //roomAsync
                    expandableListDetail = ExpandableListRoomDataPump.getData();
                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new RoomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                    setListViewHeightBasedOnChildren(expandableListView);
                    break;
            }
            Log.d( "MoniotringF", "handler: case" );

        }
    };



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MoniotringF", "onCreate");

        cloudManager = new CloudManager(getContext());

        //hannah_test
        //new MyAsyncTask().execute(urlApiSys_zone);
    }


    //hannah_test
//    class MyAsyncTask extends AsyncTask<String, Void, Void> {
//        @Override
//        protected Void doInBackground(String... strings) {
//            String url = strings[0];
//            cloudManager.zoneAsync(url, handler);
//
//            return null;
//        }
//    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        monitoringViewModel =
                ViewModelProviders.of(this).get(MonitoringViewModel.class);
        View root = inflater.inflate(R.layout.fragment_monitoring, container, false);

        initial(root);

//        expandableListDetail = ExpandableListRoomDataPump.getData();
//        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
//        expandableListAdapter = new RoomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
//        expandableListView.setAdapter(expandableListAdapter);
//        setListViewHeightBasedOnChildren(expandableListView);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();


//                Intent intent = new Intent();
//                intent.setClass(getContext(), HistoryDetailActivity.class);
//                intent.putExtra("historyID", childPosition);
//                startActivity(intent);
                return false;
            }
        });





        return root;
    }

    private void initial(View root) {
        expandableListView = (ExpandableListView) root.findViewById(R.id.expandable_listview);

        //hannah_test
//        final TextView textView = root.findViewById(R.id.text_monitoring);
//        monitoringViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("MoniotringF", "onResume");

//        final String urlApiSignin = Application.urlSignin;
//        cloudManager.getApitokenAsync(urlApiSignin, "xhwg85", "hwacom2020");

        //取得區域
        LitePal.deleteAll(Zone.class);
        final String urlApiSys_zone = Application.urlSys_zone;
        cloudManager.zoneAsync(urlApiSys_zone, handler);

        //取得設施[監測點]
        LitePal.deleteAll(Room.class);
        final String urlApiSys_room = Application.urlSys_room;
        cloudManager.roomAsync(urlApiSys_room, handler);

//        //取得設備
//        LitePal.deleteAll(Device.class);
//        final String urlApiSys_device = Application.urlSys_device;
//        cloudManager.deviceAsync(urlApiSys_device, handler);


    }

    //解決ScrollView內崁Listview時，造成listview只會顯示一行。
    public void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition 
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height = 1080;
        listView.setLayoutParams(params);
    }
}