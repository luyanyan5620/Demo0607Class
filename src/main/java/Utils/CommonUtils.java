package Utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author:Elaine
 * @Description:
 * @Date: Created in 下午3:21 2020/6/7
 * @Version: 1.0
 */
public class CommonUtils {

    // MD5加密
    public static String md5(String text){
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text);
        System.out.println("md5加密后的字符串为:" + md5str);
        return md5str;
    }

}
