package com.kdkj.caijin.util;

/**
 * @author Love丶TG
 * @ClassName: Constant
 * @Description: 常量类 : 存放本应用使用到的常量
 * @date 2017年11月13日 上午11:58:12
 */
public class Constant {

    /**
     * 实现序列化的S_id全部可以使用一样的
     */
    public static final long SYS_SID = 1000L;

    /**
     * 系统文件存储在服务上的文件夹名称
     */
    public static final String UPLOAD_URL = "http://192.168.20.2:8888";
    public static final String UPLOAD_ADDRESS = "D:/";
    public static final String UPLOAD_PATH = "/GWFiles";
    public static final String UPLOAD_LUNBO = "/LUNBO";
    public static final String UPLOAD_NEWS = "/NEWS";
    public static final String UPLOAD_APPLICATION = "/APPLICATION";
    public static final String UPLOAD_ICON = "/ICON/";

    /**
     * 系统异常提示参数
     */
    public static final String SYS_INNER_ERR = "系统内部错误（请将以下错误内容反馈给系统管理员）：";
    public static final String ERR_CODE = "错误码：";
    public static final String ERR_CLASS = "错误类：";
    public static final String ERR_METHOD = "错误方法：";
    public static final String ERR_LINE = "错误行号：";

    /**
     * HTML换行符
     */
    public static final String HTML_NEWLINE = "<br/>";

    /**
     * 异常处理的可选序号
     */
    public static final Integer MSG_MINUS_ONE = -1;   //服务器内部错误
    public static final Integer MSG_ZERO = 0;   //接口调用成功

    public static final Integer MSG_300 = 300;   //缺少key参数
    public static final Integer MSG_400 = 400;   //域名错误
    public static final Integer MSG_401 = 401;   //该域名已删除
    public static final Integer MSG_402 = 402;   //该域名已禁用
    public static final Integer MSG_403 = 403;   //禁止访问
    public static final Integer MSG_404 = 404;   //资源不存在
    public static final Integer MSG_405 = 405;   //错误的请求类型
    public static final Integer MSG_500 = 500;   //系统内部错误
    public static final Integer MSG_501 = 501;   //数据库错误
    public static final Integer MSG_502 = 502;   //并发异常，请重试
    public static final Integer MSG_600 = 600;   //缺少参数
    public static final Integer MSG_601 = 601;   //无权操作:缺少 token
    public static final Integer MSG_602 = 602;   //签名错误
    public static final Integer MSG_700 = 700;   //暂无数据
    public static final Integer MSG_701 = 701;   //该功能暂未开通
    public static final Integer MSG_702 = 702;   //资源余额不足
    public static final Integer MSG_901 = 901;   //token错误
    public static final Integer MSG_10000 = 10000;   //用户已经存在
    public static final Integer MSG_20000 = 20000;   //postJsonString 格式错误

}
