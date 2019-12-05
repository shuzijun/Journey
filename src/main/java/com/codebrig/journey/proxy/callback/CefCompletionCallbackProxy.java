package com.codebrig.journey.proxy.callback;

import org.joor.Reflect;

/**
 * Created with IntelliJ IDEA.
 *
 * @author mafayun
 * @Date 2019-12-05 13:00
 */
public interface CefCompletionCallbackProxy   extends Reflect.ProxyObject {

    void onComplete();
}
