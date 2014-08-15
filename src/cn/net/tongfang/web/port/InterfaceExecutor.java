package cn.net.tongfang.web.port;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.reflect.MethodUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class InterfaceExecutor {
	public void autoExec(HibernateTemplate hbt){
		// 得到自动执行的接口
		List autos = (List) hbt.execute(new HibernateCallback() {
			public List<String> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session
						.createSQLQuery(" select code,class from map_interface where validdatebegin<=getdate() and validdateend>=getdate() and valid=1 and auto =1 ");
				List<String> list = query.list();
				return list;
			}
		});
		for(Object auto : autos){
			Object[] row = (Object[])auto;
			String code = (String)row[0];
			String classname = (String)row[1];
			try{
				Object inter = Class.forName(classname).newInstance();
				//exec方法执行了从取数到保存的过程
				MethodUtils.invokeMethod(inter, "exec", new Object[]{code,hbt});
			}catch(Exception ex){
				ex.printStackTrace();
				continue;
			}
		}
	}
}
