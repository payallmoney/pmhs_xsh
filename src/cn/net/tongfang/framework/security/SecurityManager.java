package cn.net.tongfang.framework.security;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.net.tongfang.framework.security.demo.service.TaxempDetail;

public class SecurityManager{

	public final static SecurityContext currentSecurityContext() {
		SecurityContext context = SecurityContextHolder.getContext();
		return context;
	}
	
	/**
	 * Get current operator
	 * 
	 * @return current operator <p> if not exist, return null 
	 * 
	 */
	public final static TaxempDetail currentOperator() {
		TaxempDetail operator = null;
		Authentication auth = currentSecurityContext().getAuthentication();
		System.out.println( "auth is " + auth );
		
		if ( auth != null && auth.getPrincipal() != null) {
			Object principal = auth.getPrincipal();
			if ( principal instanceof TaxempDetail ) {
				operator = (TaxempDetail) principal;
			}
		}
		return operator;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public static boolean isValidUser(String owneruser,Session session ){
		TaxempDetail user = currentOperator();
		int orgs = (Integer)(session.createSQLQuery(" select count(distinct org_id) from sam_taxempcode where loginname in('"+user.getUsername()+"','"+owneruser+"')").list().get(0));
		//这里可以增加其他判断,如admin
		if(user.getUsername()!=null && (user.getUsername().equals(owneruser) || "admin".equals(user.getUsername()) || orgs == 1) ){
			return true;
		}else{
			return false;
		}
	}
	
}
