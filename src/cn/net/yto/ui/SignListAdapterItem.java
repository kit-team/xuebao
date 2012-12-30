package cn.net.yto.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SignListAdapterItem {
    public String mTrackingNumber = "1234567890";
    public String mSignType = "不确定类型";
    public String mReceipient = "Jackey Chen D";
    public String mSignTime = "2012/12/23";
    public boolean mSelected = false;
    
    public SignListAdapterItem(){
        DateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        mSignTime = formate.format(new Date());
    }
}
