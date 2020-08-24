package com.example.walabotsmartsecuritydefense.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.walabotsmartsecuritydefense.Application;
import com.example.walabotsmartsecuritydefense.MainActivity;
import com.example.walabotsmartsecuritydefense.activity.BeginLoginActivity;
import com.example.walabotsmartsecuritydefense.table.Announcement;
import com.example.walabotsmartsecuritydefense.table.monitoring.Device;
import com.example.walabotsmartsecuritydefense.table.monitoring.Room;
import com.example.walabotsmartsecuritydefense.table.monitoring.Zone;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;

import timber.log.Timber;

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
    //private void getApitokenAsync() {//hannah_test
    public void getApitokenAsync(String url, String username, String password) {
        Log.d(TAG, "getApitokenAsync: " + url + "username=" + username + "&" + "password=" + password);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "username=" + username + "&" + "password=" + password)
//                .url("http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom")//hannah_test
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
                            String getApiToken = json.getString("apitoken");
                            String getName = json.getString("name");
                            String getShool = json.getString("customerId");
                            String getRole = json.getString("role");


                            Log.d(TAG, "getApitokenAsync(): api token " + getApiToken);
                            preferenceManager.saveApiToken(getApiToken);
                            preferenceManager.saveLoginStatus(true);

                            preferenceManager.saveUserName(getName);
                            preferenceManager.saveShoolName(getShool);
                            preferenceManager.saveRoleName(getRole);

                            //範例
                            //mAccount.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));
                            //mAccount.setText(apiToken);//hannah_test

                            Timber.i("Signin API Result Status, Signin Success");

//                            Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
//                            intent.putExtra("isLogin", isLogin);
//                            startActivity(intent);
//                            finish();

                        } else {
                            Log.d(TAG, "signin failure!!!");

                            Timber.i("Signin API Result Status, Signin Failure");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }









//                if (response.isSuccessful()) {//回調的方法執行在子線程。
//                    BeginLoginActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                JSONObject json = new JSONObject(myResponse);
//                                String strStatus = json.getString("status");
//                                Log.d("tttt", "strStatus: " + strStatus);
//                                if (strStatus.equals("Success")) {
//                                    String apiToken = json.getString("apitoken");
//
//                                    Log.d("tttt", "apiToken: " + apiToken);
//                                    //範例
//                                    //mAccount.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));
//                                    //mAccount.setText(apiToken);//hannah_test
//
//                                    Timber.i("Signin API Result Status, Signin Success");
//
//                                    Intent intent = new Intent(BeginLoginActivity.this, MainActivity.class);
//                                    intent.putExtra("isLogin", isLogin);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    Log.d("tttt", "signin failure!!!");
//
//                                    Timber.i("Signin API Result Status, Signin Failure");
//
//                                    Toast.makeText(BeginLoginActivity.this, "登入失敗，無效帳戶或密碼", Toast.LENGTH_LONG).show();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
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
    public void sys_noteAsync(String url) {

        LitePal.deleteAll(Announcement.class);

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
                            String createDate = jsonObject.getString("createDate");


                            //hannah_test
                            Log.d("ttttt", "id: " + id);
                            Log.d("ttttt", "category: " + category);
                            Log.d("ttttt", "content: " + content);
                            Log.d("ttttt", "sort: " + sort);
                            Log.d("ttttt", "publishFlag: " + publishFlag);
                            Log.d("ttttt", "createDate: " + createDate);

                            Announcement announcement = new Announcement();
                            announcement.setSerialNumber(id);
                            announcement.setCategory(category);
                            announcement.setContent(content);
                            announcement.setSort(sort);
                            announcement.setPublishFlag(publishFlag);
                            announcement.setCreateDate(createDate);
                            announcement.save();
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
}
