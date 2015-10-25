package ru.ifmo.ctddev.varlamov.comp.methods.hw1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateSingleton {
    private static StateSingleton instance;
    private final Map<String, Object> values = new HashMap<>();

    private StateSingleton() {}

    public static StateSingleton getInstance() {
        if (instance == null) {
            synchronized (StateSingleton.class) {
                instance = new StateSingleton();
            }
        }
        return instance;
    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public List<String> getAllKeys() {
        return new ArrayList<>(values.keySet());
    }

}
