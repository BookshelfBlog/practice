package com.dev.cxf.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.dev.cxf.model.DataResult;
import com.dev.cxf.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description :  cxf操作类 //描述
 */
@Slf4j
public class CxfClientUtil {

    private static Client client = null;

    /**
     * 获取yml中配置
     * @param name 配置文件名 如：application.yml
     * @param key 配置文件名 如：cxf
     */
    public static Hashtable getYml(String name,String key){
        //"application.yml"
        ClassPathResource resource = new ClassPathResource(File.separator + name);
        try {
            Hashtable props = PropertiesLoaderUtils.loadProperties(resource);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建client
     * @param wsdlUrl webservice地址
     */
    public static boolean create(String wsdlUrl){
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            client = dcf.createClient(wsdlUrl);
            // 如果返回的address不是远程服务地址，重新制定地址
            client.getEndpoint().getEndpointInfo().setAddress(wsdlUrl);
            // 策略
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            // 连接超时
            httpClientPolicy.setConnectionTimeout(6000000);
            httpClientPolicy.setAllowChunking(false);
            // 接收超时
            httpClientPolicy.setReceiveTimeout(6000000);
            HTTPConduit http = (HTTPConduit) client.getConduit();
            http.getClient().setReceiveTimeout(0);
            http.setClient(httpClientPolicy);
            if (null != client) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            return false;
        }
        return false;
    }

    /**
     * 返回String
     * @param wsdlUrl 地址
     * @param method 方法名
     * @param parm 入参
     *
     * @return String 字符串
     */
    public static String transferToString(String wsdlUrl, String method, String parm) {
        if (create(wsdlUrl)) {
            Object[] objects = null;
            try {
                objects = client.invoke(method, parm);
                if (objects.length > 0){
                    return objects[0].toString();
                } else {
                    return "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "调用失败!";
    }

    /**
     * 返回实体类
     * @param wsdlUrl 地址
     * @param method 方法名
     * @param parm 入参
     *
     * @return User 实体类
     */
    public static User transferToEntity(String wsdlUrl, String method, String parm) {
        if (create(wsdlUrl)) {
            Object[] objects = null;
            User user = null;
            try {
                objects = client.invoke(method, parm);
                if (objects.length > 0){
                    user = JSON.parseObject(JSON.toJSONString(objects[0]), User.class);
                    return user;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return user;
            }
        }
        return new User();
    }

    /**
     * 返回List<User>
     * @param wsdlUrl 地址
     * @param method 方法名
     * @param parm 入参
     *
     * @return List<User> 实体类
     */
    public static ArrayList<User> transferToList(String wsdlUrl, String method, String parm) {
        if (create(wsdlUrl)) {
            Object[] objects = null;
            ArrayList<User> list = null;
            try {
                objects = client.invoke(method, parm);
                if (objects.length > 0){
                    list = (ArrayList<User>) JSON.parseArray(JSON.toJSONString(objects[0]), User.class);
                    return list;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return list;
            }
        }
        return new ArrayList<User>();
    }

    /**
     * 返回List<User>与Map<String,User>
     *
     * @param wsdlUrl 地址
     * @param method 方法名
     * @param parm 入参
     *
     * @return DataResult 实体类
     */
    public static DataResult transferToDataResult(String wsdlUrl, String method) {
        if (create(wsdlUrl)) {
            Object[] objects = null;
            DataResult dataResult = null;
            try {
                objects = client.invoke(method);
                if (objects.length > 0){
                    // https://www.cnblogs.com/tfxz/p/12621560.html
                    Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
                    classMap.put("userList",User.class);
                    dataResult = (DataResult) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(JSONObject.toJSONString(objects[0])), DataResult.class, classMap);
                    return dataResult;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return dataResult;
            }
        }
        return new DataResult();
    }

    public static Map<String, User> transferToUserMap(String wsdlUrl, String method) {
        if (create(wsdlUrl)) {
            Object[] objects = null;
            Map<String, User> map = new HashMap<>();
            try {
                objects = client.invoke(method);
                if (objects.length > 0) {
                    JSONArray entries = JSON.parseObject(JSON.toJSONString(objects[0])).getJSONArray("entries");
                    //https://www.cnblogs.com/tfxz/p/12621560.html
                    Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
                    for (Object object : entries) {
                        net.sf.json.JSONObject jsonObject1 = net.sf.json.JSONObject.fromObject(object);
                        User table = (User) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(jsonObject1.getString("value")), User.class, classMap);
                        map.put(jsonObject1.getString("key"), table);
                    }
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return map;
            }
        }
        return new HashMap<String, User>();
    }

    public static Map<String, Object> transferToMap(String wsdlUrl, String method,Map<String, Object> m) {
        if (create(wsdlUrl)) {
            Object[] objects = null;
            Map<String, Object> map = new HashMap<>();
            try {
                //CXF传入复杂参数：调用服务器端实体类->调用set方法->提交参数
                Object ajjySjth = Thread.currentThread().getContextClassLoader().loadClass("com.dev.cxf.service.User").getDeclaredConstructor().newInstance();// 加载类为服务端自定义对象命名空间
                Method setIp = ajjySjth.getClass().getMethod("setIp", String.class);// 调用set方法设置参数
                Method setName = ajjySjth.getClass().getMethod("setName", String.class);// 调用set方法设置参数
                setIp.invoke(ajjySjth, m.get("ip"));
                setName.invoke(ajjySjth, m.get("name"));
                objects = client.invoke(method, ajjySjth);
                if (objects.length > 0){
                    net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(objects[0]);
                    net.sf.json.JSONArray entry = jsonObject.getJSONArray("entry");
                    for (Object o : entry) {
                        JSONObject object = JSON.parseObject(JSON.toJSONString(o));
                        map.put(object.getString("key"), object.getString("value"));
                    }
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return map;
            }
        }
        return new HashMap<String, Object>();
    }

    public static Map<String, List<User>> transferToUserMapListUser(String wsdlUrl, String method) {
        if (create(wsdlUrl)) {
            Object[] objects = null;
            Map<String, List<User>> map = new HashMap<>();
            try {
                objects = client.invoke(method);
                if (objects.length > 0) {
                    JSONArray listEntry = JSON.parseObject(JSON.toJSONString(objects[0])).getJSONArray("listEntry");
                    for (Object o : listEntry) {
                        JSONObject object = JSON.parseObject(JSON.toJSONString(o));
                        map.put(object.getString("key"), JSON.parseObject(JSON.toJSONString(object.getJSONArray("userList")), List.class));
                    }
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return map;
            }
        }
        return new HashMap<String, List<User>>();
    }
}
