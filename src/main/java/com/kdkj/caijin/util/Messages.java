package com.kdkj.caijin.util;

/**
 * @author Love丶TG
 * @ClassName: Messages
 * @Description: 共用的服务器异常处理提示方法
 * @date 2017年11月13日 下午1:35:13
 */
public class Messages extends Constant {

    //注：     捕获异常返回的信息
    //	getLocalizedMessage()方法返回 Exception的本地化描述。
    //	getMessage()方法返回 Exception的详细消息字符串。
    public static String showSysErr(Class<?> clazz, Exception e) {
        StackTraceElement element = e.getStackTrace()[0];
        StringBuilder sBuilder = new StringBuilder(100);
        String localizedMessage = e.getLocalizedMessage();
        if (localizedMessage.length() > 200) {  //防止超文本
            localizedMessage = localizedMessage.substring(0, 200);
        }
        sBuilder.append(SYS_INNER_ERR).append(clazz.getSimpleName()).append(Constant.HTML_NEWLINE)
                .append(ERR_CODE).append(localizedMessage).append(Constant.HTML_NEWLINE)
                .append(ERR_CLASS).append(element.getClassName()).append(Constant.HTML_NEWLINE)
                .append(ERR_METHOD).append(element.getMethodName()).append(Constant.HTML_NEWLINE)
                .append(ERR_LINE).append(element.getLineNumber());

        return sBuilder.toString();
    }

}
