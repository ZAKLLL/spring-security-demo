package com.zakl.security.securitydemo.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @program: security
 * @description:
 * @author: ZakL
 * @create: 2019-03-20 21:13
 **/
public class SmsAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 510L;
    private Object mobile;

    public SmsAuthenticationToken(Object mobile) {
        super((Collection)null);
        this.setAuthenticated(false);
    }

    public SmsAuthenticationToken(Object mobile, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.mobile = mobile;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    public Object  getPrincipal() {
        return this.mobile;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }


}
