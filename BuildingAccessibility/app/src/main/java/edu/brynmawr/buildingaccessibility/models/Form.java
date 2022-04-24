package edu.brynmawr.buildingaccessibility.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Form {
    String username;
    String building_name;
    String description;
    String _id;

    public Form(JSONObject jsonObject) throws JSONException {
        username = jsonObject.getString("name");
        building_name = jsonObject.getString("building_name");
        description = jsonObject.getString("description");
        _id = jsonObject.getString("_id");
    }

    public static List<Form> fromJsonArray(JSONArray formJsonArray) throws JSONException {
        List<Form> forms = new ArrayList<>();
        for(int i = 0; i < formJsonArray.length(); i++){
            forms.add(new Form(formJsonArray.getJSONObject(i)));
        }
        return forms;
    }
    public String getUserName(){
        return username;
    }
    public String getBuildingName(){
        return building_name;
    }
    public String getDescription(){
        return description;
    }

}
