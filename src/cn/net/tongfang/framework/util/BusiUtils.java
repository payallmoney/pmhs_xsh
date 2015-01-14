package cn.net.tongfang.framework.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class BusiUtils {
	private static Pattern p1 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	private static Pattern p2 = Pattern.compile("\\d{8}"); 
	private static Pattern p3 = Pattern.compile("\\d{14}"); 
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 截去字符串结尾的特征串
	 * @param s
	 * @param tailString
	 * @return
	 */
	public static String trimTailSeq (final String s, final String tailString) {
		if ( s == null ) return s;
		String r = s;
		if ( r.endsWith( tailString ) )  {
			r = r.substring(0, r.length() - tailString.length());
			r = trimTailSeq(r, tailString);
		}
		return r;
	}
	
	
	public static Timestamp getChildAge() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 7);
		
		return new Timestamp( cal.getTimeInMillis() );
	}
	
	public static Timestamp parseDate(String datestr) throws Exception{
		if(p1.matcher(datestr).matches()){
			return new Timestamp(sdf1.parse(datestr).getTime());
		}
		if(p2.matcher(datestr).matches()){
			return new Timestamp(sdf2.parse(datestr).getTime());
		}
		if(p3.matcher(datestr).matches()){
			long time = Long.parseLong(datestr);
			return  new Timestamp(time);
		}
		return null;
	}
	
	
	
	public static void insertLog(HibernateDaoSupport hbt ,final String fileno,final String type,final String tablename ,final String userid,final Object oldobj, final Object newobj ){
		final String sqlstr = "insert into log_main values('%s','%s','%s','%s','%s', getdate(),?,?)";
		hbt.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				try{
					SQLQuery filequery = session.createSQLQuery(String.format(sqlstr, UUID.randomUUID(),fileno,type,tablename,userid));
					Gson gson = new GsonBuilder()  
					  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
					  .create(); 
					filequery.setString(0, gson.toJson(oldobj));
					filequery.setString(1, gson.toJson(newobj));
					filequery.executeUpdate();
				}catch(RuntimeException ex){
					ex.printStackTrace();
				}catch(Exception ex){
					ex.printStackTrace();
				}
				return null;
			}
		});
	}
	
	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
	
	public static void copyProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}
}
