package TestCase;

import Utils.ConstantUtils;
import Utils.httpClientUtils;
import com.alibaba.fastjson.JSONObject;
import dataprovider.ExeclUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:Elaine
 * @Description:
 * @Date: Created in 上午11:10 2020/6/7
 * @Version: 1.0
 */
public class delAddressTest {
    String key = "";

    @Test
    public void login() throws IOException {

        // 创建实例对象Get/Post
        String url = ConstantUtils.DOMAIN_NAME + "/newecshop/shopapi/index.php/user/login";

        // 设置参数(无参数)
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("user","ela123");
        map.put("passwd","123456");

        String result = httpClientUtils.postRequest(url,map);

        // String -> jsonObject
        JSONObject jsonObject =JSONObject.parseObject(result);
        System.out.println(jsonObject);
        String msg = jsonObject.getString("msg");

        key = jsonObject.getJSONObject("data").getString("key");

        Assert.assertEquals(msg,"登录成功");
    }

    @Test(dataProviderClass = ExeclUtils.class,dataProvider = "execlDataProvider")
    public void delAddress(String addressid,String result) throws IOException {

        String url = ConstantUtils.DOMAIN_NAME + "/newecshop/shopapi/index.php/goods/deladdress";

        // 设置参数(无参数)
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("key",key);
        map.put("address_id",addressid);

        String resultRe = httpClientUtils.postRequest(url,map);

        // String -> jsonObject
        JSONObject jsonObject =JSONObject.parseObject(resultRe);
        System.out.println(jsonObject);
        String msg = jsonObject.getString("msg");

        Assert.assertEquals(msg,result);

    }


    @Test
    public void delAddress1() throws IOException {

        String url = ConstantUtils.DOMAIN_NAME + "/newecshop/shopapi/index.php/goods/deladdress";

        // 设置参数(无参数)
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("key",key);
        map.put("address_id",9999);

        String resultRe = httpClientUtils.postRequest(url,map);

        // String -> jsonObject
        JSONObject jsonObject =JSONObject.parseObject(resultRe);
        System.out.println(jsonObject);
        String msg = jsonObject.getString("msg");

        Assert.assertEquals(msg,"删除成功");

    }


}
