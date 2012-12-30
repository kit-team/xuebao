
package cn.net.yto.vo;

// For WSSignedLogParam, UrL: m_delivery!SubmitSignedLog.action | m_delivery! updateSignedLog.action
// Field name should same as network interface 

public class WSSignedLogParamVO extends BaseRequestMsgVO{
//    // Staff code
//    public int empCode;
//
//    // 面单号
//    public int waybillNo;
//
//    // 签收时间
//    public String signedTime;
//
//    // 签收数据
//    public String pictureData;
//
//    // "1"- sign success,others - error code for sign fail
//    public int signedState;
//
//    // 满意度
//    public int satisfaction;
//
//    // 异常签收描述
//    public int expSignedDescription;
//
//    // 现金金额
//    public float cashAmount;
//
//    // 刷卡金额
//    public float cardAmount;

    public String id;
    public int isScan;
    public String signOffTypeCode = "";
    public String recieverSignOff = "";
    public String amountCollected = "";
    public String amountAgency = "";
    public String uploadStatu = "";
    public String pdaNumber = "";
    public String getStatus = "";
    public String pictureData = "";
    public String expSignedDescription = "";
    public String waybillNo = "";
    public String signedState = "";
    public String signedStateInfo = "";
    public String empCode = "";
    public String empName = "";
    public String signedTime = "";
    public String satisfaction = "";
    public String isReceiverSignOff = "";
    public String isPicture = "";
    public String retVal = "";
    public String failMessage = "";
}
