package com.codebrig.journey.proxy.network;

import com.codebrig.journey.JourneyLoader;
import org.joor.Reflect;

import java.util.Date;

/**
 * Journey local proxy for CefCookie.
 * Cookie information.
 * <p>
 * Javadoc taken from: https://bitbucket.org/chromiumembedded/java-cef
 *
 * @author shuzijun
 * @Date 2019-12-04 21:21
 */
public interface CefCookieProxy extends Reflect.ProxyObject {

    Reflect.ProxyArgumentsConverter PROXY_ARGUMENTS_CONVERTER = (methodName, args) -> {

    };

    Reflect.ProxyValueConverter PROXY_VALUE_CONVERTER = (methodName, returnValue) -> {
        return returnValue;
    };

    public String domain = null;

    static CefCookieProxy createCefCookie(String name, String value, String domain, String path, boolean secure, boolean httponly, Date creation, Date lastAccess, boolean hasExpires, Date expires) {
        JourneyLoader classLoader = JourneyLoader.getJourneyClassLoader();
        Object realCefCookie = Reflect.onClass(classLoader.loadClass("org.cef.network.CefCookie"))
                .create( name, value,domain,path,secure,httponly,creation,lastAccess,hasExpires,expires).get();
        return Reflect.on(realCefCookie).as(CefCookieProxy.class);
    }

    public String getName();

    public void setName(String name);

    public String getValue();

    public void setValue(String value);

    public String getDomain();

    public void setDomain(String domain);

    public String getPath();

    public void setPath(String path);

    public boolean isSecure();

    public void setSecure(boolean secure);

    public boolean isHttponly();

    public void setHttponly(boolean httponly);

    public Date getCreation();

    public void setCreation(Date creation);

    public Date getLastAccess();

    public void setLastAccess(Date lastAccess);

    public boolean isHasExpires();


    public void setHasExpires(boolean hasExpires);

    public Date getExpires();

    public void setExpires(Date expires);
}
