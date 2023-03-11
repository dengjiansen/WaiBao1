package com.waibaoservice.utils.WeiXinUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waibaoservice.pojo.AccessToken;
import com.waibaoservice.pojo.StatusCode;
import com.waibaoservice.utils.DateUtils.DateUtils;
import org.apache.tomcat.util.json.JSONParser;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author DJS
 * Date create 21:13 2023/3/9
 * Modified By DJS
 **/

public class WeiXinRequestUtils {
    private static final String appId = "wx935e54762f136094";
    private static final String appSecret = "f647aa91285a8a64efc3d70e621c0154";
    private static final String templateId = "1kz2gJdUSQqpd4CS7Av3sAsTrWNC9d5e_S2ZmPzIE9g";
    private WeiXinRequestUtils() {}

    // 获取GET请求的返回值
    private static String getResponse(String url) {
        String result = null;
        System.out.println("url : " + url);
        try {
            // 与微信小程序后台进行连接
            URL realUrl = new URL(url);
            final HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            connection.connect();
            // 获取字符输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // 读取数据
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            result = builder.toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 获取Post请求的结果
    private static String getPostResponse(String url, HashMap<String, Object> data) {
        String result = null;
        System.out.println("url : " + url);
        try {
            // 与微信小程序后台进行连接
            URL realUrl = new URL(url);
            final HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            conn.setDoOutput(Boolean.TRUE);
            conn.setDoInput(Boolean.TRUE);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-type", "application/json");
            conn.connect();
            final ObjectMapper objectMapper = new ObjectMapper();
            final String s = objectMapper.writeValueAsString(data);
            System.out.println("data json:" + s);

            // 发送数据
            final OutputStream outputStream = conn.getOutputStream();
            outputStream.write(s.getBytes());
            outputStream.flush();

            // 读取响应
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder rest = new StringBuilder();
            while (null != (line = reader.readLine())){
                rest.append(line);
            }
            result = rest.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 发送GET请求, 获取用户openid, session_key, unionid
    public static String getUserInfo(String js_code) {
        if (js_code == null) {
            System.out.println("js_code is null");
            return null;
        }
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+ appId + "&secret=" + appSecret + "&js_code="+ js_code+ "&grant_type=authorization_code";
        return getResponse(url);
    }

    // 获取最新的access_token
    public static String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
        System.out.println("access_token url : " + url);
        final ObjectMapper objectMapper = new ObjectMapper();
        final AccessToken accessToken;
        try {
            accessToken = objectMapper.readValue(getResponse(url), AccessToken.class);
            return accessToken.getAccess_token();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 给用户推送消息
    public static String pushMessage(String openid) {
        if (openid == null) return null;
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> data_val = new HashMap<>();
        HashMap<String, Object> time2  = new HashMap<>();
        HashMap<String, Object> phrase14  = new HashMap<>();
        HashMap<String, Object> thing9  = new HashMap<>();

        Date d = new Date();
        final String currentTimeStr = DateUtils.getCurrentTimeStr(d);

        time2.put("value", currentTimeStr);
        phrase14.put("value", "定时结束");
        thing9.put("value", "燃气检测设备");

        data_val.put("time2", time2);
        data_val.put("phrase14", phrase14);
        data_val.put("thing9", thing9);

        data.put("touser", openid);
        data.put("template_id", templateId);
        data.put("data", data_val);
        data.put("miniprogram_state", "developer");
        data.put("lang", "zh_CN");
        // 获取响应
        final String postResponse = getPostResponse(url, data);
        System.out.println(postResponse);
        // 解析响应
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final StatusCode statusCode = objectMapper.readValue(postResponse, StatusCode.class);
            return  statusCode.getErrmsg();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
