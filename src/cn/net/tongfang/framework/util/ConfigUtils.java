package cn.net.tongfang.framework.util;


public class ConfigUtils {
	private SystemInformationUtils sysInfo;

	public void setSysInfo(SystemInformationUtils sysInfo) {
		this.sysInfo = sysInfo;
	}
	public ConfigUtils(SystemInformationUtils systemInformationUtils){
		this.setSysInfo(systemInformationUtils);
//		getIsEncryption();
	}
	/**
	 * 是否加解密
	 * @return
	 */
	public Boolean getIsEncryption(){
		System.out.println("========取得加密值========");
		String val = sysInfo.getVal(4);
		if(val.equals("0")){
			return true;
		}else if(val.equals("1")){
			return false;
		}
		return false;
	}
}
