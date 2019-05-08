package com.sh.doorbell.task.util;

import java.io.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class EquipActionUtil {

    public static String dirpath = "F:\\JAVA";

    public static String getEquipOrder(String id, String actionid) throws IOException {
        String resultstr = null;
        File file =  new File(dirpath + "\\" + id +".properties");
        if (!file.exists()){
            throw new FileNotFoundException("file is not exists");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] order = new byte[97];
        char[] orderid = new char[3];
        char[] orderchar = new char[93];
        int index = -1;
        while ((index = fileInputStream.read(order)) != -1){
            if (order[3] != 61){
                continue;
            }
            for (int i = 0; i < 3; i++){
                orderid[i] = (char) order[i];
            }

            String orderidstr = String.valueOf(orderid);
            if (orderidstr.equals(actionid)){
                for (int i = 4; i < index; i++){
                    if (order[i] < 0){
                        orderchar[i - 4] = (char) (256 + order[i]);
                    }else {
                        orderchar[i - 4] = (char) order[i];
                    }

                }
                StringBuilder stringBuilder = new StringBuilder();
                resultstr = String.valueOf(orderchar);
                stringBuilder.append(resultstr);
                stringBuilder.append("rn");
                resultstr = stringBuilder.toString();
                break;
            }
        }

        fileInputStream.close();
        return resultstr;
    }
}
