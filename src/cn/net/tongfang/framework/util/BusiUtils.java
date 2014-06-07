package cn.net.tongfang.framework.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BusiUtils {
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
}
