package com.sh.mqtt.core;

import com.sh.base.utils.StringUtils;
import com.sh.mqtt.annotation.MQTTRequestMapping;
import com.sh.mqtt.core.analysis.StrWildcard;
import com.sh.mqtt.core.method.MqttHandlerMethod;
import com.sh.mqtt.stereotype.MQTTController;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class MqttMVCApplication implements MessageHandler {

    private String typeAliasesPackage;

    private ClassLoader classloader;

    private HashMap<String, MqttHandlerMethod> visitMap;


    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

    }

    public void init(){
        visitMap = new HashMap<>();
        List<String> urls = getClassUrl(typeAliasesPackage);
        if (urls.size() <= 0){
            return;
        }
        insterVisitMap(urls);

    }

    private List<String> getClassUrl(String path){
        ArrayList<String> paths = new ArrayList<>();
        List<String> filepaths = new ArrayList<>();
        if (StringUtils.isEmpty(path)){
            return paths;
        }
        path = path.trim().replace('.','/');
        String currentdir = this.getClassLoader().getResource("").getPath();
        for (String s : path.split(";")) {
            paths.add(currentdir + s + "/");
        }
        StrWildcard strWildcard = new StrWildcard("**");
        filepaths = strWildcard.processWildcard(paths);
        return filepaths;
    }

    private void insterVisitMap(List<String> urls){
        try {
            if (urls.size() < 1){
                return;
            }
            String  cc = getClassLoader().getResource("").getPath();
            File classloaderfile = new File(cc);
            for (String url : urls){
                File file = new File(url);
                if (!file.exists() || !file.isFile()){
                    continue;
                }
                ClassLoader loader = this.getClassLoader();
                String current = classloaderfile.getAbsolutePath() + "\\";
                url = url.replace(current,"");
                url = url.replace(".class","");
                url = url.replace("\\",".");
                Class clazz = loader.loadClass(url);
                MQTTController mqttController = (MQTTController) clazz.getAnnotation(MQTTController.class);
                MQTTRequestMapping request = (MQTTRequestMapping) clazz.getAnnotation(MQTTRequestMapping.class);
                if (mqttController == null || request == null){
                    continue;
                }
                String fvisiturl = request.value();
                Method[] methods = clazz.getDeclaredMethods();
                Object obj = clazz.newInstance();
                for (Method method : methods){
                    MQTTRequestMapping mrequest =  method.getAnnotation(MQTTRequestMapping.class);
                    if (mrequest == null){
                        continue;
                    }
                    String visiturl = fvisiturl + "/" + mrequest.value();
                    MqttHandlerMethod mqttHandlerMethod = new MqttHandlerMethod(obj,method);
                    visitMap.put(visiturl,mqttHandlerMethod);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public ClassLoader getClassLoader() {
        return this.classloader == null ? Thread.currentThread().getContextClassLoader() : this.classloader;
    }
}
