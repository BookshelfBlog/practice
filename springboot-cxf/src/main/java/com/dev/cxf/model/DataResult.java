package com.dev.cxf.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName : DataResult  //类名
 * @Description :   //描述
 * @Author :   //作者
 * @Date: 2020-12-16 09:34  //时间
 */
@XmlRootElement
public class DataResult {

    private List<User> userList;
    private Map<String,User> userMap;

    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public Map<String, User> getUserMap() {
        return userMap;
    }
    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    /**
     * 为了测试时方便输出重写的一个toString()方法
     */
    @Override
    public String toString(){
        for(User u: userList){
            System.out.println(u);
        }
        Set<String> key = userMap.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            System.out.println(s + "-->" + userMap.get(s));
        }
        return "end";
    }
}
