package Listener;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Author:Elaine
 * @Description:
 * @Date: Created in 下午9:20 2020/6/2
 * @Version: 1.0
 */
public class RetryListenerLinstener implements IAnnotationTransformer {

    @SuppressWarnings("rawtypes")
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retryAnalyzer = annotation.getRetryAnalyzer();
        if(retryAnalyzer == null){
            annotation.setRetryAnalyzer(RetryListener.class);
        }
    }
}
