package com.example.walabotsmartsecuritydefense.table;

import org.litepal.crud.LitePalSupport;

/* 取得代碼資料
    codetable.php?apitoken=xxx

 to do: 回傳array，待研究，怎麼處理

    {"result":[
            {"customer":[
            {"zhps":"台北市日新國民小學","tfg":"臺北市立第一女子高級中學"}]},
            {"role":[{"A":"系統管理","M":"管理人員","O":"維運人員"}]},
            {"devicetype":[{"1":"廁所","2":"監控點"}]},
            {"eventtype":[{"fall":"跌倒","presence":"使用情形","connect":"連接"}]},
            {"eventcode":[{"connected":"連線","disconnected":"斷線","unknown":"未知","vacant":"無人","occupied":"有人","timeout":"停留超時","fall_detected":"跌倒警示","fall_confirmed":"跌倒確認","calling":"跌倒告警","canceled":"告警解除","fall_exit":"自行排除","restroom":"廁所","spot":"監測點","in_restroom":"廁所有人","in_spot":"監測點有人","out_restroom":"廁所無人","out_spot":"監測點無人","in_cubicle":"隔間有人","out_cubicle":"隔間無人"}]}]}

 */



public class Codetable extends LitePalSupport {
    //以下為sample
    private int id;
    private String serialNumber;
    private String deviceId;
    private String eventType;
    private String eventCode;
    private String eventTime;
    private String responseTime;
    private String note;
    private String createTime;

}
