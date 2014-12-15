package cn.net.tongfang.web.service.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.net.tongfang.framework.security.SecurityManager;
import cn.net.tongfang.framework.security.demo.service.TaxempDetail;
import cn.net.tongfang.framework.security.vo.District;
import cn.net.tongfang.framework.security.vo.Organization;

//@Component("UploadServlet")
public class OrganizationTreeServlet extends HttpServlet {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = this.getServletContext();

		WebApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(servletContext);

		sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/xml;charset=utf-8");
		StringBuffer content = new StringBuffer("[");
		// System.out.println(request.getParameter("pid") + "-----------"
		// + request.getParameter("levels"));
		List subList = null;
		if (request.getParameter("type").equals("0")) {
			subList = getSubList0(request.getParameter("pid"), request
					.getParameter("levels"));
			List finalList = new ArrayList();
			for (int i = 0; i < subList.size(); i++) {
				TreeItemData finalIt = new TreeItemData();
				Organization organization = (Organization) subList.get(i);
				finalIt.setId(String.valueOf(organization.getId()));
				finalIt.setParentid(String.valueOf(organization.getParentId()));
				finalIt.setText(organization.getName());
				finalIt.setLevels(String.valueOf(organization.getLevel()));
				finalList.add(finalIt);
			}

			for (int i = 0; i < finalList.size(); i++) {
				TreeItemData ti = (TreeItemData) finalList.get(i);
				content.append(TreeItemData.positionTreeItemContent(ti.getId(),
						ti.getText(), ti.getLevels()));
			}
			// System.out.println(content);
			if (content.toString().equals("[")) {
				content.append("]");
			} else {
				content.delete(content.lastIndexOf(","), content.length());
				content.append("]");
			}

		} else if (request.getParameter("type").equals("1")) {
			subList = getSubList1(request.getParameter("pid"), request
					.getParameter("levels"));
			List finalList = new ArrayList();
			for (int i = 0; i < subList.size(); i++) {
				TreeItemData finalIt = new TreeItemData();
				District district = (District) subList.get(i);
				finalIt.setId(String.valueOf(district.getId()));
				finalIt.setParentid(String.valueOf(district.getParentId()));
				finalIt.setText(district.getName());
				finalIt.setLevels(String.valueOf(district.getLevel()));
				finalList.add(finalIt);
			}

			for (int i = 0; i < finalList.size(); i++) {
				TreeItemData ti = (TreeItemData) finalList.get(i);
				content.append(TreeItemData.positionTreeItemContent(ti.getId(),
						ti.getText(), ti.getLevels()));
			}
			// System.out.println(content);
			if (content.toString().equals("[")) {
				content.append("]");
			} else {
				content.delete(content.lastIndexOf(","), content.length());
				content.append("]");
			}

		} else {
			String aa = "[{  \"id\": 1,   \"text\": \"Node 1\",   \"state\": \"closed\",   \"children\": [{   \"id\": 11,   \"text\": \"Node 11\"  },{   \"id\": 12,   \"text\": \"Node 12\"  }] }] ";
			String treeString = getJtree();
			response.getWriter().print(treeString);
			response.getWriter().close();
			return;
			// subList = getSubList1();
		}

		// System.out.println(content.toString());
		// StringBuffer sb = new StringBuffer();
		// sb.append("[{");
		// sb.append("\"id\":000000");
		// sb.append(",");
		// sb.append("\"text\":\"aa\"");
		// sb.append(",");
		// sb.append("\"children\":");
		// sb.append("[{");
		// sb.append("\"id\":11");
		// sb.append(",");
		// sb.append("\"text\":\"bb\"");
		// sb.append(",");
		// sb.append("\"state\":\"closed\"");
		//
		// sb.append("}]");
		// sb.append("}]");
		response.getWriter().print(content.toString());
		response.getWriter().close();
	}

	// Clean up resources
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

	List getSubList0(String pid, String levels) {
		// String filtId = "";
		// if (levels.equals("1")) {
		// filtId = "530100";
		// }
		// if (levels.equals("2")) {
		// filtId = "530122";
		// }
		// String level = String.valueOf(Integer.parseInt(levels) + 1);
		List subList = null;
		Session hbtSession = sessionFactory.openSession();
		String hql = "from Organization where parentid = '" + pid + "'";
		// String hql = "from District where parentid = '" + pid + "' and id
		// like '" + filtId + "%'";
		try {
			Query query = hbtSession.createQuery(hql);
			subList = query.list();
			// System.out.println("-----------" + subList.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			hbtSession.close();
		}
		return subList;

	}

	List getSubList1(String pid, String levels) {
		String filtId = "";
		// if (levels.equals("1")) {
		// filtId = "530100";
		// }
		// if (levels.equals("2")) {
		// filtId = "530122";
		// }
		// String level = String.valueOf(Integer.parseInt(levels) + 1);
		List subList = null;
		TaxempDetail user = SecurityManager.currentOperator();
		String districtId = user.getDistrictId();
		String hql = "";
		if (levels.equals("init")) {
			hql = "from District where ID = '" + districtId + "'";
		} else {
			hql = "from District where parentid = '" + pid + "'";
		}
		Session hbtSession = sessionFactory.openSession();
		// hql = "from District where parentid = '" + pid + "' and id like '" +
		// filtId + "%'";
		try {
			Query query = hbtSession.createQuery(hql);
			subList = query.list();
			// System.out.println("-----------" + subList.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			hbtSession.close();
		}
		return subList;
		// return "[{ \"id\": 1, \"text\": \"Node 1\", \"state\": \"closed\",
		// \"children\": [{ \"id\": 11, \"text\": \"Node 11\" },{ \"id\": 12,
		// \"text\": \"Node 12\" }] }] ";

	}

	String getJtree() {
		TaxempDetail user = SecurityManager.currentOperator();
		String orgId = String.valueOf(user.getOrg().getId());
		String level = String.valueOf(user.getOrg().getLevel());
		String districtId = user.getDistrictId();
		StringBuffer sb = new StringBuffer("[{");
		List<District> list = new ArrayList();
		Session hbtSession = sessionFactory.openSession();
		String hql = "from District where ID = '" + districtId + "'";
		// String hql = "from District where parentid = '" + pid + "' and id
		// like '" + filtId + "%'";
		try {
			Query query = hbtSession.createQuery(hql);
			list = query.list();
			District district = list.get(0);
			sb.append("\"id\":\"" + district.getId() + "\"");
			sb.append(",");
			sb.append("\"text\":\"" + district.getName() + "\"");
			sb.append(",");
			sb.append("\"state\":\"closed\"");
			sb.append(",");
			sb.append("\"children\":[");
			String hql2 = "from District where ParentID = '" + districtId + "'";
			query = hbtSession.createQuery(hql2);
			List<District> subList = query.list();
			for (int i = 0; i < subList.size(); i++) {
				District sub = subList.get(i);
				sb.append("{\"id\":\"" + sub.getId() + "\"");
				sb.append(",");
				sb.append("\"text\":\"" + sub.getName() + "\"}");
				sb.append(",");
			}
			sb.delete(sb.lastIndexOf(","), sb.length());
			sb.append("]");
			sb.append("}]");
			// System.out.println("-----------" + sb.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			hbtSession.close();
		}
		return sb.toString();
		// return "[{ \"id\": 1, \"text\": \"Node 1\", \"state\": \"closed\",
		// \"children\": [{ \"id\": 11, \"text\": \"Node 11\" },{ \"id\": 12,
		// \"text\": \"Node 12\" }] }] ";

	}
}