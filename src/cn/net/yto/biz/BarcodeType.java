package cn.net.yto.biz;

public interface BarcodeType {
	public static final int BillCode = 0; // 运单
	public static final int DBillCode = 1; // 到付运单
	public static final int ReturnBillCode = 2;// 回单
	public static final int Bag = 3; // 包签
	public static final int Cage = 4;// 笼签
	public static final int Car = 5;// 车签
	public static final int Seal = 6;// 铅封
	public static final int Station = 7;// 网点编号
	public static final int Employee = 8; // 员工编号
	public static final int Customer = 9;// 用户编号
	public static final int LineNo = 10;// 线路编号
	public static final int Driver = 11;// 驾驶员编号
	public static final int Unknow = 12;
}
