package com.dev.cxf.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : MapListUser  //类名
 * @Description :   //描述
 * @Author :   //作者
 * @Date: 2020-12-17 09:50  //时间
 */
@XmlType(name = "MapListUser")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlSeeAlso({User.class})
public class MapListUser {

    private List<ListEntry> listEntry;

    static class ListEntry{
        private String key;
        private List<User> userList = new ArrayList<User>();

        public ListEntry() {
        }

        public ListEntry(String key, List<User> userList) {
            this.key = key;
            this.userList = userList;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<User> getUserList() {
            return userList;
        }

        public void setUserList(List<User> value) {
            this.userList = value;
        }
    }

    public List<ListEntry> getListEntry() {
        return listEntry;
    }

    public void setListEntry(List<ListEntry> listEntry) {
        this.listEntry = listEntry;
    }
}
