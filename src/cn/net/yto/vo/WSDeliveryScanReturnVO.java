
package cn.net.yto.vo;

// For WSDeliveryScanReturn, Field name should same as network interface
public class WSDeliveryScanReturnVO {
    public String failMessage;

    // 1 success, -1 fail
    public int retVal;

    // Example, "402882b12e9e464e012e9eb5fe7f000a"
    public String scanId;

    // 0�����ǵ�����1����������
    public int invertedPay;

    // 0�����Ǵ��գ�1����������
    public int insteadPay;

    // �˷�
    public float feeAmt;

    // ������
    public float goodsAmount;
}
