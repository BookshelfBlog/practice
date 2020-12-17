package com.dev.cxf.model;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.*;

/**
 * @Description :   //描述
 */
public class MapListAdapter extends XmlAdapter<MapListUser, Map<String, List<User>>> {
    /**
     * Convert a value type to a bound type.
     *
     * @param v The value to be converted. Can be null.
     * @throws Exception if there's an error during the conversion. The caller is responsible for
     *                   reporting the error to the user through {@link ValidationEventHandler}.
     */
    @Override
    public Map<String, List<User>> unmarshal(MapListUser v) throws Exception {
        Map<String, List<User>> map = new HashMap<>();
        for (MapListUser.ListEntry listEntry : v.getListEntry()) {
            map.put(listEntry.getKey(), listEntry.getUserList());
        }
        return map;
    }

    /**
     * Convert a bound type to a value type.
     *
     * @param v The value to be convereted. Can be null.
     * @throws Exception if there's an error during the conversion. The caller is responsible for
     *                   reporting the error to the user through {@link ValidationEventHandler}.
     */
    @Override
    public MapListUser marshal(Map<String, List<User>> v) throws Exception {
        MapListUser mapListUser = new MapListUser();
        List<MapListUser.ListEntry> listEntries = new ArrayList<>();
        for (String s : v.keySet()) {
            MapListUser.ListEntry listEntry= new MapListUser.ListEntry();
            listEntry.setKey(s);
            listEntry.setUserList(v.get(s));
            listEntries.add(listEntry);
        }
        mapListUser.setListEntry(listEntries);
        return mapListUser;
    }
}
