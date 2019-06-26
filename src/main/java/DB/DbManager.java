package DB;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

public class DbManager {

    private static DB db =  DBMaker
            .fileDB("data_base.db")
            .closeOnJvmShutdown()
            .fileMmapEnable()
            .make();

    public static void init(String name) {
        if(db.exists(name)){
            throw new IllegalArgumentException("hashmap name is duplicate");
        }
        db.hashMap(name, Serializer.STRING, Serializer.STRING).create();
    }

    private static ConcurrentMap<String,String> getMap(String name){
        if(!db.exists(name)){
            throw new IllegalArgumentException("hashmap name does not exist");
        }
        return (ConcurrentMap<String, String>) db.hashMap(name).open();
    }

    private static void checkKey(String hashMapName, String key){
        if(!getMap(hashMapName).containsKey(key)){
            throw new IllegalArgumentException("key does not exist");
        }
    }

    public static String get(String hashMapName, String key){
        checkKey(hashMapName, key);
        return getMap(hashMapName).get(key);
    }

    public static void delete_key(String hashMapName, String key){
        checkKey(hashMapName, key);
        getMap(hashMapName).remove(key);
    }

    public static void put(String hashMapName, String key, String value, RequestMethod requestMethod){
        if(requestMethod.equals(POST) ^ !getMap(hashMapName).containsKey(key)){
            throw new IllegalArgumentException("key does not exist");
        }

        getMap(hashMapName).put(key, value);
    }

    public static ArrayList<String> getAll(String hashMapName){
        ArrayList<String> result = new ArrayList<>();
        for (final Map.Entry<String, String> entry : getMap(hashMapName).entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public static ArrayList<String> getAllKeys(String hashMapName){
        ArrayList<String> result = new ArrayList<>();
        for (final Map.Entry<String, String> entry : getMap(hashMapName).entrySet()) {
            result.add(entry.getKey());
        }
        return result;
    }


    public static void debug_print(DB db){
        for (final Map.Entry<String, Object> entry : db.getAll().entrySet()) {
            final String name = entry.getKey();
            final Object value = entry.getValue();
            if (value instanceof Map) {
                inspectMap(name, (Map<?, ?>) value);
            } else {
                System.err.println(String.format("Unexpected type (%s) for '%s'.", value.getClass(), name));
            }
        }
    }
    private static <K, V> void inspectMap(final String name, final Map<K, V> map) {
        System.out.println(name);
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            final K key = entry.getKey();
            final V value = entry.getValue();
            System.out.println(String.format("    %s = %s [%s, %s]", key, value, key.getClass(), value.getClass()));
        }
    }
}
