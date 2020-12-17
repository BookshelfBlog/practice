package com.dev.cxf.client;

import lombok.extern.slf4j.Slf4j;
import java.util.*;

/**
 * @Description : client端测试  //描述
 */
@Slf4j
public class Test {
    private static final String wsdlUrl = "http://127.0.0.1:80/user/test?wsdl";
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name","zhansgan");
        map.put("ip","127.0.0.1");
        //string
//        System.out.println(CxfClientUtil.transferToString(wsdlUrl, "getName", map.get("ip").toString()));
        //entity
//        System.out.println(CxfClientUtil.transferToEntity(wsdlUrl,"getUser",map.get("name").toString()));
        //list
//        System.out.println(CxfClientUtil.transferToList(wsdlUrl,"getUserList",map.get("name").toString()));
        //list + map
//        System.out.println(CxfClientUtil.transferToDataResult(wsdlUrl,"getDataResult"));
        //map<String,User>
//        System.out.println(CxfClientUtil.transferToUserMap(wsdlUrl, "getUserMap"));
        //map<String,Object>
        System.out.println(CxfClientUtil.transferToMap(wsdlUrl, "getMap", map));
        //map<String,List<User>>
//        System.out.println(CxfClientUtil.transferToUserMapListUser(wsdlUrl, "getUserMapListUser"));
    }
}
