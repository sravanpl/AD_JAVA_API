package com.test.DAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.naming.directory.SearchControls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.NotFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.test.DTO.ADUserDetails;
import com.test.utils.HelperMethods;

 
@Component
public class UserDaoImpl {
	private static final String USER_ACCOUNT_CONTROL_ATTR_NAME = "userAccountControl";
	private static final String ACCOUNT_EXPIRES_ATTR_NAME = "accountExpires";
	private static final String PWD_LAST_SET_ATTR_NAME = "pwdLastset";
	private static final String PASSWORD_ATTR_NAME = "uniCodePwd";
	private static final String DISTINGUISHED_NAME_ATTR_NAME = "distinguishedname";
	private static final String MEMBER_ATTR_NAME = "member";
	private static final int FLAG_TO_DISABLE_USER = 0x2;
	private static final int ADS_UF_DONT_EXPIRE_PASSWD = 0X10000;
	private static final int USER_CONTROL_NORMAL_USER = 512;
	private static final String ACCOUNT_NEVER_EXPIRE_VALUE = "9223372036854775807";
	private static final String maxPwdAgeStr = "-36288000000000";
	private static final int ACCOUNT_DISABLE = 0X0002;

	@Autowired
	private LdapTemplate ldapTemplate;
	@Autowired
	private HelperMethods helperMethods;

	public List<ADUserDetails> getAllUsers(String base) {
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "person"));
		filter.and(new NotFilter(new EqualsFilter("objectclass", "computer")));

		return ldapTemplate.search(LdapUtils.newLdapName(base), filter.toString(), new ADUserContextMapper());
	}

	private class ADUserContextMapper extends AbstractContextMapper {
		@Override
		protected Object doMapFromContext(DirContextOperations context) {
			String accountExpires = context.getStringAttribute(ACCOUNT_EXPIRES_ATTR_NAME);
			String pwdLastSet = context.getStringAttribute(PWD_LAST_SET_ATTR_NAME);
			String userAccountControlLevel = context.getStringAttribute(USER_ACCOUNT_CONTROL_ATTR_NAME);
			String userName = context.getStringAttribute("sAMAccountName");
			System.out.println(userName);
			String fullDn = context.getStringAttribute("distinguishedName");
			String[] memberOf = context.getStringAttributes("memberOf");
			List<String> userGroupNames = new ArrayList<>();
			if (null != memberOf) {
				for (String groupName : memberOf) {
					userGroupNames.add(groupName.split(",")[0].replace("CN=", ""));
				}
			}
			int userAccountControl = Integer.parseInt(userAccountControlLevel);
			boolean accountNeverExpire = accountExpires.equals("0")
					|| ACCOUNT_NEVER_EXPIRE_VALUE.equals(accountExpires);
			boolean accountDisabled = (userAccountControl & ACCOUNT_DISABLE) == ACCOUNT_DISABLE;
			boolean credentialsNeverExpire = (userAccountControl
					& ADS_UF_DONT_EXPIRE_PASSWD) == ADS_UF_DONT_EXPIRE_PASSWD;
			Date pwdLastSetDate = helperMethods.getDateTimeFrom(pwdLastSet);
			int maxPwdAgeinDays = 90;
			Date currentDateTime = new Date();
			Date currentDate = helperMethods.truncTimeFrom(currentDateTime);
			boolean credentialsExpired = false;
			int daysBeforeCredentialsExpiration = Integer.MAX_VALUE;
			Date credentialsExpiresDate = null;
			if (!credentialsNeverExpire) {
				credentialsExpiresDate = helperMethods.addDaysToDate(maxPwdAgeinDays, pwdLastSetDate);
				credentialsExpired = credentialsExpiresDate.compareTo(currentDate) < 0;
				daysBeforeCredentialsExpiration = (int) TimeUnit.DAYS
						.convert(credentialsExpiresDate.getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);
			}
			boolean accountExpired = false;
			int daysBeforeAccountExpiration = Integer.MAX_VALUE;
			Date accountExpiresDate = null;
			if (!accountNeverExpire) {
				accountExpiresDate = helperMethods.getDateTimeFrom(accountExpires);
				accountExpired = accountExpiresDate.compareTo(currentDate) < 0;
				daysBeforeAccountExpiration = (int) TimeUnit.DAYS
						.convert(accountExpiresDate.getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);
			}
			ADUserDetails adUserDetails = new ADUserDetails();
			adUserDetails.setUsername(userName);
			adUserDetails.setDn(fullDn);
			adUserDetails.setAccountEnable(!accountDisabled);
			adUserDetails.setTimeBeforeAccountExpiration(daysBeforeAccountExpiration);
			adUserDetails.setCredentialsNeverExpired(credentialsExpired);
			adUserDetails.setMemberOf(StringUtils.collectionToCommaDelimitedString(userGroupNames));
			adUserDetails.setEmail(context.getStringAttribute("mail"));
			adUserDetails.setDisplayName(context.getStringAttribute("displayNa,e"));
			adUserDetails.setPasswordExpiryDate(helperMethods.convertDateToString(credentialsExpiresDate));
			adUserDetails.setAccountExpiryDate(helperMethods.convertDateToString(accountExpiresDate));
			adUserDetails.setDescription(context.getStringAttribute("description"));
			String lastLoginDate = getLastLoginTime(context.getStringAttribute("lastLogon"),
					context.getStringAttribute("LastLogonTimeStamp"));
			adUserDetails.setLastLogonDate(lastLoginDate);
			adUserDetails.setUserAccountControl(userAccountControl);
			try {
				adUserDetails.setDaysSinceLogin(getDaysSinceLogin(lastLoginDate));
			} catch (java.text.ParseException e) {
				adUserDetails.setDaysSinceLogin(-999);
				e.printStackTrace();
			}
			return adUserDetails;
		}

		public String getLastLoginTime(String lastLogon, String lastLogonTimeStamp) {
			if (null == lastLogon) {
				lastLogon = "116444736000000000";
			}
			if (null == lastLogonTimeStamp) {
				lastLogonTimeStamp = "116444736000000000";
			}
			Long lastLogonLong = Long.parseLong(lastLogon);
			Long lastLogonTimeStampLong = Long.parseLong(lastLogonTimeStamp);
			Long finalValue = lastLogonLong > lastLogonTimeStampLong ? lastLogonLong : lastLogonTimeStampLong;
			Date date = new Date((finalValue - 116444736000000000L) / 10000);
			return date.toString();
		}

		public long getDaysSinceLogin(String lastLogonDate) throws java.text.ParseException {
			SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));
			Date date = format.parse(lastLogonDate);
			long endTime = new Date().getTime();
			long startTime = date.getTime();
			long diffTime = endTime - startTime;
			long diffDays = diffTime / (1000 * 60 * 60 * 24);
			return diffDays;
		}
	}
}
