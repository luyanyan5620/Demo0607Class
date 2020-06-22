package TestCase;

import Utils.CommonUtils;
import Utils.ConstantUtils;
import Utils.JDBCUtils;
import Utils.httpClientUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author:Elaine
 * @Description:
 * @Date: Created in 下午2:32 2020/6/7
 * @Version: 1.0
 */
public class orderTest {
    String key = "";
    String address_id = "";

    @Test
    public void login() throws IOException {

        // 创建实例对象Get/Post
        String url = ConstantUtils.DOMAIN_NAME + "/newecshop/shopapi/index.php/user/login";

        String pwd = "123456";
        String user = "ela123";

        // 设置参数(无参数)
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("user","ela123");
        map.put("passwd",pwd);

        String password = CommonUtils.md5(pwd);

        String result = httpClientUtils.postRequest(url,map);

        // String -> jsonObject
        JSONObject jsonObject =JSONObject.parseObject(result);
        System.out.println(jsonObject);
        String msg = jsonObject.getString("msg");

        key = jsonObject.getJSONObject("data").getString("key");

        String sql = "select password from ecs_users where user_name='"+user+"'";

        Object[][] pas = JDBCUtils.search(sql);

        Assert.assertEquals(pas[0][0],password);

        Assert.assertEquals(msg,"登录成功");
    }


    @Test
    public void getAddressId() throws IOException {

        // 创建实例对象Get/Post
        String url = ConstantUtils.DOMAIN_NAME + "/newecshop/shopapi/index.php/goods/addresslist";

        // 设置参数(无参数)
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("key",key);

        String result = httpClientUtils.postRequest(url,map);

        // String -> jsonObject
        JSONObject jsonObject =JSONObject.parseObject(result);
        System.out.println(jsonObject);

        JSONArray dataArray = jsonObject.getJSONArray("data");
        JSONObject data = dataArray.getJSONObject(0);
        address_id = data.getString("address_id");
        System.out.println(address_id);
    }



    @Test
    public void order_test() throws IOException {
        String sql = "Select goods_id,goods_number,`market_price`,shop_price from ecs_goods where goods_number >= 2";
        Object[][] info = JDBCUtils.search(sql);

        // 接口
        String url = ConstantUtils.DOMAIN_NAME + "/newecshop/shopapi/index.php/order/create";
        // 填充参数
        // 随机生成数据
        int number = new Random().nextInt(info.length);
        Object[] goodsInfo = info[number];


        Map<String,Object> para = new HashMap<String,Object>();
        int goodsNumber = 2;
        para.put("number",goodsNumber);
        para.put("key",key);
        para.put("goods_id",goodsInfo[0]);
        para.put("address_id",address_id);
        float amount = goodsNumber * Float.parseFloat(goodsInfo[2].toString());
        para.put("amount",amount);
        para.put("money_paid",amount);
        para.put("shipping_fee",0);
        para.put("expressage_id",19);
        para.put("redpacket",0);
        para.put("goods_attr_id",0);
        para.put("bonus_id",0);
        para.put("message","您自己看吧");
        para.put("integral","0");

        // 数据库中获取psaaword
        String responeStr = httpClientUtils.postRequest(url,para);

        // 处理数据
        String[] split = responeStr.split("}");
        String jsonStr = split[1] + "}}";

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        String order = jsonObject.getJSONObject("data").getString("order_sn");

        // 验证
        String orderSql = "Select order_sn from ecs_order_info where order_sn ='"+order+"';";
        Object[][] result = JDBCUtils.search(orderSql);

        Assert.assertNotNull(result);
    }
}
