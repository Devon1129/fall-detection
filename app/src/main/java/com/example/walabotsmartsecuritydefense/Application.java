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
    //hannah_test: apitoken: 3353685c416b191ba36ccf8d9e9a57d80d75136e722dedd19132ee595cdc9c4b
    //ex: http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom
    public static String urlSignin = apiEnvironment + "signin.php?";


    /**
     * 登出:
     Table: user
     Usage: http://60.248.34.228:81/walabot/api/signout.php?username&password&apitoken
     Output1: {"status":"Success"}
     Output2:
     */
    //hannah_test: apitoken: 3353685c416b191ba36ccf8d9e9a57d80d75136e722dedd19132ee595cdc9c4b
    //ex: http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom
    public static String urlSignout = apiEnvironment + "signout.php?";


    /**
     * 取得訊息佈告:
     Table: user
     Usage: http://60.248.34.228:81/walabot/api/dump.php?table=sys_note&apitoken=
     Output1: {"status":"Success"}
     Output2:
     */
    //hannah_test: apitoken: 3353685c416b191ba36ccf8d9e9a57d80d75136e722dedd19132ee595cdc9c4b
    //ex: http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom
    public static String urlSys_note = apiEnvironment + "dump.php?table=sys_note&";


}
