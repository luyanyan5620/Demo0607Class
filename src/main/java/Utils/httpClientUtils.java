package Utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:Elaine
 * @Description:
 * @Date: Created in 上午11:14 2020/6/7
 * @Version: 1.0
 */
public class httpClientUtils {

    public static String postRequest(String url,Map<String,Object> map) throws IOException {
        // 创建httpClient对象,
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建实例对象Get/Post
        HttpPost httpPost = new HttpPost(url);
        // 设置参数
        List<NameValuePair> para = new ArrayList<NameValuePair>();
        if(map != null){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                para.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
            }
        }

        // list变成httpEntiy
        UrlEncodedFormEntity entitypara = new UrlEncodedFormEntity(para,"utf-8");
        httpPost.setEntity(entitypara);
        // 执行execute
        CloseableHttpResponse result = httpClient.execute(httpPost);
        // 结果的获取
        HttpEntity entity = result.getEntity();
        String entityStr = EntityUtils.toString(entity,"utf-8");
        System.out.println(entityStr);

        result.close();
        httpClient.close();

        return entityStr;
    }

    public void postRequest(String url,String para,String contentType) throws IOException {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建实例对象 get／post
        HttpPost httpPost = new HttpPost(url);
        // 设置参数

        // list对象专程enity
        StringEntity entity = new StringEntity(para);
        httpPost.setEntity(entity);
        // 设置header
        httpPost.addHeader("Content-Type",contentType);

        // execute
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 对返回值的处理
        HttpEntity responseEntity = response.getEntity();
        String responseStr = EntityUtils.toString(responseEntity);
        System.out.println(responseStr);
        // 关闭所有连接
        httpClient.close();
        response.close();
    }

}
