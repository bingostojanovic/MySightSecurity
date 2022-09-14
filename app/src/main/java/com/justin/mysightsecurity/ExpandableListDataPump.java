package com.justin.mysightsecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> data81 = new ArrayList<String>();
        data81.add("02");

        List<String> data82 = new ArrayList<String>();
        data82.add("01");

        List<String> data83 = new ArrayList<String>();
        data83.add("31");

        List<String> data84 = new ArrayList<String>();
        data84.add("30");

        List<String> data85 = new ArrayList<String>();
        data85.add("29");


        expandableListDetail.put("01,September,2022", data81);
        expandableListDetail.put("02,September,2022", data82);
        expandableListDetail.put("31,August,2022", data83);
        expandableListDetail.put("30,August,2022", data84);
        expandableListDetail.put("29,August,2022", data85);

        return expandableListDetail;
    }
}
