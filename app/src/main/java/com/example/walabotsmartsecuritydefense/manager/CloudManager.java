package com.example.walabotsmartsecuritydefense.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.activity.BeginLoginActivity;
import com.example.walabotsmartsecuritydefense.table.Announcement;
import com.example.walabotsmartsecuritydefense.table.Notification;
import com.example.walabotsmartsecuritydefense.table.monitoring.Device;
import com.example.walabotsmartsecuritydefense.table.monitoring.MonitoringPointStatus;
import com.example.walabotsmartsecuritydefense.table.monitoring.Room;
import com.example.walabotsmartsecuritydefense.table.monitoring.Zone;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import timber.log.Timber;

import static com.example.walabotsmartsecuritydefense.BaseActivity.getPushToken;

public class CloudManager {
    static String TAG = CloudManager.class.getName();
    private RequestQueue volleyQueue;

    //全域變數
    private Application gv;
    private final Gson gson = new Gson();
    private Context context;

    PreferenceManager preferenceManager;

    public CloudManager(@NonNull Context context) {
//        gv = (Application) context.getApplicationContext();
        volleyQueue = Volley.newRequestQueue(context);
        this.context = context;
        preferenceManager = new PreferenceManager(context);
    }

    //登入
    //api/signin.php?username&password
    public void signinAsync(String url, String username, String password, final Handler handler) {
        Log.d(TAG, "signinAsync: " + url + "username=" + username + "&" + "password=" + password);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "username=" + username + "&" + "password=" + password)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();

                        preferenceManager.setLoginStatus(false);
                        preferenceManager.saveLoginStatus(false);

                        Message message = new Message();
                        String obj = "loginError";
                        message.what = 2;
                        message = handler.obtainMessage(2, obj);
                        handler.sendMessage(message);

