package com.sh.mqtt.core.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MqttHandlerMethod {

    private Object obj;

    private Method method;

    public MqttHandlerMethod(Object obj, Method method){
        this.obj = obj;
        this.method = method;
    }

    public Object invoke(Object... args) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(obj,args);
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
