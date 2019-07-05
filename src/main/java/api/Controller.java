package api;

import DB.DbManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class Controller {
    //todo rename all apis

    @RequestMapping(method = POST, path = "/init_DB")
    public ResponseEntity<String> initDB(@RequestParam Map<String, String> requestParams) {
        String name = requestParams.get("name");
        if (name == null) {
            name = "default_name";
        }

        try {
            DbManager.init(name);
            System.out.println( name + " db created");
            return new ResponseEntity<String>(name + " created", HttpStatus.OK);

        } catch (IllegalArgumentException ignored) {
            System.out.println("db create exception!");
            return new ResponseEntity<String>("There is a db with this name", HttpStatus.BAD_REQUEST);

        }
    }


    @RequestMapping(method = POST, path = "/get")
    public ResponseEntity<String> get(@RequestParam Map<String, String> requestParams){
        String name = requestParams.get("name");
        String key = requestParams.get("key");
        if (name == null || key == null){
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                String value = DbManager.get(name, key);
                return new ResponseEntity<String>(value, HttpStatus.OK);
            }
            catch (IllegalArgumentException e){
                return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.BAD_REQUEST);
            }
        }
     }

    @RequestMapping(method = POST, path = "/get_all_keys")
    public ResponseEntity<List> getAll(@RequestParam Map<String, String> requestParams) {
        String name = requestParams.get("name");
        if (name == null){
            return new ResponseEntity<>(new ArrayList(), HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                List value = DbManager.getAllKeys(name);
                return new ResponseEntity<>(value, HttpStatus.OK);
            }
            catch (IllegalArgumentException e){
                return new ResponseEntity<>(new ArrayList(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(method = POST, path = "/get_all_values")
    public ResponseEntity<List> getAllValue(@RequestParam Map<String, String> requestParams) {
        String name = requestParams.get("name");
        if (name == null){
            return new ResponseEntity<>(new ArrayList(), HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                List value = DbManager.getAll(name);
                return new ResponseEntity<>(value, HttpStatus.OK);
            }
            catch (IllegalArgumentException e){
                return new ResponseEntity<>(new ArrayList(), HttpStatus.BAD_REQUEST);
            }
        }
    }


    @RequestMapping(method = POST, path = "/del_from_DB")
    public ResponseEntity<String> del(@RequestParam Map<String, String> requestParams) {
        String name = requestParams.get("name");
        String key = requestParams.get("key");
        if (name == null || key == null) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST);
        } else {
            try {
                DbManager.delete_key(name, key);
                return new ResponseEntity<String>(key + " from " + name + " deleted", HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<String>("Key or DB unavailable!", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(method = PUT, path = "/put")
    public ResponseEntity<String> update(@RequestParam Map<String, String> requestParams) {
        return getContent(requestParams, PUT, "Unavailable key or name !", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = POST , path = "/put")
    public ResponseEntity<String> put(@RequestParam Map<String, String> requestParams) {
        return getContent(requestParams, POST, "There is a value with that key!", HttpStatus.BAD_REQUEST);
    }

    @NotNull
    private ResponseEntity<String> getContent(@RequestParam Map<String, String> requestParams, RequestMethod put, String errorMessage
            , HttpStatus errorCode) {
        String name = requestParams.get("name");
        String key = requestParams.get("key");
        String value = requestParams.get("value");
        if (name == null || key == null || value == null) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST);
        } else {
            try {
                DbManager.put(name, key, value, put);
                return new ResponseEntity<String>("Done", HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<String>(errorMessage, errorCode);
            }
        }
    }

}
