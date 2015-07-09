package rt.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.log4j.Logger;

/**
 * @author 张强
 * LDAP认证服务
 */
public class LDAPAuthentication {
	public static void main(String[] args) {
		LDAPAuthentication l = new LDAPAuthentication();
		String uName = "qiang1_zhang";
		String uPwd = "ri-teng1234";
		LdapContext context = l.authByLDAP(uName,uPwd,RP_DOMAIN);
		Map<String, Object> m = l.searchUserInfo(context,uName,RP_DOMAIN);
		System.out.println("1=="+m.get("wWWHomePage"));
	}
	
	private static Logger log = Logger.getLogger(LDAPAuthentication.class);
	public static final String RP_DOMAIN = "10.131.118.15";
	public static final String AVY_DOMAIN = "10.131.156.2";
	public static final String OTHER_DOMAIN = "10.161.128.12";
	String searchDIR = "";
	
	public Map<String, Object> searchUserInfo(LdapContext ldapContext,String uName, String providerURL) {
		Map<String, Object> map = null;
		try {
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = "(&(objectCategory=person)(objectClass=user)(sAMAccountName="+uName+"))";

			String returnedAtts[] = { "wWWHomePage", "title", "department","name", "mail" }; // 定制返回属性

			searchCtls.setReturningAttributes(returnedAtts); // 设置返回属性集
			
			if(RP_DOMAIN.equals(providerURL))
				searchDIR = "DC=cs,DC=corpnet";
			else if(AVY_DOMAIN.equals(providerURL))
				searchDIR = "DC=avy,DC=corpnet";
			else if(OTHER_DOMAIN.equals(providerURL))
				searchDIR = "DC=cjs,DC=corpnet";
			
			NamingEnumeration<SearchResult> answer = ldapContext.search(searchDIR, searchFilter, searchCtls);
			map = new HashMap<String, Object>();
			
			while (answer.hasMoreElements()) {//遍历节点
				SearchResult sr = (SearchResult) answer.next();// 得到符合搜索条件的DN
				Attributes Attrs = sr.getAttributes();
				if (null != Attrs) {//节点不为空
					for (NamingEnumeration<?> ne = Attrs.getAll(); ne.hasMore(); ) {
	                    Attribute Attr = (Attribute) ne.next();
	                    for (NamingEnumeration<?> e = Attr.getAll(); e.hasMore();) {
	                         map.put(Attr.getID(), e.next());
	                    }
					}
					break;
				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("搜索用户信息时异常："+e.getMessage());
		}
		return map;
	}

	/**
	 * LDAP认证
	 * @param uName 域帐号
	 * @param uPwd 密码
	 * @param providerURL 域地址
	 * @return 认证信息
	 */
	public LdapContext authByLDAP(String uName, String uPwd, String providerURL) {
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		env.put("java.naming.referral", "follow");
		env.put(Context.PROVIDER_URL,"ldap://"+providerURL+":389");//"ldap://10.131.118.15:389"
		env.put(Context.SECURITY_PRINCIPAL, "cs\\"+uName);//"cs\\qiang1_zhang"
		env.put(Context.SECURITY_CREDENTIALS, uPwd);
		
		LdapContext ldapContext = null;
		try {
			ldapContext = new InitialLdapContext(env, null);
			return ldapContext;
		} catch (NamingException e) {
			log.error("账户【"+uName+"】LDAP认证异常："+e.getMessage());
//			e.printStackTrace();
			return ldapContext;
		}
	}
}