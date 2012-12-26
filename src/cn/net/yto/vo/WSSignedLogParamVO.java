
package cn.net.yto.vo;

// For WSSignedLogParam, UrL: m_delivery!SubmitSignedLog.action | m_delivery! updateSignedLog.action
// Field name should same as network interface 

public class WSSignedLogParamVO {
    // Staff code
    public int empCode;

    // �浥��
    public int waybillNo;

    // ǩ��ʱ��
    public String signedTime;

    // ǩ������ ,<ͼƬ>�Ķ��������ݾ���base64�������ַ���
    public String pictureData;

    // "1"- sign success��others - error code for sign fail
    public int signedState;

    // ����� <�����⣬���⣬������>
    public int satisfaction;

    // �쳣ǩ������
    public int expSignedDescription;

    // �ֽ���
    public float cashAmount;

    // ˢ�����
    public float cardAmount;

}
