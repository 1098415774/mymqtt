package com.sh.base.converter;

import com.sh.base.form.BaseForm;
import com.sh.base.result.ResultData;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FormMessageConverter extends AbstractHttpMessageConverter {

    public FormMessageConverter(){
        super(new MediaType("application","json", Charset.forName("UTF-8")));
    }
    @Override
    protected boolean supports(Class aClass) {
        if (ResultData.class.isAssignableFrom(aClass) || BaseForm.class.isAssignableFrom(aClass)){
            return true;
        }
        return false;
    }

    @Override
    protected Object readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        Jsonstring(o);
//        ArrayList
        OutputStream outputStream = httpOutputMessage.getBody();
    }

    private String Jsonstring(Object o){
        StringBuilder builder = new StringBuilder("{");
        Class clazz = o.getClass();
        if (clazz.isAssignableFrom(Collection.class)){
            builder = new StringBuilder("[");
            Collection collection = (Collection) o;
            collection.forEach(obj -> {});
        } else if (clazz.isAssignableFrom(Map.class)){

        } else {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                if (field.isAnnotationPresent(Notshow.class)){
                    continue;
                }
                if (field.getClass().isAssignableFrom(Collection.class)){

                }else {

                }
            }
        }

        return builder.toString();
    }
}
