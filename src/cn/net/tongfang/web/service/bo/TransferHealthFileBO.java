package cn.net.tongfang.web.service.bo;

import java.sql.Timestamp;
import java.util.List;

public class TransferHealthFileBO {
	private String districtNumber;
	private String transferReason;
	private Timestamp transferTime;
	private List<TransferHealthfileInfo> fileNos;
	
	public TransferHealthFileBO() {
		super();
	}

	public TransferHealthFileBO(String districtNumber, String transferReason,
			Timestamp transferTime, List<TransferHealthfileInfo> fileNos) {
		super();
		this.districtNumber = districtNumber;
		this.transferReason = transferReason;
		this.transferTime = transferTime;
		this.fileNos = fileNos;
	}

	public String getDistrictNumber() {
		return districtNumber;
	}

	public void setDistrictNumber(String districtNumber) {
		this.districtNumber = districtNumber;
	}

	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	public Timestamp getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Timestamp transferTime) {
		this.transferTime = transferTime;
	}

	public List<TransferHealthfileInfo> getFileNos() {
		return fileNos;
	}

	public void setFileNos(List<TransferHealthfileInfo> fileNos) {
		this.fileNos = fileNos;
	}
	
}
