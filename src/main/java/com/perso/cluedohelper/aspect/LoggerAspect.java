package com.perso.cluedohelper.aspect;

import com.perso.cluedohelper.util.LogManagerWrapper;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * This class will facilitate the logging in common places of the application
 */
@Aspect
@Configuration
public class LoggerAspect {

	private static final String START_METHOD_MESSAGE = "{}(..) | START";
	private static final String END_METHOD_SUCCESS_MESSAGE = "{}(..) | END";

	/**
	 * Pointcut for all methods in classes annotated with
	 * {@link org.springframework.web.bind.annotation.RestController @RestController}
	 */
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void anyRestControllerMethod() {
		// pointcut to be used in advices
	}

	/**
	 * Advice to log the entry and exit points for {@link #anyRestControllerMethod() anyRestControllerMethod}
	 *
	 * @param proceedingJoinPoint the method we want to execute, used to gather information about that method
	 * @return the result of the execution of the {@code proceedingJoinPoint}
	 * @throws Throwable from the {@code proceedingJoinPoint}
	 */
	@Around(value = "anyRestControllerMethod()")
	public Object logMethodNameInAllRestControllersWithAround(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		final Logger logger = LogManagerWrapper.getLogger(proceedingJoinPoint);
		final String methodName = proceedingJoinPoint.getSignature().getName();

		logger.info(START_METHOD_MESSAGE, methodName);
		final Object result = proceedingJoinPoint.proceed();
		logger.info(END_METHOD_SUCCESS_MESSAGE, methodName);

		return result;
	}

}
