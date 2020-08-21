package com.example.walabotsmartsecuritydefense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListRoomDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("5:47 下午，持續時間 - 00:00");
        cricket.add("3:28 下午，持續時間 - 00:08");
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

        expandableListDetail.put("星期五 19/06/2020", cricket);
//        expandableListDetail.put("星期三 17/06/2020", football);
        //expandableListDetail.put("BASKETBALL TEAMS", basketball);
        return expandableListDetail;
    }
}
