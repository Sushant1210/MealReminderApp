package com.example.mealreminder;
/*
 * Created by Sushant Kale 22-05-2021.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String url = "https://naviasmart.health/dummy/";
    ProgressDialog dialog;
    private ExpandableListView expList;
    private ExpandableListAdapter expandableListAdapter;
    private ArrayList<String> listDataHeader, listDataHeaderTemp;
    private HashMap<String, ArrayList<String>> listDataChild;
    private ArrayList<String> sundayDiet, mondayDiet, tuesdayDiet, wednesdayDiet, thursdayDiet, fridayDiet, saturdayDiet;
    private ArrayList<String> daysOfWeekArray;
    private ArrayList<ArrayList> dietDayzArray;
    private ArrayList<Integer> daysOfWeekList;
    private ArrayList mealOfTheDay;
    private String diet_duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        makeServiceCall();
        super.onResume();
    }

    // Method to init the views and initialize values.
    private void initViews() {
        expList = findViewById(R.id.lvExp);
        daysOfWeekArray = new ArrayList<>();
        daysOfWeekArray.add("Sunday");
        daysOfWeekArray.add("Monday");
        daysOfWeekArray.add("Tuesday");
        daysOfWeekArray.add("Wednesday");
        daysOfWeekArray.add("Thursday");
        daysOfWeekArray.add("Friday");
        daysOfWeekArray.add("Saturday");
    }

    // Method to make service call and get response
    private void makeServiceCall() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("volleyError is ", "" + volleyError);
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);
    }

    // Method to process the data received from response.
    private void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            Log.e("jsonString is ", "" + jsonString);
            JSONObject week_diet_data = object.optJSONObject("week_diet_data");
            diet_duration = object.optString("diet_duration");
            Log.e("week_diet_data is ", "" + week_diet_data);
            Log.e("diet_duration is ", "" + diet_duration);

            // Logic to check and store diet days from response.
            listDataHeader = new ArrayList<>();

            if (week_diet_data.has("sunday"))
                listDataHeader.add("Sunday");
            if (week_diet_data.has("monday"))
                listDataHeader.add("Monday");
            if (week_diet_data.has("tuesday"))
                listDataHeader.add("Tuesday");
            if (week_diet_data.has("wednesday"))
                listDataHeader.add("Wednesday");
            if (week_diet_data.has("thursday"))
                listDataHeader.add("Thursday");
            if (week_diet_data.has("friday"))
                listDataHeader.add("Friday");
            if (week_diet_data.has("saturday"))
                listDataHeader.add("Saturday");

            // Logic to check and store diet for everyday from response.
            JSONArray jsonArraySunday = object.getJSONObject("week_diet_data").optJSONArray("sunday");
            JSONArray jsonArrayMonday = object.getJSONObject("week_diet_data").optJSONArray("monday");
            JSONArray jsonArrayTuesday = object.getJSONObject("week_diet_data").optJSONArray("tuesday");
            JSONArray jsonArrayWednesday = object.getJSONObject("week_diet_data").optJSONArray("wednesday");
            JSONArray jsonArrayThursday = object.getJSONObject("week_diet_data").optJSONArray("thursday");
            JSONArray jsonArrayFriday = object.getJSONObject("week_diet_data").optJSONArray("friday");
            JSONArray jsonArraySaturday = object.getJSONObject("week_diet_data").optJSONArray("saturday");

            if (jsonArraySunday != null) {
                sundayDiet = new ArrayList<>();
                for (int i = 0; i < jsonArraySunday.length(); i++) {
                    JSONObject foodTimeObject = jsonArraySunday.getJSONObject(i);
                    sundayDiet.add(foodTimeObject.getString("food") + " - " + foodTimeObject.getString("meal_time"));
                }
            }
            if (jsonArrayMonday != null) {
                mondayDiet = new ArrayList<>();
                for (int i = 0; i < jsonArrayMonday.length(); i++) {
                    JSONObject foodTimeObject = jsonArrayMonday.getJSONObject(i);
                    mondayDiet.add(foodTimeObject.getString("food") + " - " + foodTimeObject.getString("meal_time"));
                }
            }
            if (jsonArrayTuesday != null) {
                tuesdayDiet = new ArrayList<>();
                for (int i = 0; i < jsonArrayTuesday.length(); i++) {
                    JSONObject foodTimeObject = jsonArrayTuesday.getJSONObject(i);
                    tuesdayDiet.add(foodTimeObject.getString("food") + " - " + foodTimeObject.getString("meal_time"));
                }
            }
            if (jsonArrayWednesday != null) {
                wednesdayDiet = new ArrayList<>();
                for (int i = 0; i < jsonArrayWednesday.length(); i++) {
                    JSONObject foodTimeObject = jsonArrayWednesday.getJSONObject(i);
                    wednesdayDiet.add(foodTimeObject.getString("food") + " - " + foodTimeObject.getString("meal_time"));
                }
            }
            if (jsonArrayThursday != null) {
                thursdayDiet = new ArrayList<>();
                for (int i = 0; i < jsonArrayThursday.length(); i++) {
                    JSONObject foodTimeObject = jsonArrayThursday.getJSONObject(i);
                    thursdayDiet.add(foodTimeObject.getString("food") + " - " + foodTimeObject.getString("meal_time"));
                }
            }
            if (jsonArrayFriday != null) {
                fridayDiet = new ArrayList<>();
                for (int i = 0; i < jsonArrayFriday.length(); i++) {
                    JSONObject foodTimeObject = jsonArrayFriday.getJSONObject(i);
                    fridayDiet.add(foodTimeObject.getString("food") + " - " + foodTimeObject.getString("meal_time"));
                }
            }
            if (jsonArraySaturday != null) {
                saturdayDiet = new ArrayList<>();
                for (int i = 0; i < jsonArraySaturday.length(); i++) {
                    JSONObject foodTimeObject = jsonArraySaturday.getJSONObject(i);
                    saturdayDiet.add(foodTimeObject.getString("food") + " - " + foodTimeObject.getString("meal_time"));
                }
            }

            Log.e("mondayDiet value is ", "" + mondayDiet);
            Log.e("wednesdayDiet value is ", "" + wednesdayDiet);
            Log.e("thursdayDiet value is ", "" + thursdayDiet);
            dietDayzArray = new ArrayList<>();
            dietDayzArray.add(sundayDiet);
            dietDayzArray.add(mondayDiet);
            dietDayzArray.add(tuesdayDiet);
            dietDayzArray.add(wednesdayDiet);
            dietDayzArray.add(thursdayDiet);
            dietDayzArray.add(fridayDiet);
            dietDayzArray.add(saturdayDiet);
            Log.e("dietDayzArray value is ", "" + dietDayzArray);
            listDataChild = new HashMap<>();

            if(sundayDiet != null)
                listDataChild.put("Sunday", sundayDiet);
            if(mondayDiet != null)
                listDataChild.put("Monday", mondayDiet);
            if(tuesdayDiet != null)
                listDataChild.put("Tuesday", tuesdayDiet);
            if(wednesdayDiet != null)
                listDataChild.put("Wednesday", wednesdayDiet);
            if(thursdayDiet != null)
                listDataChild.put("Thursday", thursdayDiet);
            if(fridayDiet != null)
                listDataChild.put("Friday", fridayDiet);
            if(saturdayDiet != null)
                listDataChild.put("Saturday", saturdayDiet);

            // Set expanded list adapter
            expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            expList.setAdapter(expandableListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();

        // Logic to set the Meal Notifications.
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        int day;
        daysOfWeekList = new ArrayList<>();
        for (int i = 0; i < listDataHeader.size(); i++) {
            day = Utilities.getWeekDaysInNo(listDataHeader.get(i));
            daysOfWeekList.add(day);
        }
        Log.e("daysOfWeekList value is ", "" + daysOfWeekList);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        for (int i = 0; i < daysOfWeekList.size(); i++) {
            mealOfTheDay = dietDayzArray.get(daysOfWeekList.get(i));
            for (int j = 0; j < mealOfTheDay.size(); j++) {
                String food = (String) mealOfTheDay.get(j);
                String meal = food.split("-")[0].trim();
                String meal_time = food.split("-")[1].trim();
                String meal_hour = meal_time.split(":")[0].trim();
                String meal_minute = meal_time.split(":")[1].trim();

                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.setAction(meal + " time");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1253, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                calendar.set(Calendar.DAY_OF_WEEK, (daysOfWeekList.get(i) + 1));
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(meal_hour));
                calendar.set(Calendar.MINUTE, (Integer.parseInt(meal_minute)));
                calendar.set(Calendar.SECOND, 1);

                if (calendar.getTimeInMillis() - 300000 >= System.currentTimeMillis()) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, (calendar.getTimeInMillis() - 300000), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                }

                Log.e("alarm is set for ", "day of week " + (daysOfWeekList.get(i) + 1)
                        + ",hour of the day " + meal_hour + ",minute of the day "
                        + Integer.parseInt(meal_minute) + ", for the meal - " + meal + " and meal time - " + meal_time);
            }
        }
    }
}