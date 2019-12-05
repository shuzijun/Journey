package com.codebrig.journey.proxy.network;

import com.codebrig.journey.JourneyLoader;
import com.codebrig.journey.proxy.callback.CefCompletionCallbackProxy;
import com.codebrig.journey.proxy.callback.CefCookieVisitorProxy;
import org.joor.Reflect;

import java.lang.reflect.Proxy;
import java.util.Vector;

/**
 * Journey local proxy for CefCookieManager.
 * Class used for managing cookies. The methods of this class may be called on any thread unless otherwise indicated.
 * <p>
 * Javadoc taken from: https://bitbucket.org/chromiumembedded/java-cef
 *
 * @author shuzijun
 * @Date 2019-12-04 21:21
 */
public interface CefCookieManagerProxy extends Reflect.ProxyObject {


    Reflect.ProxyArgumentsConverter PROXY_ARGUMENTS_CONVERTER = (methodName, args) -> {
        if ("visitAllCookies".equals(methodName)) {
            args[0] = ((Reflect.ProxyInvocationHandler) Proxy.getInvocationHandler(args[0])).getUnderlyingObject();
        } else if ("visitUrlCookies".equals(methodName)) {
            args[2] = ((Reflect.ProxyInvocationHandler) Proxy.getInvocationHandler(args[2])).getUnderlyingObject();
        } else if ("setCookie".equals(methodName)) {
            args[1] = ((Reflect.ProxyInvocationHandler) Proxy.getInvocationHandler(args[1])).getUnderlyingObject();
        } else if ("addLoadHandler".equals(methodName)) {
            args[0] = ((Reflect.ProxyInvocationHandler) Proxy.getInvocationHandler(args[0])).getUnderlyingObject();
        }
    };

    Reflect.ProxyValueConverter PROXY_VALUE_CONVERTER = (methodName, returnValue) -> {
        return returnValue;
    };

    /**
     * Set the schemes supported by this manager.
     */
    public void setSupportedSchemes(Vector<String> schemes);

    /**
     * Visit all cookies.
     */
    public boolean visitAllCookies(CefCookieVisitorProxy visitor);

    /**
     * Visit a subset of cookies.
     */
    public boolean visitUrlCookies(String url, boolean includeHttpOnly, CefCookieVisitorProxy visitor);

    /**
     * Sets a cookie given a valid URL and explicit user-provided cookie attributes.
     */
    public boolean setCookie(String url, CefCookieProxy cookie);

    /**
     * Delete all cookies that match the specified parameters.
     */
    public boolean deleteCookies(String url, String cookieName);

    /**
     * Sets the directory path that will be used for storing cookie data.
     */
    public boolean setStoragePath(String path, boolean persistSessionCookies);

    /**
     * Flush the backing store (if any) to disk and execute the specified |handler| on the IO thread when done.
     */
    public boolean flushStore(CefCompletionCallbackProxy handler);

    /**
     * Returns the global cookie manager.
     */
    static CefCookieManagerProxy getGlobalManager() {
        JourneyLoader classLoader = JourneyLoader.getJourneyClassLoader();
        Object realCefCookieManager = Reflect.onClass(classLoader.loadClass("org.cef.network.CefCookieManager"))
                .call("getGlobalManager").get();
        return Reflect.on(realCefCookieManager).as(CefCookieManagerProxy.class);
    }

    /**
     * Creates a new cookie manager.
     */
    static CefCookieManagerProxy createManager(String path, boolean persistSessionCookies) {
        JourneyLoader classLoader = JourneyLoader.getJourneyClassLoader();
        Object realCefCookieManager = Reflect.onClass(classLoader.loadClass("org.cef.network.CefCookieManager"))
                .call("createManager", path, persistSessionCookies).get();
        return Reflect.on(realCefCookieManager).as(CefCookieManagerProxy.class);
    }
}
