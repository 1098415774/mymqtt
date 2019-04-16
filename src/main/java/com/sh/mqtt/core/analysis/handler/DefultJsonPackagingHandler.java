package com.sh.mqtt.core.analysis.handler;

import com.alibaba.fastjson.JSONObject;
import com.sh.base.utils.StringUtils;
import com.sh.mqtt.core.MqttMessage;
import com.sh.mqtt.core.RequetMqttMessage;
import com.sh.mqtt.core.method.MqttHandlerMethod;

import java.text.ParseException;

public class DefultJsonPackagingHandler extends JsonPackagingHandler {


    public Object parser(String msg, Class<?> parameterType) throws ParseException {
        Object obj = JSONObject.parseObject(msg,parameterType);
        return obj;
    }
}
