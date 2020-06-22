package Listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;


/**
 * @Author:Elaine
 * @Description:
 * @Date: Created in 下午8:47 2020/6/2
 * @Version: 1.0
 */
public class RetryListener implements IRetryAnalyzer {
    //Logger logger = Logger.getLogger(RetryListener.class);
    int retryCount = 1;
    int maxRetryCount = 3;


    @Override
    public boolean retry(ITestResult result) {

//        if(!result.isSuccess()) {
            if (retryCount < maxRetryCount) {

                // String message = result.getName()+" 测试用例执行失败！即将重跑第 ["+ retryCount + "]次 :class [" + result.getTestClass().getName();
                //logger.info(message);
                Reporter.setCurrentTestResult(result);
                Reporter.log("RunCount=" + (retryCount + 1));
                retryCount++;
                return true;
            }
//        }
        return false;
    }
}
