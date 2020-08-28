package com.example.walabotsmartsecuritydefense.ui.monitoring;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.walabotsmartsecuritydefense.adapter.MonitoringExpandableListAdapter;
import com.example.walabotsmartsecuritydefense.adapter.RoomExpandableListAdapter;
import com.example.walabotsmartsecuritydefense.group.Child;
import com.example.walabotsmartsecuritydefense.group.Parent;
import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.table.monitoring.Device;
import com.example.walabotsmartsecuritydefense.table.monitoring.MonitoringPointStatus;
import com.example.walabotsmartsecuritydefense.table.monitoring.Room;
import com.example.walabotsmartsecuritydefense.table.monitoring.Zone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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


    ArrayList<Parent> groups;
    ExpandableListView listView;
    MonitoringExpandableListAdapter adapter;
//    List<String> expandableListTitle;
//    HashMap<String, List<String>> expandableListDetail;


    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){ //zoneAsync
                case 1:
//                    expandableListDetail = ExpandableListRoomDataPump.getData();
//                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
//                    expandableListAdapter = new RoomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
//                    expandableListView.setAdapter(expandableListAdapter);
//                    setListViewHeightBasedOnChildren(expandableListView);
                    break;
                case 2: //roomAsync
//                    expandableListDetail = ExpandableListRoomDataPump.getData();
//                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
//                    expandableListAdapter = new RoomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
//                    expandableListView.setAdapter(expandableListAdapter);
//                    setListViewHeightBasedOnChildren(expandableListView);
                    break;
                case 3: //statusAsync
                    groups = new ArrayList<Parent>();

                    List<MonitoringPointStatus> monitoringPointStatuses = LitePal.findAll(MonitoringPointStatus.class);
                    Log.d("MoniotringF", "monitoringPointStatuses size: " + monitoringPointStatuses.size());

                    String nextZName = "";
                    for (MonitoringPointStatus mPS : monitoringPointStatuses) {
                        String roomId = mPS.getRoomId();
                        String zoneName = mPS.getZoneName();
                        String deviceId = mPS.getDeviceId();
                        String roomNumber = mPS.getRoomNumber();
                        String connectFlag = mPS.getConnectFlag();
                        String presence = mPS.getPresence();
                        String datetime = mPS.getDatetime();

						if(isContainsZoneName(groups, zoneName)){
                            Log.d("ddddd", "X3");
                            int index = 0;
                            for(Parent group : groups){
                                if(group.zoneName.equals(zoneName)){
                                    Child child = new Child(roomId, deviceId, roomNumber, connectFlag, presence, datetime);
                                    group.addChildrenItem(child);
                                    groups.set(index, group);
                                    Log.d("ddddd", "X1");
                                }
                                index++;
                            }
						}else{
							Child child = new Child(roomId, deviceId, roomNumber, connectFlag, presence, datetime);
							Parent group = new Parent(zoneName);
                            group.addChildrenItem(child);
                            groups.add(group);
                            Log.d("ddddd", "X2");
							
						}
                    }
                    Log.d( "MoniotringF", "handler: case 333" );

                    adapter = new MonitoringExpandableListAdapter(getContext(), groups);
                    listView.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(listView);
                    break;
            }
        }
    };

    //控制ExpandableListView的子項內容
    boolean isContainsZoneName(ArrayList<Parent> groups,String zoneName){
        for(Parent item : groups){
            Log.d( "dddd", "zoneName - " + zoneName);
            Log.d( "dddd", "item.zoneName - " + item.zoneName);
            if(item.zoneName.equals(zoneName)){
                return true;
            }
        }
        return false;
	}
	
	Parent getParent(String zoneName){
		for(Parent item : groups){
			if(item.zoneName == zoneName){
				return item;				
			}			
		}		
		return null;
	}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MoniotringF", "onCreate");

        cloudManager = new CloudManager(getContext());

    }


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

//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

//        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });


//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
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
//                return false;
//            }
//        });





        return root;
    }

    private void initial(View root) {
        listView = (ExpandableListView)root.findViewById(R.id.expandable_listview);

//        expandableListView = (ExpandableListView) root.findViewById(R.id.expandable_listview);

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
//        cloudManager.signinAsync(urlApiSignin, "xhwg85", "hwacom2020");


        //即時監測
        final String urlApiSys_status = Application.urlSys_status;
        cloudManager.statusAsync(urlApiSys_status,handler);

        //取得區域
        LitePal.deleteAll(Zone.class);
        final String urlApiSys_zone = Application.urlSys_zone;
        cloudManager.zoneAsync(urlApiSys_zone, handler);
//
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