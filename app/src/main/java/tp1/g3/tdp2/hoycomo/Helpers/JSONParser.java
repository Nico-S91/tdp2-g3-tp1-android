package tp1.g3.tdp2.hoycomo.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
    public static ArrayList<String> favAthletes = new ArrayList<>();
    public static ArrayList<String> favTeams = new ArrayList<>();
    public static ArrayList<String> listAux = new ArrayList<>();


    public static ArrayList<String> getList(JSONObject obj, String ObjName) {
        listAux.clear();
        try {

            JSONArray arr = obj.getJSONArray(ObjName);
            String s;
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                s = obj.getString("name");
                listAux.add(s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listAux;
    }

    public static ArrayList<String> getEducation(JSONObject obj) {
        favAthletes.clear();
        try {

            JSONArray arr = obj.getJSONArray("education");
            String s;
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                s = obj.getString("school");
                favAthletes.add(s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favAthletes;
    }

    public static ArrayList<String> getFavAthletes(JSONObject obj) {
            favAthletes.clear();
        try {

            JSONArray arr = obj.getJSONArray("favorite_athletes");
            String s;
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                s = obj.getString("name");
                favAthletes.add(s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favAthletes;
    }

    public static ArrayList<String> getFavTeams(JSONObject obj) {
        favTeams.clear();
        try {

            JSONArray arr = obj.getJSONArray("favorite_teams");
            String s;
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                s = obj.getString("name");
                favTeams.add(s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favTeams;
    }

    public static String getProperty(JSONObject obj, String property) {
        String str = "";
        try {

            str = obj.getString(property);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getName(JSONObject obj) {
        String str = "";
        try {

             str = obj.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String getId(JSONObject obj) {
        String str = "";
        try {

            str = obj.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }
}