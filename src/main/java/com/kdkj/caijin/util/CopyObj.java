package com.kdkj.caijin.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class CopyObj {
    /**
     * @param <M>
     * @param @param  t被复制的对象
     * @param @param  m目标对象
     * @param @return
     * @param @throws InstantiationException
     * @param @throws IllegalAccessException    设定文件
     * @return T    返回类型
     * @throws
     * @Title: copyObjNotNullFieldsAsObj
     * @Description: 通过属性复制对象
     */
    public static <T, M> M copyObjNotNullFieldsAsObj(T t, M m) throws InstantiationException, IllegalAccessException {

        Field[] tFields = t.getClass().getDeclaredFields();
        Field[] asmFields = m.getClass().getDeclaredFields();
        for (int i = 0, length = tFields.length; i < length; i++) {
            tFields[i].setAccessible(true);
            if (tFields[i].get(t) != null && !"".equals(tFields)) {
                for (int j = 0, jlength = asmFields.length; j < jlength; j++) {
                    asmFields[j].setAccessible(true);
                    if (tFields[i].getName() == asmFields[j].getName()) {
                        if (tFields[i].getName() == "serialVersionUID") {
                            continue;
                        }
                        System.out.println(tFields[i].getName());
                        asmFields[j].set(m, tFields[i].get(t));
                    }
                }
            }
        }
        return m;
    }

    /**
     * @param @param  t被复制的对象
     * @param @param  m目标对象
     * @param @return
     * @param @throws IllegalAccessException
     * @param @throws IllegalArgumentException
     * @param @throws InvocationTargetException
     * @param @throws IntrospectionException    设定文件
     * @return M    返回类型
     * @throws
     * @Title: copyObjNotNullMethodAsObj
     * @Description: 通过get和set方法复制对象，可以用于被代理过的对象复制
     */
    public static <T, M> M copyObjNotNullMethodAsObj(T t, M m) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
        Field[] tFields = t.getClass().getDeclaredFields();
        Field[] asmFields = m.getClass().getDeclaredFields();
        for (int i = 0, length = tFields.length; i < length; i++) {
            tFields[i].setAccessible(true);
            for (int j = 0, jlength = asmFields.length; j < jlength; j++) {
                asmFields[j].setAccessible(true);
                System.out.println("methods" + tFields[i].getName());
                if (tFields[i].getName() == asmFields[j].getName()) {
                    if (tFields[i].getName() == "serialVersionUID") {
                        continue;
                    }
                    PropertyDescriptor mpd = new PropertyDescriptor(asmFields[j].getName(), m.getClass());
                    PropertyDescriptor tpd = new PropertyDescriptor(tFields[i].getName(), t.getClass());
                    mpd.getWriteMethod().invoke(m, tpd.getReadMethod().invoke(t));

                }
            }
        }
        return m;

    }


}
