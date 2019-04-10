package com.sh.base.factory;

import com.sh.base.cache.LocalUserManager;

public class LocalManagerFactory {

    public static LocalUserManager getLocalUserManager(){
        return LocalUserManager.getInstance();
    }

}
