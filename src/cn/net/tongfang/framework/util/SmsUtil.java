package cn.net.tongfang.framework.util;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.vo.CodTelSendRule;
import cn.net.tongfang.framework.security.vo.SmsStatus;

public class SmsUtil extends HibernateDaoSupport implements ApplicationListener {
	public static int IS_CREATED_FALSE = 0;
	public static int IS_CREATED_TRUE = 1;
	public static int IS_SENDED_FALSE = 0;
	public static int IS_SENDED_TRUE = 1;
	public static int IS_SENDED_ERROR = -1;

	private String host;
	private String name;
	private String pwd;
	private String apiId;
	private String dbName;
	
	private boolean started = false;
	private String processString = "";
	
	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			createMsgJob();
		}
	}

	public void createMsgJob() {
		// TODO 启动时生成当天的任务信息 即Sms_Status 表的数据
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		List isCreated = getHibernateTemplate().find("from SmsStatus where smsdate = ?",today);
		SmsStatus smsstatus = null;
		if (isCreated == null || isCreated.isEmpty()) {
			smsstatus = new SmsStatus();
			smsstatus.setSmsdate(new Timestamp(today.getTime()));
			smsstatus.setIscreated(IS_CREATED_FALSE);
			smsstatus.setIssended(IS_SENDED_FALSE);
			getHibernateTemplate().save(smsstatus);
		} else {
			smsstatus = (SmsStatus) isCreated.get(0);
		}
		// TODO 还要查看是否生成了当天应该发送的短信
		if (smsstatus.getIscreated() == IS_CREATED_FALSE) {
			smsstatus.setIscreated(IS_CREATED_TRUE);
			getHibernateTemplate().update(smsstatus);
		}
		getHibernateTemplate().flush();
		makeSendMsg();
	}
	
	public void setSended(){
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		List isCreated = getHibernateTemplate().find("from SmsStatus where smsdate = ?",new Object[]{today});
		SmsStatus smsstatus = null;
		if (isCreated == null || isCreated.isEmpty()) {
			smsstatus = new SmsStatus();
			smsstatus.setSmsdate(new Timestamp(today.getTime()));
			smsstatus.setIscreated(IS_CREATED_FALSE);
			smsstatus.setIssended(IS_SENDED_FALSE);
			getHibernateTemplate().save(smsstatus);
		} else {
			smsstatus = (SmsStatus) isCreated.get(0);
		}
		smsstatus.setIssended(IS_SENDED_TRUE);
		getHibernateTemplate().flush();
	}
	
	public boolean isSended(){
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		List isCreated = getHibernateTemplate().find("from SmsStatus where smsdate = ?",today);
		SmsStatus smsstatus = null;
		if (!isCreated.isEmpty()) {
			smsstatus = (SmsStatus) isCreated.get(0);
			if(smsstatus.getIssended() == IS_SENDED_TRUE){
				return true;
			}
		}
		return false;
	}

	@Transactional
	public void makeSendMsg() {
		// TODO 这里根据Cod_TelSendRule 表,生成Sms_SendLog表的内容 ,并且不生成重复的数据
		List<CodTelSendRule> rules = getHibernateTemplate().find("from CodTelSendRule ");
		Date today = new Date();
		today = DateUtils.truncate(today, Calendar.DAY_OF_MONTH);
		for (CodTelSendRule rule : rules) {
			String tableidnamestr = "";
			if("number".equals(rule.getIdtype())){
				tableidnamestr = " convert(varchar,a."+rule.getTableidname()+")";
			}else{
				tableidnamestr = "a."+rule.getTableidname();
			}
			String msg = rule.getMsg();
			msg = msg.replaceAll("\r\n", "");
			msg = msg.replaceAll("\n", "");
			if("0".equals(rule.getType())){
				final String sql = 
						" insert into Sms_SendLog "
								+ "select  DATEADD(D, 0, DATEDIFF(D, 0, GETDATE())) ,'"
								+ rule.getName()
								+ "', a.fileno,b.tel,'"
								+ msg
								+ "',"
								+ IS_SENDED_FALSE
								+ " , null,null,'"+rule.getTablename()+"',"+tableidnamestr
								+ ",null,'0',newid() from "
								+ rule.getTablename()
								+ " a , Sms_PersonTel b,HealthFile c where a.fileno = b.fileno and a.fileno = c.fileno and c.status = '0' and NOT EXISTS (select 1 from Sms_SendLog log where log.fileNo = a.fileNo and log.smsdate = DATEADD(D, 0, DATEDIFF(D, 0, GETDATE())) and examname ='"
								+ rule.getName()
								+ "'  ) and DATEADD(D, 0, DATEDIFF(D, 0, a."
								+ rule.getCol() + ")) = dateadd(day,"
								+ rule.getDays()
								+ ", DATEADD(D, 0, DATEDIFF(D, 0, GETDATE()))) ";
				getHibernateTemplate().execute(new HibernateCallback(){
					@Override
					public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
						return arg0.createSQLQuery(sql).executeUpdate();
					}
				});
						
			}else{
				final String sql = 
						" insert into Sms_SendLog "
								+ "select  DATEADD(D, 0, DATEDIFF(D, 0, GETDATE())) ,'"
								+ rule.getName()
								+ "', '"+rule.getTablename()+"',b."+rule.getTelcol()+",'"
								+ msg
								+ "',"
								+ IS_SENDED_FALSE
								+ " , null,null,'"+rule.getTablename()+"',"+tableidnamestr
								+ ",null,'0',newid() from "
								+ rule.getTablename()
								+ " a , "+rule.getTeltable()+" b where "+rule.getTeljoinstr()
								+ " and NOT EXISTS (select 1 from Sms_SendLog log where log.fileNo = '"+rule.getTablename()+"' and log.smsdate = DATEADD(D, 0, DATEDIFF(D, 0, GETDATE())) and examname ='"
								+ rule.getName()
								+ "'  ) and "+rule.getRulestr();
				getHibernateTemplate().execute(new HibernateCallback(){
					@Override
					public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
						return arg0.createSQLQuery(sql).executeUpdate();
					}
				});
			}
		}
		if(rules.size()>0)
			getHibernateTemplate().flush();
	}

	public boolean isStarted() {
		return started;
	}

	public String getProcessString() {
		return processString;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public void setProcessString(String processString) {
		this.processString = processString;
	}

}