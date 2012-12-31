package cn.net.yto.net;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.net.yto.MainApp;
import cn.net.yto.net.UrlType;
import cn.net.yto.net.ZltdHttpClient;
import cn.net.yto.net.ZltdHttpClient.Listener;
import cn.net.yto.utils.LogUtils;
import cn.net.yto.vo.SubmitSignedLogResponseMsgVO;
import cn.net.yto.models.SignedLog;

public class UserService {
	public static final int ROLE_ID_OrdMsg = 1; // ����֪ͨ
	public static final int ROLE_ID_OrdRec = 2; // �ж����ռ�
	public static final int ROLE_ID_NooRec = 3; // �޶����ռ�
	public static final int ROLE_ID_DelRec = 4; // �ռ�����
	public static final int ROLE_ID_RepRec = 5; // �ռ�����
	public static final int ROLE_ID_OrdPus = 6; // �����߰�
	public static final int ROLE_ID_OrdCan = 7; // ����ȡ��
	public static final int ROLE_ID_DeliSca = 8; // �ɼ�����ɨ��
	public static final int ROLE_ID_SigLog = 9; // �ͻ�ǩ��
	public static final int ROLE_ID_DeliPus = 10; // �ɼ��߰�
	public static final int ROLE_ID_Notes = 11; // ��ǩ��¼
	public static final int ROLE_ID_LocPDA = 12; // ����
	public static final int ROLE_ID_Logout = 13; // ע��
	public static final int ROLE_ID_Photo = 14; // ����
	public static final int ROLE_ID_Volumn = 15; // ��������
	public static final int ROLE_ID_Calcul = 16; // ������
	public static final int ROLE_ID_UplLog = 17; // ��־�ϴ�
	public static final int ROLE_ID_Restar = 18; // ��������
	public static final int ROLE_ID_UplDat = 19; // ��������ϴ�
	public static final int ROLE_ID_SysDow = 20; // ϵͳ����
	public static final int ROLE_ID_SerHel = 21; // �����ֲ�
	public static final int ROLE_ID_DeliAre = 22; // ���ͷ�Χ
	public static final int ROLE_ID_Contra = 23; // Υ��Ʒ
	public static final int ROLE_ID_BlaLis = 24; // �ͻ������б�
	public static final int ROLE_ID_FeeQue = 25; // ��׼�˼۲�ѯ
	public static final int ROLE_ID_CusInf = 26; // �ͻ���֤
	public static final int ROLE_ID_ExpTra = 27; // �������
	public static final int ROLE_ID_Notice = 28; // ������Ϣ
	public static final int ROLE_ID_RecStat = 29; // ���ɼ�ͳ��

	// -1ʧ��
	public static final Integer LOGIN_RESULT_FAIL = -1;
	// 1 �ɹ�
	public static final Integer LOGIN_RESULT_SUCCESS = 1;
	// -101 �豸�Ų�����
	public static final Integer LOGIN_RESULT_DEVICE_UNEXIST = -101;
	// -102 �豸�Ų����ã������� �� ����Ϊͣ��
	public static final Integer LOGIN_RESULT_OUT_OF_SERVICE = -102;
	private static final String TAG = "UserService";

	public UserService() {
	}
		
	public static boolean submitSignedLog(SignedLog signedLog, Listener listener) {
		ZltdHttpClient client = new ZltdHttpClient(UrlType.SUBMIT_SIGNEDLOG, signedLog.toVO(),
				listener, SubmitSignedLogResponseMsgVO.class);
		return client.submit(MainApp.getAppContext());
	}
	
	private long parseRights(ArrayList<String> rights) {
		long right = 0;

		if (rights == null || rights.size() < 1) {
			return right;
		}

		for (String item : rights) {
			if ("PDA_OrdMsg".equals(item)) {
				right |= 0x1 << ROLE_ID_OrdMsg;
			} else if ("PDA_OrdRec".equals(item)) {
				right |= 0x1 << ROLE_ID_OrdRec;
			}  else if ("PDA_NooRec".equals(item)) {
				right |= 0x1 << ROLE_ID_NooRec;
			} else if ("PDA_DelRec".equals(item)) {
				right |= 0x1 << ROLE_ID_DelRec;
			} else if ("PDA_RepRec".equals(item)) {
				right |= 0x1 << ROLE_ID_RepRec;
			} 

			else if ("PDA_OrdPus".equals(item)) {
				right |= 0x1 << ROLE_ID_OrdPus;
			} else if ("PDA_OrdCan".equals(item)) {
				right |= 0x1 << ROLE_ID_OrdCan;
			} else if ("PDA_DeliSca".equals(item)) {
				right |= 0x1 << ROLE_ID_DeliSca;
			} else if ("PDA_SigLog".equals(item)) {
				right |= 0x1 << ROLE_ID_SigLog;
			} else if ("PDA_DeliPus".equals(item)) {
				right |= 0x1 << ROLE_ID_DeliPus;
			} 
			
			else if ("PDA_Notes".equals(item)) {
				right |= 0x1 << ROLE_ID_Notes;
			} else if ("PDA_LocPDA".equals(item)) {
				right |= 0x1 << ROLE_ID_LocPDA;
			} else if ("PDA_Logout".equals(item)) {
				right |= 0x1 << ROLE_ID_Logout;
			} else if ("PDA_Photo".equals(item)) {
				right |= 0x1 << ROLE_ID_Photo;
			} else if ("PDA_Volumn".equals(item)) {
				right |= 0x1 << ROLE_ID_Volumn;
			} 
			
			else if ("PDA_Calcul".equals(item)) {
				right |= 0x1 << ROLE_ID_Calcul;
			} else if ("PDA_UplLog".equals(item)) {
				right |= 0x1 << ROLE_ID_UplLog;
			} else if ("PDA_Restar".equals(item)) {
				right |= 0x1 << ROLE_ID_Restar;
			} else if ("PDA_UplDat".equals(item)) {
				right |= 0x1 << ROLE_ID_UplDat;
			} else if ("PDA_SysDow".equals(item)) {
				right |= 0x1 << ROLE_ID_SysDow;
			} 

			else if ("PDA_SerHel".equals(item)) {
				right |= 0x1 << ROLE_ID_SerHel;
			} else if ("PDA_DeliAre".equals(item)) {
				right |= 0x1 << ROLE_ID_DeliAre;
			} else if ("PDA_Contra".equals(item)) {
				right |= 0x1 << ROLE_ID_Contra;
			} else if ("PDA_BlaLis".equals(item)) {
				right |= 0x1 << ROLE_ID_BlaLis;
			} else if ("PDA_FeeQue".equals(item)) {
				right |= 0x1 << ROLE_ID_FeeQue;
			}
			
			else if ("PDA_CusInf".equals(item)) {
				right |= 0x1 << ROLE_ID_CusInf;
			} else if ("PDA_ExpTra".equals(item)) {
				right |= 0x1 << ROLE_ID_ExpTra;
			} else if ("PDA_Notice".equals(item)) {
				right |= 0x1 << ROLE_ID_Notice;
			} else if ("PDA_RecStat".equals(item)) {
				right |= 0x1 << ROLE_ID_RecStat;
			}
		}
		return right;
	}
}
