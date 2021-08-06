package com.test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

@Component
public class LdapConfiguration {
    @Value("${ldap.base}")
    String ldapBase;
    @Value("${ldap.url}")
    String ldapUrl;
    @Value("${ldap.username}")
    String ldapUsername;
    @Value("${ldap.password}")
    String ldapPassword;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setUserDn(ldapUsername);
        contextSource.setPassword(ldapPassword);
        contextSource.afterPropertiesSet();
        return contextSource;
    }
    @Bean
    public LdapTemplate ldapTemplate(){
        LdapTemplate ldapTemplate = new LdapTemplate(contextSource());
        ldapTemplate.setIgnorePartialResultException(true);
        return ldapTemplate;
    }
}
