package cn.net.yto.vo;

import cn.net.yto.utils.CommonUtils;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CityVO {
	@DatabaseField(id = true)
	private String id;
	@DatabaseField
	private String areaNo;
	@DatabaseField
	private String cityName;
	@DatabaseField
	private String cityPinYin;
	@DatabaseField
	private String parentCityCode;
	/**
	 * 是否有效状态	VALID:有效；INVALID:无效
	 */
	@DatabaseField
	private String status;
	@DatabaseField
	private String centerCode;
	@DatabaseField
	private long versionNo;
	@DatabaseField
	private int cityLevel;
	

	public CityVO() {

	}

	public CityVO(String id, String areaNo, String cityName, String cityPinYin,
			String parentCityCode, String status, String centerCode,
			long versionNo) {
		super();
		this.id = id;
		this.areaNo = areaNo;
		this.cityName = cityName;
		this.cityPinYin = cityPinYin;
		this.parentCityCode = parentCityCode;
		this.status = status;
		this.centerCode = centerCode;
		this.versionNo = versionNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityPinYin() {
		return cityPinYin;
	}

	public void setCityPinYin(String cityPinYin) {
		this.cityPinYin = cityPinYin;
	}

	public String getParentCityCode() {
		return parentCityCode;
	}

	public void setParentCityCode(String parentCityCode) {
		this.parentCityCode = parentCityCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
	
	public int getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(int cityLevel) {
		this.cityLevel = cityLevel;
	}

	@Override
	public String toString() {
		return "" + cityName;
	}
	
	@Override
	public boolean equals(Object anObject) {
		if(anObject == null){
			return false;
		}
		
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof CityVO) {
			CityVO city1 = (CityVO) anObject;
			if(!CommonUtils.isEmpty(id) && id.equals(city1.getId())){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		String s = "" + id;
		return s.hashCode();
	}
}
