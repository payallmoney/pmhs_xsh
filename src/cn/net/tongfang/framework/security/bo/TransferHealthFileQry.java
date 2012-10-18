package cn.net.tongfang.framework.security.bo;

public class TransferHealthFileQry {
	private String district;
    private String filterKey;
    private String filterValue;
    private String type;
    private String flowType;
    
	public TransferHealthFileQry() {
		super();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getFilterKey() {
		return filterKey;
	}

	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TransferHealthFileQry(String district, String filterKey,
			String filterValue, String type,String flowType) {
		super();
		this.district = district;
		this.filterKey = filterKey;
		this.filterValue = filterValue;
		this.type = type;
		this.flowType = flowType;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
    
    
}
