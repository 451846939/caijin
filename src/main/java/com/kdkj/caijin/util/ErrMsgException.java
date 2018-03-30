package com.kdkj.caijin.util;

/**
 * @author Love丶TG
 * @ClassName: ErrMsgEx
 * @Description: 通用的异常
 * @date 2017年11月16日 下午6:59:57
 */
public class ErrMsgException extends RuntimeException {

    private static final long serialVersionUID = 1000L;

    private String msg;
    private int code = 500;

    public ErrMsgException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ErrMsgException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ErrMsgException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ErrMsgException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
