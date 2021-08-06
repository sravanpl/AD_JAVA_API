package com.test.DTO;

import java.util.Objects;

public class ADUserDetails {
    private String username;
    private String displayName;
    private String dn;
    private String memberOf;
    private String accoutExpired;
    private boolean credentialsNeverExpired;
    private boolean passwordExpired;
    private String passwordExpiryDate;
    private boolean accountEnable = true;
    private String accountExpiryDate;
    private String email;
    private String description;
    private int timeBeforeAccountExpiration = Integer.MAX_VALUE;
    private int timeBeforeCredentialsExpiration = Integer.MAX_VALUE;
    private String lastLogonDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ADUserDetails that = (ADUserDetails) o;
        return credentialsNeverExpired == that.credentialsNeverExpired && passwordExpired == that.passwordExpired && accountEnable == that.accountEnable && timeBeforeAccountExpiration == that.timeBeforeAccountExpiration && timeBeforeCredentialsExpiration == that.timeBeforeCredentialsExpiration && userAccountControl == that.userAccountControl && daysSinceLogin == that.daysSinceLogin && Objects.equals(username, that.username) && Objects.equals(displayName, that.displayName) && Objects.equals(dn, that.dn) && Objects.equals(memberOf, that.memberOf) && Objects.equals(accoutExpired, that.accoutExpired) && Objects.equals(passwordExpiryDate, that.passwordExpiryDate) && Objects.equals(accountExpiryDate, that.accountExpiryDate) && Objects.equals(email, that.email) && Objects.equals(description, that.description) && Objects.equals(lastLogonDate, that.lastLogonDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, displayName, dn, memberOf, accoutExpired, credentialsNeverExpired, passwordExpired, passwordExpiryDate, accountEnable, accountExpiryDate, email, description, timeBeforeAccountExpiration, timeBeforeCredentialsExpiration, lastLogonDate, userAccountControl, daysSinceLogin);
    }

    @Override
    public String toString() {
        return "ADUserDetails{" +
                "username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", dn='" + dn + '\'' +
                ", memberOf='" + memberOf + '\'' +
                ", accoutExpired='" + accoutExpired + '\'' +
                ", credentialsNeverExpired=" + credentialsNeverExpired +
                ", passwordExpired=" + passwordExpired +
                ", passwordExpiryDate='" + passwordExpiryDate + '\'' +
                ", accountEnable=" + accountEnable +
                ", accountExpiryDate='" + accountExpiryDate + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", timeBeforeAccountExpiration=" + timeBeforeAccountExpiration +
                ", timeBeforeCredentialsExpiration=" + timeBeforeCredentialsExpiration +
                ", lastLogonDate='" + lastLogonDate + '\'' +
                ", userAccountControl=" + userAccountControl +
                ", daysSinceLogin=" + daysSinceLogin +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String memberOf) {
        this.memberOf = memberOf;
    }

    public String getAccoutExpired() {
        return accoutExpired;
    }

    public void setAccoutExpired(String accoutExpired) {
        this.accoutExpired = accoutExpired;
    }

    public boolean isCredentialsNeverExpired() {
        return credentialsNeverExpired;
    }

    public void setCredentialsNeverExpired(boolean credentialsNeverExpired) {
        this.credentialsNeverExpired = credentialsNeverExpired;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public String getPasswordExpiryDate() {
        return passwordExpiryDate;
    }

    public void setPasswordExpiryDate(String passwordExpiryDate) {
        this.passwordExpiryDate = passwordExpiryDate;
    }

    public boolean isAccountEnable() {
        return accountEnable;
    }

    public void setAccountEnable(boolean accountEnable) {
        this.accountEnable = accountEnable;
    }

    public String getAccountExpiryDate() {
        return accountExpiryDate;
    }

    public void setAccountExpiryDate(String accountExpiryDate) {
        this.accountExpiryDate = accountExpiryDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeBeforeAccountExpiration() {
        return timeBeforeAccountExpiration;
    }

    public void setTimeBeforeAccountExpiration(int timeBeforeAccountExpiration) {
        this.timeBeforeAccountExpiration = timeBeforeAccountExpiration;
    }

    public int getTimeBeforeCredentialsExpiration() {
        return timeBeforeCredentialsExpiration;
    }

    public void setTimeBeforeCredentialsExpiration(int timeBeforeCredentialsExpiration) {
        this.timeBeforeCredentialsExpiration = timeBeforeCredentialsExpiration;
    }

    public String getLastLogonDate() {
        return lastLogonDate;
    }

    public void setLastLogonDate(String lastLogonDate) {
        this.lastLogonDate = lastLogonDate;
    }

    public int getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(int userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public long getDaysSinceLogin() {
        return daysSinceLogin;
    }

    public void setDaysSinceLogin(long daysSinceLogin) {
        this.daysSinceLogin = daysSinceLogin;
    }

    private int userAccountControl;
    private long daysSinceLogin;

}