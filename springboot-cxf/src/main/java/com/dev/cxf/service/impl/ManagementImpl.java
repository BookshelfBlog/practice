package com.dev.cxf.service.impl;

import com.dev.cxf.model.DataResult;
import com.dev.cxf.model.User;
import com.dev.cxf.service.Management;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.*;

/**
 * @Description : webservice接口实现类  //描述
 */
@Component
@WebService(
        //与接口中的命名空间一致
        targetNamespace = "http://service.cxf.dev.com",
        //接口地址
        endpointInterface = "com.dev.cxf.service.Management"
)
public class ManagementImpl implements Management {

    @Override
    public String getName(String ip) {
        return "hello world!";
    }

    @Override
    public User getUser(String name) {
        System.out.println(name);
        User user = new User();
        user.setName("cxf demo");
        user.setIp("127.0.0.1");
        return user;
    }

    @Override
    public ArrayList<User> getUserList(String name) {
        System.out.println(name);
        ArrayList<User> list = new ArrayList<User>();
        list.add(new User("ip","127.0.0.1"));
        list.add(new User("ip","127.0.0.2"));
        list.add(new User("ip","127.0.0.3"));
        return list;
    }

    @Override
    public DataResult getDataResult() {
        DataResult dataResult = new DataResult();
        List<User> list = new ArrayList<User>();
        list.add(new User("ip","127.0.0.1"));
        list.add(new User("ip","127.0.0.2"));
        Map<String, User> s = new HashMap<String, User>();
        s.put("name", new User("ip","127.0.0.1"));
        dataResult.setUserMap(s);
        dataResult.setUserList(list);
        return dataResult;
    }

    @Override
    public Map<String, User> getUserMap() {
        HashMap<String, User> s = new HashMap<String, User>();
        s.put("name",new User("ip","127.0.0.1"));
        s.put("name2",new User("ip","127.0.0.2"));
        s.put("name3",new User("ip","127.0.0.3"));
        return s;
    }

    @Override
    public Map<String, List<User>> getUserMapListUser() {
        Map<String, List<User>> s = new HashMap<String, List<User>>();
        List<User> list = new ArrayList<User>();
        list.add(new User("ip","127.0.0.1"));
        list.add(new User("ip","127.0.0.2"));
        s.put("name",list);
        s.put("name2",list);
        s.put("name3",list);
        return s;
    }

    @Override
    public HashMap<String, Object> getMap(User user) {
        System.out.println(user);
        HashMap<String, Object> s = new HashMap<String, Object>();
        s.put("ip1", "127.0.0.1");
        s.put("ip2", "127.0.0.1");
        return s;
    }
}
