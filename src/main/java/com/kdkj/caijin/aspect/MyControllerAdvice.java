package com.kdkj.caijin.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

import com.kdkj.caijin.util.Messages;
import com.kdkj.caijin.util.Result;


@ControllerAdvice
public class MyControllerAdvice {
    @Autowired
    private LogExceptionPointcut logExceptionPointcut;
//	  @ModelAttribute  
//	    public User newUser() {  
//	        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");  
//	        return new User();  
//	    }  
//	  
//	    @InitBinder  
//	    public void initBinder(WebDataBinder binder) {  
//	        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");  
//	    }  

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Result processUnauthenticatedException(NativeWebRequest request, Exception e) {
        logExceptionPointcut.logException(Messages.showSysErr(getClass(), e));
        e.printStackTrace();
        return Result.error("你没有对应权限或内部错误请联系管理员"); //返回一个逻辑视图名
    }
}
