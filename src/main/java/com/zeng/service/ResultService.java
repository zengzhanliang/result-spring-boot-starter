package com.zeng.service;

import com.sun.xml.internal.ws.api.model.CheckedException;
import com.zeng.annotation.NonResultCovert;
import com.zeng.model.CommonResult;
import com.zeng.model.ResultProperties;
import com.zeng.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Slf4j
/**
 * 统一返回结果，统一异常处理 服务类
 * @author zengzhanliang
 */
public class ResultService {

    private ResultProperties properties;

    public ResultService(ResultProperties properties) {
        this.properties = properties;
    }

    @Pointcut("execution(public * *.*.*.controller.*.*(..))")
    public void resultAspect(){}

    @Around("resultAspect()")
    public Object handlerControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        CommonResult commonResult;

        //判断是否不需要拦截
        if ( !properties.isEnabled() )
        {
            return joinPoint.proceed();
        }

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        NonResultCovert classNonResultCovert = joinPoint.getTarget().getClass().getAnnotation(NonResultCovert.class);
        NonResultCovert methodNonResultCovert = methodSignature.getMethod().getAnnotation( NonResultCovert.class );

        if ( classNonResultCovert != null || methodNonResultCovert != null )
        {
            return joinPoint.proceed();
        }

        try
        {
            Object result = joinPoint.proceed();

            //已经是封装结果的子类直接返回结果
            if ( result instanceof CommonResult )
            {
                return result;
            }

            commonResult = new CommonResult().success( result ) ;

            if ( properties.isLog() )
            {
                //打印运行日志
                log.info( "ip地址为 {}, {} 接口参数列表 {} , 运行用时 {} ms", IpUtil.getLocalIpByNetcard(), joinPoint.getSignature(), joinPoint.getArgs(), (System.currentTimeMillis() - startTime) );
            }
        } catch (Throwable e)
        {
            commonResult = handlerException( joinPoint, e );
        }

        return commonResult;
    }

    /**
     * 统一异常处理
     * @param pjp
     * @param e
     * @return
     */
    private CommonResult<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        CommonResult<?> commonResult;

        // 已知异常
        if (e instanceof CheckedException) {

            commonResult = new CommonResult<>().failed( e );

        } else {
            log.error( pjp.getSignature() + " 接口运行出现未知错误 ", e);

            //TODO 未知的异常，应该格外注意，可以发送邮件通知等

            commonResult = new CommonResult<>().failed( e );
        }

        return commonResult;
    }
}
