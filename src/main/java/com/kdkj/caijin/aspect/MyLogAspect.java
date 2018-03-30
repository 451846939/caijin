package com.kdkj.caijin.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kdkj.caijin.entity.Log;
import com.kdkj.caijin.service.LogService;


/**
 * @author 林晨旭
 * @ClassName: MyLogAspect
 * @Description: 日志打印
 * @date 2017年11月7日 上午10:28:04
 */
@Aspect
@Component
public class MyLogAspect {
    @Autowired
    private LogService logService;

    //	private LogExceptionPointcut LogexceptionPointcut;
    @Pointcut("execution(* com.kdkj.caijin.service..*.insert*(..))||"
            + "execution(* com.kdkj.caijin.service..*.update*(..))||"
            + "execution(* com.kdkj.caijin.service..*.delete*(..))||"
            + "execution(* com.kdkj.caijin.aspect..*.logException*(..))")
    public void logPointcut() {

    }

    /**
     * @param @param joinpoint    设定文件
     * @return void    返回类型
     * @throws
     * @Title: before
     * @Description: 前置通知
     */
    public void before(JoinPoint joinpoint) {
        Object[] args = joinpoint.getArgs();// 此方法返回的是一个数组，数组中包括request以及ActionCofig等类对象
        System.out.println("被拦截方法调用之前调用此方法，输出此语句");
    }

    /**
     * @param @param joinpoint    设定文件
     * @return void    返回类型
     * @throws
     * @Title: after
     * @Description: 后置通知
     */
    @After(value = "logPointcut()")
    public void after(JoinPoint joinpoint) {
        System.out.println("日志调用");
        String targetName = joinpoint.getTarget().getClass().getName();
        String methodName = joinpoint.getSignature().getName();
        Object[] arguments = joinpoint.getArgs();
        Class targetClass;
        System.out.println("targetName " + targetName);
        System.out.println("methodName " + methodName);
        for (int i = 0, j = arguments.length; i < j; i++) {
            System.out.println("arguments " + arguments[i]);
        }
        try {
            targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            StringBuffer operationType = new StringBuffer();
            StringBuffer operationName = new StringBuffer();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationName.append(method.getName());
                        for (int i = 0, j = arguments.length; i < j; i++) {
                            operationType.append(arguments[i]);
                        }
                        break;
                    }
                }
            }
            try {
                String user = "111"/*(String)SecurityUtils.getSubject().getPrincipal()*/;
                if (user != null) {
                    Log log = new Log();
                    log.setStoreId(user);
                    log.setCreateDate(new Date());
                    log.setOpType(operationName.toString());
                    log.setOpDesc(user + "调用了" + operationName + "，传了" + operationType);
                    logService.logInsert(log);
                } else {
                    Log log = new Log();
                    log.setStoreId("没有登录的人");
                    log.setCreateDate(new Date());
                    log.setOpType(operationName.toString());
                    log.setOpDesc("没有登录的人" + "调用了" + operationName + "，传了" + operationType);
                    logService.logInsert(log);
                }
            } catch (NullPointerException e) {
                Log log = new Log();
                log.setStoreId("没有登录的人");
                log.setCreateDate(new Date());
                log.setOpType(operationName.toString());
                log.setOpDesc("没有登录的人" + "调用了" + operationName + "，传了" + operationType);
                logService.logInsert(log);
            }


        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
            System.err.println(e.getMessage());
        }
        System.out.println("被拦截方法调用之后调用此方法，输出此语句");
    }

    //	public void afterThrowing(Exception exception) {
//		LogexceptionPointcut.logException(Messages.showSysErr(getClass(), exception));
//		System.out.println(exception);
//	}
    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }


}
