package com.dev.cxf.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description :  map //描述
 */
@XmlType(name = "MapConverter")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlSeeAlso({User.class})
public class MapConverter {

    private List<MapEntry> entries = new ArrayList<MapEntry>();

    public void addEntry(MapEntry entry) {
        entries.add(entry);
    }

    public static class MapEntry {
        public MapEntry() {
            super();
        }

        public MapEntry(Map.Entry<String, Object> entry) {
            super();
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        public MapEntry(String key, Object value) {
            super();
            this.key = key;
            this.value = value;
        }

        private String key;
        private Object value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    public List<MapEntry> getEntries() {
        return entries;
    }
}
