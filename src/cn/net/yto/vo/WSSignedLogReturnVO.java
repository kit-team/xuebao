
package cn.net.yto.vo;

// For WSSignedLogReturn, Field name should same as network interface 

public class WSSignedLogReturnVO extends BaseResponseMsgVO{

    // Fail reason
    public String failMessage;

    // 1 success, -1 fail
    public int retVal;

    public int id;

}
