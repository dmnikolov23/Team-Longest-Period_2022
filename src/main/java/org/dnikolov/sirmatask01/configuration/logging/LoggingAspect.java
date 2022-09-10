package org.dnikolov.sirmatask01.configuration.logging;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    static org.apache.logging.log4j.Logger log = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* org.dnikolov.sirmatask01.controller..*(..))")
    public void controllerMethodsPointcut() {}

    @Around("controllerMethodsPointcut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logRequest(joinPoint);
        Object result = joinPoint.proceed();
        logResponse(result, joinPoint.getSignature().getName());

        return result;
    }

    private void logRequest(ProceedingJoinPoint joinPoint) {
        try {
            Object[] parameterValues = joinPoint.getArgs();
            CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
            String[] parameterNames = codeSignature.getParameterNames();
            StringBuilder message = new StringBuilder(joinPoint.getSignature().getName() + " - Request: {");
            for (int i = 0; i < parameterValues.length; i++) {
                message.append("\"").append(parameterNames[i]).append("\": ");
                message.append(parameterValues[i]);

                if (i < parameterValues.length - 1) {
                    message.append(", ");
                }
            }
            message.append("}");

            log.info(message);
        } catch (Throwable e) {
            log.error("Failed logging request for method  " + joinPoint.getSignature().getName(), e);
        }
    }

    private void logResponse(Object response, String methodName) {
        try {
            log.info(methodName + " - Response: {" + response.toString() + "}");
        } catch (Throwable e) {
            log.error("Failed logging response for method  " + methodName, e);
        }
    }
}
