package com.codebrig.journey.proxy.callback;

import com.codebrig.journey.JourneyLoader;
import com.codebrig.journey.proxy.misc.BoolRefProxy;
import com.codebrig.journey.proxy.network.CefCookieProxy;
import org.joor.Reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Journey local proxy for CefCookieVisitor.
 * Interface to implement for visiting cookie values. The methods of this class will always be called on the IO thread.
 * <p>
 * Javadoc taken from: https://bitbucket.org/chromiumembedded/java-cef
 *
 * @author shuzijun
 * @Date 2019-12-04 21:21
 */
public interface CefCookieVisitorProxy extends Reflect.ProxyObject {

    Reflect.ProxyArgumentsConverter PROXY_ARGUMENTS_CONVERTER = (methodName, args) -> {
        if ("visit".equals(methodName)) {
            args[0] = Reflect.on(args[0]).as(CefCookieProxy.class);
            args[3] = Reflect.on(args[3]).as(BoolRefProxy.class);
        }
    };

    Reflect.ProxyValueConverter PROXY_VALUE_CONVERTER = (methodName, returnValue) -> returnValue;


    static CefCookieVisitorProxy createVisitor(CefCookieVisitorProxy visitor) {
        Object instance = Proxy.newProxyInstance(JourneyLoader.getJourneyClassLoader(),
                new Class<?>[]{JourneyLoader.getJourneyClassLoader()
                        .loadClass("org.cef.callback.CefCookieVisitor")},
                new Reflect.ProxyInvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        ((Reflect.ProxyArgumentsConverter) Reflect.on(visitor).field("PROXY_ARGUMENTS_CONVERTER").get())
                                .convertArguments(method.getName(), args);
                        return ((Reflect.ProxyValueConverter) Reflect.on(visitor).field("PROXY_VALUE_CONVERTER").get())
                                .convertValue(method.getName(), Reflect.on(visitor).call(method.getName(), args).get());
                    }
                });
        return Reflect.on(instance).as(CefCookieVisitorProxy.class);
    }


    /**
     * Method that will be called once for each cookie.
     */
    boolean visit(CefCookieProxy cookie, int count, int total, BoolRefProxy delete);
}
