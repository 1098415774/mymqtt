package com.sh.doorbell;

import com.sh.doorbell.task.TaskWakeupRunnable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("springInitializingServiceImp")
public class SpringInitializingServiceImp implements InitializingBean ,ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
//        创建二维码删除线程
        TaskWakeupRunnable runnable = applicationContext.getBean(TaskWakeupRunnable.class);
        new Thread(runnable).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

