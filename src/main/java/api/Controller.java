package api;

import DB.DbManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class Controller {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = POST, path = "/init_DB")
    public Content initDB(@RequestParam Map<String, String> requestParams) {

        String name = requestParams.get("name");
        if (name == null) {
            name = "default_name";
        }

        try {
            DbManager.init(name);
            return new Content(name + " created", 200);
        } catch (IllegalArgumentException ignored) {
            return new Content("There is a db with this name", 400);
        }
    }

    @RequestMapping(method = POST, path = "/put")
    public Content put(@RequestParam Map<String, String> requestParams) {
        return getContent(requestParams, POST, "There is a value with that key!", 400);
    }

    @RequestMapping
    public Content get(@RequestParam Map<String, String> requestParams){
        String name = requestParams.get("name");
        String key = requestParams.get("key");
        if (name == null || key == null){
            return new Content("Invalid input", 401);
        }
        else {
            try {
                String value = DbManager.get(name, key);
                return new Content(value, 200);
            }
            catch (IllegalArgumentException e){
                return new Content(e.getMessage(), 401);
            }
        }
     }

    @RequestMapping(method = GET, path = "/get_all")
    public Content getAll(@RequestParam Map<String, String> requestParams) {
        return null;
    }

    @RequestMapping(method = DELETE, path = "/del")
    public Content del(@RequestParam Map<String, String> requestParams) {
        String name = requestParams.get("name");
        String key = requestParams.get("key");
        if (name == null || key == null) {
            return new Content("Invalid input", 401);
        } else {
            try {
                DbManager.delete_key(name, key);
                return new Content(key + " from " + name + " deleted", 200);
            } catch (IllegalArgumentException e) {
                return new Content("Key or DB unavailable!", 401);
            }
        }
    }

    @RequestMapping(method = PUT, path = "/update")
    public Content update(@RequestParam Map<String, String> requestParams) {
        return getContent(requestParams, PUT, "Unavailable key or name !", 401);
    }

    @NotNull
    private Content getContent(@RequestParam Map<String, String> requestParams, RequestMethod put, String errorMessage
            , int errorCode) {
        String name = requestParams.get("name");
        String key = requestParams.get("key");
        String value = requestParams.get("value");
        if (name == null || key == null || value == null) {
            return new Content("invalid input", 401);
        } else {
            try {
                DbManager.put(name, key, value, put);
                return new Content("Done :)", 200);
            } catch (IllegalArgumentException e) {
                return new Content(errorMessage, errorCode);
            }
        }
    }

}
