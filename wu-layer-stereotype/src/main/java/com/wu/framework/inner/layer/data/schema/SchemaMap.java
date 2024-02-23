package com.wu.framework.inner.layer.data.schema;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Setter
@Getter
public class SchemaMap<K, V> extends LinkedHashMap<K, V> {

    private List<FieldSchema> fieldSchemas = new ArrayList<>();

    @Override
    public V put(K key, V value) {
        fieldSchemas.add(new FieldSchema().setName(String.valueOf(key)).setType(value.getClass()));
        return super.put(key, value);
    }


}
