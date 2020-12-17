package com.dev.cxf.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description : 返回map复杂参数转化  //描述
 */
public class MapAdapter extends XmlAdapter<MapConverter, Map<String, Object>> {

    @Override
    public MapConverter marshal(Map<String, Object> map) throws Exception {
        MapConverter convertor = new MapConverter();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            MapConverter.MapEntry e = new MapConverter.MapEntry(entry);
            convertor.addEntry(e);
        }
        return convertor;
    }

    @Override
    public Map<String, Object> unmarshal(MapConverter map) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        for (MapConverter.MapEntry e : map.getEntries()) {
            result.put(e.getKey(), e.getValue());
        }
        return result;
    }
}
