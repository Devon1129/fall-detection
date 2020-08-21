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
    //ex: http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom
    public static String urlSignin = apiEnvironment + "signin.php?";


    /**
     * 登出:
     Table: user
     Usage: http://60.248.34.228:81/walabot/api/signout.php?username&password&apitoken
     Output1: {"status":"Success"}
     Output2:
     */
    //ex: http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom
    public static String urlSignout = apiEnvironment + "signout.php?";


    /**
     * 取得訊息佈告:
     Table: user
     Usage: http://60.248.34.228:81/walabot/api/dump.php?table=sys_note&apitoken=
     Output1: "result":[{"id":"1","category":"info","content":"新設備連線後請執行[設備註冊]取得設備資料","sort":"10","publishFlag":"1","createDate":"2020-06-10"},
     Output2:
     */
    //ex: http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom
    public static String urlSys_note = apiEnvironment + "dump.php?table=sys_note&";

    /**
     * 取得區域:
     Table: user
     Usage: http://60.248.34.218/walabot/api/dump.php?table=zone&apitoken=
     Output1: {"result":[{"id":"2","customerId":"zhps","zoneName":"停用區域","note":"","activeFlag":"0","createTime":"2020-06-24 17:10:32","updateTime":"2020-08-19 09:40:15"},
     Output2:
     */
    //ex: http://60.248.34.228:81/walabot/api/signin.php?username=xhwg85&password=hwacom
    public static String urlSys_zone = apiEnvironment + "dump.php?table=zone&";


}