                        Log.d(TAG, "onFailure " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + exception.toString());
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {
                        JSONObject json = new JSONObject(myResponse);
                        String strStatus = json.getString("status");
                        Log.d(TAG, "strStatus: " + strStatus);
                        if (strStatus.equals("Success")) {
                            String getApiToken = json.getString("apitoken");
                            String getName = json.getString("name");
                            String getShool = json.getString("customerId");
                            String getRole = json.getString("role");

                            Log.d(TAG, "signinAsync(): api token " + getApiToken);
                            preferenceManager.setApiTokenResult(getApiToken);
                            preferenceManager.saveApiToken(getApiToken);

                            preferenceManager.setLoginStatus(true);
                            preferenceManager.saveLoginStatus(true);

                            preferenceManager.saveUserName(getName);
                            preferenceManager.saveShoolName(getShool);
                            preferenceManager.saveRoleName(getRole);

                            Message message = new Message();
                            String obj = "signinAsync";
                            message.what = 1;
                            message = handler.obtainMessage(1, obj);
                            handler.sendMessage(message);

                            Timber.i("Signin API Result Status, Signin Success");
                        } else {
                            preferenceManager.setLoginStatus(false);
                            preferenceManager.saveLoginStatus(false);

                            Message message = new Message();
                            String obj = "loginError";
                            message.what = 2;
                            message = handler.obtainMessage(2, obj);
                            handler.sendMessage(message);

                            Log.d(TAG, "signin failure!!!");
                            Timber.i("Signin API Result Status, Signin Failure");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //登出
    //api/signout.php?username=xxx&password=xxx&apitoken=xxx
    public void logoutAsync(String url, String username, String password) {
        //apitoken
        Log.d(TAG, "logoutAsync: " + url + "username=" + username + "&" + "password=" + password + "&" + "apitoken=" + preferenceManager.getApiToken());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "username=" + username + "&" + "password=" + password + "&" + "apitoken=" + preferenceManager.getApiToken())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();
                        Log.d(TAG, "onFailure " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + exception.toString());
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {
                        JSONObject json = new JSONObject(myResponse);
                        String strStatus = json.getString("status");
                        Log.d(TAG, "strStatus: " + strStatus);
                        if (strStatus.equals("Success")) {

                            preferenceManager.setApiTokenResult("");
                            preferenceManager.setLoginStatus(false);
                            preferenceManager.setUserName("");


                            Timber.i("signout API Result Status, signout Success");

//                            Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
//                            intent.putExtra("isLogin", isLogin);
//                            startActivity(intent);
//                            finish();

                        } else {
                            Log.d(TAG, "signout failure!!!");

                            Timber.i("signout API Result Status, Signin Failure");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //取得訊息佈告
    //api/dump.php?table=sys_note&apitoken=xxx
    public void sys_noteAsync(String url, final Handler handler) {

        //apitoken
        Log.d(TAG, "sys_noteAsync: " + url + "&" + "apitoken=" + preferenceManager.getApiToken());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "&" + "apitoken=" + preferenceManager.getApiToken())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();
                        Log.d(TAG, "onFailure " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + exception.toString());
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {

                        JSONObject json = new JSONObject(myResponse);
                        String strResult = json.getString("result");
                        Log.d(TAG, "strStatus: " + strResult);

                        JSONArray array = new JSONArray(strResult);


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String id = jsonObject.getString("id"); //serialNumber
                            String category = jsonObject.getString("category");
                            String content = jsonObject.getString("content");
                            String sort = jsonObject.getString("sort");
                            String publishFlag = jsonObject.getString("publishFlag");
                            String publishDate = jsonObject.getString("publishDate");


                            //hannah_test
                            Log.d("sys_noteAsync", "id: " + id);
                            Log.d("sys_noteAsync", "category: " + category);
                            Log.d("sys_noteAsync", "content: " + content);
                            Log.d("sys_noteAsync", "sort: " + sort);
                            Log.d("sys_noteAsync", "publishFlag: " + publishFlag);
                            Log.d("sys_noteAsync", "publishDate: " + publishDate);

                            Announcement announcement = new Announcement();
                            announcement.setSerialNumber(id);
                            announcement.setCategory(category);
                            announcement.setContent(content);
                            announcement.setSort(sort);
                            announcement.setPublishFlag(publishFlag);
                            announcement.setPublishDate(publishDate);
                            announcement.save();

                            Message message = new Message();
                            String obj = "sys_noteAsync";
                            message.what = 1;
                            message = handler.obtainMessage(1, obj);
                            handler.sendMessage(message);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //取得區域
    //api/dump.php?table=zone&apitoken=xxx
    public void zoneAsync(String url, final Handler handler) {
        //apitoken
        Log.d(TAG, "zoneAsync: " + url + "&" + "apitoken=" + preferenceManager.getApiToken());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "&" + "apitoken=" + preferenceManager.getApiToken())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();
                        Log.d(TAG, "onFailure " + "zoneAsync: " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + "zoneAsync: " + exception.toString());
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + "zoneAsync: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {

                        JSONObject json = new JSONObject(myResponse);
                        String strResult = json.getString("result");
                        Log.d(TAG, "strStatus: " + strResult);

                        JSONArray array = new JSONArray(strResult);


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String id = jsonObject.getString("id"); //serialNumber
                            String customerId = jsonObject.getString("customerId");
                            String zoneName = jsonObject.getString("zoneName");
                            String note = jsonObject.getString("note");
                            String activeFlag = jsonObject.getString("activeFlag");
                            String createTime = jsonObject.getString("createTime");
                            String updateTime = jsonObject.getString("updateTime");


                            //hannah_test
                            Log.d("ttttt: zoneAsync", "id: " + id);
                            Log.d("ttttt: zoneAsync", "customerId: " + customerId);
                            Log.d("ttttt: zoneAsync", "zoneName: " + zoneName);
                            Log.d("ttttt: zoneAsync", "note: " + note);
                            Log.d("ttttt: zoneAsync", "activeFlag: " + activeFlag);
                            Log.d("ttttt: zoneAsync", "createTime: " + createTime);
                            Log.d("ttttt: zoneAsync", "updateTime: " + updateTime);

                            Zone zone = new Zone();
                            zone.setSerialNumber(id);
                            zone.setCustomerId(customerId);
                            zone.setZoneName(zoneName);
                            zone.setNote(note);
                            zone.setActiveFlag(activeFlag);
                            zone.setCreateTime(createTime);
                            zone.setUpdateTime(updateTime);
                            zone.save();

                            Message message=new Message();
                            String obj = "zoneAsync";
                            message.what=1;
                            message = handler.obtainMessage(1, obj);
//                            message.obj=werther;
                            handler.sendMessage(message);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //取得設施[監測點]
    //api/dump.php?table=room&apitoken=xxx
    public void roomAsync(String url, final Handler handler) {

        LitePal.deleteAll(Room.class);

        //apitoken
        Log.d(TAG, "roomAsync: " + url + "&" + "apitoken=" + preferenceManager.getApiToken());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "&" + "apitoken=" + preferenceManager.getApiToken())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();
                        Log.d(TAG, "onFailure " + "roomAsync: " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + "roomAsync: " + exception.toString());
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + "roomAsync: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {

                        JSONObject json = new JSONObject(myResponse);
                        String strResult = json.getString("result");
                        Log.d(TAG, "strStatus: " + strResult);

                        JSONArray array = new JSONArray(strResult);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String id = jsonObject.getString("id"); //serialNumber
                            String customerId = jsonObject.getString("customerId");
                            String zoneName = jsonObject.getString("zoneName");
                            String roomNumber = jsonObject.getString("roomNumber");
                            String roomType = jsonObject.getString("roomType");
                            String deviceCount = jsonObject.getString("deviceCount");
                            String caregiverList = jsonObject.getString("caregiverList");
                            String residentList = jsonObject.getString("residentList");
                            String digit2 = jsonObject.getString("digit2");
                            String style = jsonObject.getString("style");
                            String note = jsonObject.getString("note");
                            String activeFlag = jsonObject.getString("activeFlag");
                            String createTime = jsonObject.getString("createTime");
                            String updateTime = jsonObject.getString("updateTime");


                            //hannah_test
                            Log.d("ttttt: roomAsync", "id: " + id);
                            Log.d("ttttt: roomAsync", "customerId: " + customerId);
                            Log.d("ttttt: roomAsync", "zoneName: " + zoneName);
                            Log.d("ttttt: roomAsync", "roomNumber: " + roomNumber);
                            Log.d("ttttt: roomAsync", "roomType: " + roomType);
                            Log.d("ttttt: roomAsync", "deviceCount: " + deviceCount);
                            Log.d("ttttt: roomAsync", "caregiverList: " + caregiverList);
                            Log.d("ttttt: roomAsync", "residentList: " + residentList);
                            Log.d("ttttt: roomAsync", "digit2: " + digit2);
                            Log.d("ttttt: roomAsync", "style: " + style);
                            Log.d("ttttt: roomAsync", "note: " + note);
                            Log.d("ttttt: roomAsync", "activeFlag: " + activeFlag);
                            Log.d("ttttt: roomAsync", "createTime: " + createTime);
                            Log.d("ttttt: roomAsync", "updateTime: " + updateTime);


                            Room room = new Room();
                            room.setSerialNumber(id);
                            room.setCustomerId(customerId);
                            room.setZoneName(zoneName);
                            room.setRoomNumber(roomNumber);
                            room.setRoomType(roomType);
                            room.setDeviceCount(deviceCount);
                            room.setCaregiverList(caregiverList);
                            room.setResidentList(residentList);
                            room.setDigit2(digit2);
                            room.setStyle(style);
                            room.setNote(note);
                            room.setActiveFlag(activeFlag);
                            room.setCreateTime(createTime);
                            room.setUpdateTime(updateTime);
                            room.save();

                            Message message=new Message();

                            String obj = "roomAsync";
                            message.what = 2;
                            message = handler.obtainMessage(2, obj);
//                            message.obj=werther;
                            handler.sendMessage(message);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //取得設備:
    //api/dump.php?table=device&apitoken=xxx
    public void deviceAsync(String url) {

        LitePal.deleteAll(Device.class);

        //apitoken
        Log.d(TAG, "deviceAsync: " + url + "&" + "apitoken=" + preferenceManager.getApiToken());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "&" + "apitoken=" + preferenceManager.getApiToken())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();
                        Log.d(TAG, "onFailure " + "deviceAsync: " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + "deviceAsync: " + exception.toString());
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + "deviceAsync: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {

                        JSONObject json = new JSONObject(myResponse);
                        String strResult = json.getString("result");
                        Log.d(TAG, "strStatus: " + strResult);

                        JSONArray array = new JSONArray(strResult);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String id = jsonObject.getString("id"); //serialNumber
                            String customerId = jsonObject.getString("customerId");
                            String roomId = jsonObject.getString("roomId");
                            String roomNumber = jsonObject.getString("roomNumber");
                            String patternId = jsonObject.getString("patternId");
                            String deviceId = jsonObject.getString("deviceId");
                            String deviceSerial = jsonObject.getString("deviceSerial");
                            String deviceMacAddress = jsonObject.getString("deviceMacAddress");
                            String deviceType = jsonObject.getString("deviceType");
                            String deviceConfig = jsonObject.getString("deviceConfig");
                            String deviceModel = jsonObject.getString("deviceModel");
                            String firmwareVersion = jsonObject.getString("firmwareVersion");
                            String caregiverList = jsonObject.getString("caregiverList");
                            String note = jsonObject.getString("note");
                            String lastStateTime = jsonObject.getString("lastStateTime");
                            String connectFlag = jsonObject.getString("connectFlag");
                            String rebootFlag = jsonObject.getString("rebootFlag");
                            String activeFlag = jsonObject.getString("activeFlag");
                            String createTime = jsonObject.getString("createTime");
                            String updateTime = jsonObject.getString("updateTime");

                            //hannah_test
                            Log.d("ttttt: deviceAsync", "id: " + id);
                            Log.d("ttttt: deviceAsync", "customerId: " + customerId);
                            Log.d("ttttt: deviceAsync", "roomId: " + roomId);
                            Log.d("ttttt: deviceAsync", "roomNumber: " + roomNumber);
                            Log.d("ttttt: deviceAsync", "patternId: " + patternId);
                            Log.d("ttttt: deviceAsync", "deviceId: " + deviceId);
                            Log.d("ttttt: deviceAsync", "deviceSerial: " + deviceSerial);
                            Log.d("ttttt: deviceAsync", "deviceMacAddress: " + deviceMacAddress);
                            Log.d("ttttt: deviceAsync", "deviceType: " + deviceType);
                            Log.d("ttttt: deviceAsync", "deviceConfig: " + deviceConfig);
                            Log.d("ttttt: deviceAsync", "deviceModel: " + deviceModel);
                            Log.d("ttttt: deviceAsync", "firmwareVersion: " + firmwareVersion);
                            Log.d("ttttt: deviceAsync", "caregiverList: " + caregiverList);
                            Log.d("ttttt: deviceAsync", "note: " + note);
                            Log.d("ttttt: deviceAsync", "lastStateTime: " + lastStateTime);
                            Log.d("ttttt: deviceAsync", "connectFlag: " + connectFlag);
                            Log.d("ttttt: deviceAsync", "rebootFlag: " + rebootFlag);
                            Log.d("ttttt: deviceAsync", "activeFlag: " + activeFlag);
                            Log.d("ttttt: deviceAsync", "createTime: " + createTime);
                            Log.d("ttttt: deviceAsync", "updateTime: " + updateTime);


                            Device device = new Device();
                            device.setSerialNumber(id);
                            device.setCustomerId(customerId);
                            device.setRoomId(roomId);
                            device.setRoomNumber(roomNumber);
                            device.setPatternId(patternId);
                            device.setDeviceId(deviceId);
                            device.setDeviceSerial(deviceSerial);
                            device.setDeviceMacAddress(deviceMacAddress);
                            device.setDeviceType(deviceType);
                            device.setDeviceConfig(deviceConfig);
                            device.setDeviceModel(deviceModel);
                            device.setFirmwareVersion(firmwareVersion);
                            device.setCaregiverList(caregiverList);
                            device.setNote(note);
                            device.setLastStateTime(lastStateTime);
                            device.setConnectFlag(connectFlag);
                            device.setRebootFlag(rebootFlag);
                            device.setActiveFlag(activeFlag);
                            device.setCreateTime(createTime);
                            device.setUpdateTime(updateTime);
                            device.save();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }



    //上傳推播token
    //api/pushtoken.php?username=xhwg85&os=2&pushtoken=xxx
    //os: 1 iOS; 2 Android
    public void pushtokenAsync(String url, String username) {
        preferenceManager.setPushtoken(true);
        preferenceManager.savePushtoken(true);

        Log.d(TAG, "notification_copyAsync: " + url + username + "&os=2&pushtoken=" + getPushToken());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + username + "&os=2&pushtoken=" + getPushToken())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();
                        Log.d(TAG, "onFailure " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + exception.toString());
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + myResponse);

                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {
                        JSONObject json = new JSONObject(myResponse);
                        String strStatus = json.getString("status");
                        Log.d(TAG, "strStatus: " + strStatus);
                        if (strStatus.equals("Success")) {

                            preferenceManager.setPushtoken(true);
                            preferenceManager.savePushtoken(true);

                            Timber.i("send pushtoken Result Status, send Success");
                        } else {
                            preferenceManager.setPushtoken(false);
                            preferenceManager.savePushtoken(false);

                            Log.d(TAG, "send pushtoken failure!!!");
                            Timber.i("send pushtoken API Result Status, send Failure");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    //取得異常通知
    //api/dump.php?table=notification_copy&username=xxx&apitoken=xxx
    public void notification_copyAsync(String url, String username) {

        LitePal.deleteAll(Notification.class);

        //apitoken
        Log.d(TAG, "notification_copyAsync: " + url + "&" + "username=" + username + "&apitoken=" + preferenceManager.getApiToken());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "&" + "username=" + username + "&apitoken=" + preferenceManager.getApiToken())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();
                        Log.d(TAG, "onFailure " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + exception.toString());
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    try {

                        JSONObject json = new JSONObject(myResponse);
                        String strResult = json.getString("result");
                        Log.d(TAG, "strStatus: " + strResult);

                        JSONArray array = new JSONArray(strResult);


                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String id = jsonObject.getString("id"); //serialNumber
                            String source = jsonObject.getString("source");
                            String username = jsonObject.getString("username");
                            String readFlag = jsonObject.getString("readFlag");
                            String createTime = jsonObject.getString("createTime");
                            String updateTime = jsonObject.getString("updateTime");
                            String customerId = jsonObject.getString("customerId");
                            String roomId = jsonObject.getString("roomId");
                            String category = jsonObject.getString("category");
                            String title = jsonObject.getString("title");
                            String content = jsonObject.getString("content");
                            String link = jsonObject.getString("link");

                            //hannah_test
                            Log.d("notification_copyAsync", "id: " + id);
                            Log.d("notification_copyAsync", "source: " + source);
                            Log.d("notification_copyAsync", "username: " + username);
                            Log.d("notification_copyAsync", "readFlag: " + readFlag);
                            Log.d("notification_copyAsync", "createTime: " + createTime);
                            Log.d("notification_copyAsync", "updateTime: " + updateTime);
                            Log.d("notification_copyAsync", "customerId: " + customerId);
                            Log.d("notification_copyAsync", "roomId: " + roomId);
                            Log.d("notification_copyAsync", "category: " + category);
                            Log.d("notification_copyAsync", "title: " + title);
                            Log.d("notification_copyAsync", "content: " + content);
                            Log.d("notification_copyAsync", "link: " + link);


                            Notification notification = new Notification();
                            notification.setSerialNumber(id);
                            notification.setSource(source);
                            notification.setUsername(username);
                            notification.setReadFlag(readFlag);
                            notification.setCreateTime(createTime);
                            notification.setUpdateTime(updateTime);
                            notification.setCustomerId(customerId);
                            notification.setRoomId(roomId);
                            notification.setCategory(category);
                            notification.setTitle(title);
                            notification.setContent(content);
                            notification.setLink(link);
                            notification.save();

//                            Message message=new Message();
//                            String obj = "notification_copyAsync";
//                            message.what = 3;
//                            message = handler.obtainMessage(3, obj);
//                            handler.sendMessage(message);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //即時監測
    //api/status.php?apitoken=xxx
    public void statusAsync(String url, final Handler handler){
        LitePal.deleteAll(MonitoringPointStatus.class);

        //apitoken
        Log.d(TAG, "statusAsync: " + url + "apitoken=" + preferenceManager.getApiToken());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "apitoken=" + preferenceManager.getApiToken())
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                try {
                    if (request != null) {
                        final String myRequest = request.body().toString();

                        Log.d(TAG, "onFailure " + "statusAsync: " + myRequest + " ; " + e.toString());
                    }
                }catch (Exception exception) {
                    Log.d(TAG, "exception: " + "statusAsync: " + exception.toString());
                }
            }


            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d(TAG, "myResponse: " + "statusAsync: " + myResponse);
                if (response.isSuccessful()) {//回調的方法執行在子線程。

                    //no1:第一筆資料
                    //Gson parser response.
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jsonElement = jsonParser.parse(myResponse);

                    //get array:rooms
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    String name = jsonObject.get("result").getAsString();
                    JsonArray roomsJsonArray = jsonObject.get("rooms").getAsJsonArray();

                    Log.d("statusAsync", "name: " + name);
                    Log.d("statusAsync", "roomsJsonArray: " + roomsJsonArray);
                    Log.d("statusAsync", "roomsJsonArray 00: " + roomsJsonArray.get(0));

                    //Gson parser roomsJsonArray.
                    JsonParser jsonParser1 = new JsonParser();
                    JsonElement jsonElement1 = jsonParser1.parse(String.valueOf(roomsJsonArray.get(0)));

                    //get object:rooms
                    JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
                    String zoneName = jsonObject1.get("zoneName").getAsString();
                    String roomId = jsonObject1.get("roomId").getAsString();
                    String roomNumber = jsonObject1.get("roomNumber").getAsString();
                    Log.d("ggggg2", "zoneName: " + zoneName);//no1
                    Log.d("ggggg2", "roomId: " + roomId);//no1
                    Log.d("ggggg2", "roomNumber: " + roomNumber);//no1

                    //get array:devices
                    JsonArray roomsJsonArray1 = jsonObject1.get("devices").getAsJsonArray();
                    String devices1 =  roomsJsonArray1.get(0).toString();

                    //Gson parser devices.
                    JsonParser jsonParser2 = new JsonParser();
                    JsonElement jsonElement2 = jsonParser2.parse(devices1);

                    //get object:devices
                    JsonObject jsonObject2 = jsonElement2.getAsJsonObject();
                    String deviceId = jsonObject2.get("deviceId").getAsString();
                    String deviceType = jsonObject2.get("deviceType").getAsString();
                    String connectFlag = jsonObject2.get("connectFlag").getAsString();
                    String presence = jsonObject2.get("presence").getAsString();
                    String datetime = jsonObject2.get("datetime").getAsString();
                    Log.d("ggggg2-1", "deviceId: " + deviceId);//no1
                    Log.d("ggggg2-1", "deviceType: " + deviceType);//no1
                    Log.d("ggggg2-1", "connectFlag: " + connectFlag);//no1
                    Log.d("ggggg2-1", "presence: " + presence);//no1
                    Log.d("ggggg2-1", "datetime: " + datetime);//no1

                    //save db.
                    MonitoringPointStatus monitoringPointStatus = new MonitoringPointStatus();
                    monitoringPointStatus.setZoneName(zoneName);
                    monitoringPointStatus.setRoomId(roomId);
                    monitoringPointStatus.setRoomNumber(roomNumber);
                    monitoringPointStatus.setDeviceId(deviceId);
                    monitoringPointStatus.setDeviceType(deviceType);
                    monitoringPointStatus.setConnectFlag(connectFlag);
                    monitoringPointStatus.setPresence(presence);
                    monitoringPointStatus.setDatetime(datetime);
                    monitoringPointStatus.save();


                    //no2:第一筆資料之後的資料集
                    Iterator<JsonElement> iterator = roomsJsonArray.iterator();
                    while(iterator.hasNext()) {
                        System.out.println(iterator.next());
                        Log.d("statusAsync", "iterator.next(): " + iterator.next());

                        //Gson parser roomsJsonArray.
                        JsonParser jsonParser3 = new JsonParser();
                        JsonElement jsonElement3 = jsonParser3.parse(String.valueOf(iterator.next()));

                        JsonObject jsonObject3 = jsonElement3.getAsJsonObject();
                        String zoneName2 = jsonObject3.get("zoneName").getAsString();
                        String roomId2 = jsonObject3.get("roomId").getAsString();
                        String roomNumber2 = jsonObject3.get("roomNumber").getAsString();
                        Log.d("ggggg2-1", "zoneName2: " + zoneName2);//no2
                        Log.d("ggggg2-1", "roomId2: " + roomId2);//no2
                        Log.d("ggggg2-1", "roomNumber2: " + roomNumber2);//no2

                        //get array:devices
                        JsonArray roomsJsonArray2 = jsonObject3.get("devices").getAsJsonArray();
                        String devices2 = roomsJsonArray2.get(0).toString();

                        //Gson parser devices.
                        JsonParser jsonParser4 = new JsonParser();
                        JsonElement jsonElement4 = jsonParser4.parse(devices2);

                        //get object:devices
                        JsonObject jsonObject4 = jsonElement4.getAsJsonObject();
                        String deviceId2 = jsonObject4.get("deviceId").getAsString();
                        String deviceType2 = jsonObject4.get("deviceType").getAsString();
                        String connectFlag2 = jsonObject4.get("connectFlag").getAsString();
                        String presence2 = jsonObject4.get("presence").getAsString();
                        String datetime2 = jsonObject4.get("datetime").getAsString();
                        Log.d("ggggg2-3", "deviceId2: " + deviceId2);//no2
                        Log.d("ggggg2-3", "deviceType: " + deviceType2);//no2
                        Log.d("ggggg2-3", "connectFlag: " + connectFlag2);//no2
                        Log.d("ggggg2-3", "presence: " + presence2);//no2
                        Log.d("ggggg2-3", "datetime: " + datetime2);//no2

                        //save db.
                        MonitoringPointStatus monitoringPointStatus2 = new MonitoringPointStatus();
                        monitoringPointStatus2.setZoneName(zoneName2);
                        monitoringPointStatus2.setRoomId(roomId2);
                        monitoringPointStatus2.setRoomNumber(roomNumber2);
                        monitoringPointStatus2.setDeviceId(deviceId2);
                        monitoringPointStatus2.setDeviceType(deviceType2);
                        monitoringPointStatus2.setConnectFlag(connectFlag2);
                        monitoringPointStatus2.setPresence(presence2);
                        monitoringPointStatus2.setDatetime(datetime2);
                        monitoringPointStatus2.save();
                    }
                        Message message=new Message();
                        String obj = "statusAsync";
                        message.what = 3;
                        message = handler.obtainMessage(3, obj);
                        handler.sendMessage(message);

                }

            }

        });
    }
}
