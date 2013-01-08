package cn.net.yto.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import cn.net.yto.utils.CommonUtils;
import cn.net.yto.vo.ScanruleVO;

public class BarcodeManager {
	private static BarcodeManager sInstance;
	private BasicDataManager mBasicDataManager;
	private Map<String, List<Pattern>> mPatternMap = new HashMap<String, List<Pattern>>();

	private BarcodeManager() {
		mBasicDataManager = BasicDataManager.getInstance();
		init();
	}

	public void reInit(){
		init();
	}
	
	public boolean vaildate(String barcode, int barcodeType){
		List<String> codes = getCodesByBarcodeType(barcodeType);
		boolean result = false;
		
		if(CommonUtils.isEmpty(barcode)){
			return result;
		}
		
		for(String code : codes){
			List<Pattern> patterns = mPatternMap.get(code);
			if(patterns != null){
				for(Pattern p : patterns){
					if(p.matcher(barcode).matches()){
						result = true;
						break;
					}
				}
			}
		}
		if(barcodeType == BarcodeType.DBillCode && !barcode.toUpperCase().startsWith("D")){
			result = false;
		}
		return result;
	}
	
	
	public boolean isReturnBillNoValid(String returnBillNo){
		return vaildate(returnBillNo, BarcodeType.DBillCode);
	}
	
	public boolean isWayBillNoValid(String wayBillNo){
		return vaildate(wayBillNo, BarcodeType.BillCode);
	}
	
	private void init(){
		mPatternMap.clear();
		List<ScanruleVO> lists = mBasicDataManager.getScanruleList();
		for(ScanruleVO sVo : lists){
			addRegex(sVo);
		}
	}
	
	private void addRegex(ScanruleVO vo){
		vo.getCode();
		String pre = vo.getPrefix();
		String post = vo.getPostfix();
		
		int digitLen = vo.getScanLength() - CommonUtils.length(pre) - CommonUtils.length(post);
		
		if(digitLen < 0){
			return;
		}
		
		StringBuffer sb = new StringBuffer('^');
		if(!CommonUtils.isEmpty(pre)){
			sb.append(pre);
		}
		if(digitLen > 0){
			sb.append("\\d{" + digitLen + "}");
		}
		
		if(!CommonUtils.isEmpty(post)){
			sb.append(post);
		}
		sb.append('$');
		Pattern p = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
		if(mPatternMap.get(vo.getCode()) == null){
			ArrayList<Pattern> pl = new ArrayList<Pattern>();
			pl.add(p);
			mPatternMap.put(vo.getCode(), pl);
		} else {
			mPatternMap.get(vo.getCode()).add(p);
		}
	}
	
	public static BarcodeManager getInstance() {
		if(sInstance == null){
			sInstance = new BarcodeManager();
		}
		return sInstance;
	}

	private List<String> getCodesByBarcodeType(int barcodeType) {
		List<String> codes = new ArrayList<String>();
		switch (barcodeType) {
		case BarcodeType.BillCode:
			codes.add("CODE0019");
			codes.add("CODE0001");
			codes.add("CODE0027");
			break;
		case BarcodeType.DBillCode:
			codes.add("CODE0001");
			break;
		case BarcodeType.Bag:
			codes.add("CODE0002");
			break;
		case BarcodeType.Cage:
			codes.add("CODE0003");
			break;
		case BarcodeType.Car:
			codes.add("CODE0004");
			break;
		case BarcodeType.Seal:
			codes.add("CODE0020");
			break;
		case BarcodeType.Station:
			codes.add("CODE0010");
			break;
		case BarcodeType.Employee:
			codes.add("CODE0012");
			break;
		case BarcodeType.Customer:
			codes.add("CODE0011");
			break;
		case BarcodeType.LineNo:
			codes.add("CODE0021");
			break;
		case BarcodeType.Driver:
			codes.add("CODE0025");
			break;
		default:
			// throw new ArgumentOutOfRangeException("BarcodeType");
		}

		return codes;
	}

}
