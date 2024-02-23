package com.wu.framework.inner.layer.data.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SchemaMap<K, V> extends HashMap<K, V> {

    private List<FieldSchema> fieldSchemas=new ArrayList<>();

    @Override
    public V put(K key, V value) {
        fieldSchemas.add(new FieldSchema().setName(String.valueOf(key)).setType(value.getClass()));
        return super.put(key, value);
    }



}
