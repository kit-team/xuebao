
package cn.net.yto.vo.message;

// For WSDeliveryScanReturn, Field name should same as network interface
public class WSDeliveryScanReturnVO {
    public String failMessage;

    // 1 success, -1 fail
    public int retVal;

    // Example, "402882b12e9e464e012e9eb5fe7f000a"
    public String scanId;

    // 到付标志,0：不是到付；1：到付类型
    public int invertedPay;

    // 代收标志,0：不是代收；1：代收类型
    public int insteadPay;

    // 运费
    public float feeAmt;

    // 货物金额
    public float goodsAmount;

}