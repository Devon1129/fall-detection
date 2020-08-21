package com.example.walabotsmartsecuritydefense;

public class Application {


    public static String apiEnvironment = "http://60.248.34.218/walabot/api/";

    /**
     * 登入:
     Table: user
     Usage: http://60.248.34.228:81/walabot/api/signin.php?username&password
     Output1: {"status":"Success","apitoken":"xxxx..."}
     Output2:
    */
    public static String urlSignin = apiEnvironment + "signin.php?";


    /**
     * 登出:
     Table: user
     Usage: http://60.248.34.228:81/walabot/api/signout.php?username&password&apitoken
     Output1: {"status":"Success"}
     Output2:
     */
    public static String urlSignout = apiEnvironment + "signout.php?";


    /**
     * 取得訊息佈告:
     Table: user
     Usage: http://60.248.34.228:81/walabot/api/dump.php?table=sys_note&apitoken=
     Output1: "result":[{"id":"1","category":"info","content":"新設備連線後請執行[設備註冊]取得設備資料","sort":"10","publishFlag":"1","createDate":"2020-06-10"},
     Output2:
     */
    public static String urlSys_note = apiEnvironment + "dump.php?table=sys_note&";

    /**
     * 取得區域:
     Table: zone
     Usage: http://60.248.34.218/walabot/api/dump.php?table=zone&apitoken=
     Output1: {"result":[{"id":"2","customerId":"zhps","zoneName":"停用區域","note":"","activeFlag":"0","createTime":"2020-06-24 17:10:32","updateTime":"2020-08-19 09:40:15"},
     Output2:
     */
    public static String urlSys_zone = apiEnvironment + "dump.php?table=zone&";

    /**
     * 取得設施[監測點]:
     Table: room
     Usage: http://60.248.34.218/walabot/api/dump.php?table=room&apitoken=
     Output1:{"result":[
        {"id":"5","customerId":"zhps","zoneName":"測試區域","roomNumber":"呆伯特隔間","roomType":"","deviceCount":"","caregiverList":"","residentList":"","digit2":"","style":"","note":"","activeFlag":"1","createTime":"2020-07-03 09:46:12","updateTime":"2020-08-19 10:34:46"},
     Output2:
     */
    public static String urlSys_room = apiEnvironment + "dump.php?table=room&";


    /**
     * 取得設備
     Table: device
     Usage: http://60.248.34.218/walabot/api/dump.php?table=device&apitoken=
     Output1:{"result":[
     {"id":"1","customerId":"zhps","roomId":"5","roomNumber":"","patternId":"1","deviceId":"id_YTQ6YTQ6ZDM6YjU6OWI6NDE","deviceSerial":"","deviceMacAddress":"","deviceType":"2","deviceConfig":"0,1.5,0.3,1.5,0,1.8;0,0,0,0","deviceModel":"WH21BBUS02","firmwareVersion":"1.6.50","caregiverList":"","note":"","lastStateTime":"2020-08-20 15:23:57","connectFlag":"1","rebootFlag":"0","activeFlag":"1","createTime":"2020-06-12 16:55:35","updateTime":"2020-08-20 15:24:01"},
     Output2:
     */
    public static String  urlSys_device = apiEnvironment + "dump.php?table=device&";







}
