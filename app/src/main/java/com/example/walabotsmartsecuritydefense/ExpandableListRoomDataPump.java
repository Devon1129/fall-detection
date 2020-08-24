package com.example.walabotsmartsecuritydefense;

import android.util.Log;

import com.example.walabotsmartsecuritydefense.manager.CloudManager;
import com.example.walabotsmartsecuritydefense.table.monitoring.Room;
import com.example.walabotsmartsecuritydefense.table.monitoring.Zone;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListRoomDataPump {


    public static HashMap<String, List<String>> getData() {

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        /*
          範例
         */
//        List<String> cricket = new ArrayList<String>();
//        cricket.add("5:47 下午，持續時間 - 00:00");///
//        cricket.add("3:28 下午，持續時間 - 00:08");///
//        cricket.add("Australia");
//        cricket.add("England");
//        cricket.add("South Africa");

//        List<String> football = new ArrayList<String>();
//        football.add("2:26 下午，持續時間 - 00:00");
//        football.add("12:34 下午，持續時間 - 00:00");
//        football.add("11:52 下午，持續時間 - 00:00");
//        football.add("07:03 上午，持續時間 - 00:00");
//        football.add("05:17 上午，持續時間 - 00:00");

//        List<String> basketball = new ArrayList<String>();
//        basketball.add("United States");
//        basketball.add("Spain");
//        basketball.add("Argentina");
//        basketball.add("France");
//        basketball.add("Russia");


        List<Zone> zone = LitePal.findAll(Zone.class);
        //List<Room> room = LitePal.findAll(Room.class);


        //hannah_test
        for (Zone zones : zone) {

            Log.d("ExpandableList", "zone serialNumber is " + zones.getSerialNumber());
            Log.d("ExpandableList", "zone zoneName is " + zones.getZoneName());
            Log.d("ExpandableList", "zone customerId() is " + zones.getCustomerId());


            //查詢指定區域(zonename)的相關欄位(roomnumber、zonename)
            List<Room> roomList =
                    LitePal.select( "roomnumber", "zonename").where("zonename = ?", zones.getZoneName()).find(Room.class);

            Log.d("ExpandableList", "roomList.size(); " + roomList.size());


            List<String> cricket = new ArrayList<String>();
            for (int i = 0; i < roomList.size(); i++) {

                Log.d("ExpandableList", "zones.getZoneName(): " + zones.getZoneName());
                Log.d("ExpandableList", "roomList.get(i).getZoneName(): " + roomList.get(i).getZoneName());

                /*
                  區域(zone)的區域名稱(zoneName)，
                  如果與房間(room)的區域名稱(zoneName)相同，
                  就取房間的房間名稱(roomNumber)加入群組
                 */
                if (zones.getZoneName().equals(roomList.get(i).getZoneName())) {

                    cricket.add(roomList.get(i).getRoomNumber());

                    Log.d("ExpandableList", "ssss: " + "roomList.get(i).getRoomNumber(): " + roomList.get(i).getRoomNumber());

                    Log.d("ExpandableList", "ssss: " + "zones.getZoneName() " + zones.getZoneName());

                    expandableListDetail.put(zones.getZoneName(), cricket);

                }
            }
        }


         /*
          範例
         */
//        expandableListDetail.put("星期五 19/06/2020", cricket);///
//        expandableListDetail.put("星期三 17/06/2020", football);
        //expandableListDetail.put("BASKETBALL TEAMS", basketball);
        return expandableListDetail;
    }
}
