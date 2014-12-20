package cn.net.tongfang.framework.security.bo;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class NewQryCondition {
	private static final Logger logger = Logger.getLogger(NewQryCondition.class);
	private static String PATTERN = "yyyy-MM-dd";
	private static String PATTERN_FALLBACK = "yyyyMMdd";
	private static String PATTERN_HOUR_FALLBACK = "yyyyMMddHHmmss";
	private static Pattern p1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	private static Pattern p2 = Pattern.compile("\\d{8}");
	private static Pattern p3 = Pattern.compile("\\d{14}");
	private String district;
	private List<NewCondition> conditions;

	public List<NewCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<NewCondition> conditions) {
		this.conditions = conditions;
	}

	public String getValueByName(String name) {
		for (int i = 0; i < conditions.size(); i++) {
			if (name.equals(conditions.get(i).getFilterKey()) && StringUtils.hasText(conditions.get(i).getFilterVal())) {
				return conditions.get(i).getFilterVal();
			}
		}
		return null;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getParamsStr() throws Exception {
		String retstr = "";
		for (NewCondition condition : this.conditions) {
			if (!condition.isNotsql()) {
				String strvalue = condition.getFilterVal();
				String opt = condition.getOpt();
				if (!StringUtils.hasText(condition.getOpt())) {
					opt = "=";
				}
				System.out.println("=======condition.getType()====="+condition.getType());
				if (StringUtils.hasText(strvalue)) {
					if ("date".equals(condition.getType())) {
						if (strvalue.indexOf("-") > 0) {
							String[] strs = strvalue.split("-");
							String begindate = getdate(strs[0]);
							String enddate = getdate(strs[1]);
							if (begindate != null)
								retstr += " and " + condition.getFilterKey() + " >= " + " '" + begindate + "' ";
							if (enddate != null)
								retstr += " and " + condition.getFilterKey() + " < " + " dateadd(day,1,convert(date, '" + enddate + "')) ";
						} else {
							String date = getdate(strvalue);
							if(date!=null)
								retstr += " and " + condition.getFilterKey() + " = " + " '" + date + "' ";
						}
					}else if ("int".equals(condition.getType())) {
						if (strvalue.indexOf("-") > 0) {
							String[] strs = strvalue.split("-");
							if(StringUtils.hasText(strs[0]))
								retstr += " and " + condition.getFilterKey() + " >= " + " '" + strs[0] + "' ";
							if(StringUtils.hasText(strs[1]))
								retstr += " and " + condition.getFilterKey() + " <= " + "  '" + strs[1] + "'  ";
						} else {
							if(StringUtils.hasText(strvalue))
								retstr += " and " + condition.getFilterKey() + " = " + " '" + strvalue + "' ";
						}
					} else {
						if ("llike".equals(opt)) {
							retstr += " and " + condition.getFilterKey() + " like " + " '" + strvalue + "%' ";
						} else if ("rlike".equals(opt)) {
							retstr += " and " + condition.getFilterKey() + " like " + " '%" + strvalue + "' ";
						} else if ("alike".equals(opt)) {
							retstr += " and " + condition.getFilterKey() + " like " + " '%" + strvalue + "%' ";
						} else {
							retstr += " and " + condition.getFilterKey() + opt + " '" + strvalue + "' ";
						}
					}
				}
			}
		}
		return retstr;

	}

	public static String getdate(String strvalue) {
		Object value;
		DateFormat dfFallback = new SimpleDateFormat(PATTERN_FALLBACK);
		try {
			if (p1.matcher(strvalue).matches()) {
				DateFormat df = new SimpleDateFormat(PATTERN);
				value = new Timestamp(df.parse(strvalue).getTime());
			} else if (p2.matcher(strvalue).matches()) {

				value = new Timestamp(dfFallback.parse(strvalue).getTime());
			} else if (p3.matcher(strvalue).matches()) {
				DateFormat dfHourFallback = new SimpleDateFormat(PATTERN_HOUR_FALLBACK);
				value = new Timestamp(dfHourFallback.parse(strvalue).getTime());
			} else {
				long time = Long.parseLong(strvalue);
				value = new Timestamp(time);
			}
			strvalue = dfFallback.format(value);
			return strvalue;
		} catch (Exception ex) {
			logger.error("传入的日期类型为无效的日期值: " + strvalue);
			return null;
		}
	}

}
