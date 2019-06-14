package api;

import DB.DbManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class Controller {
    private final AtomicLong counter = new AtomicLong();
    @RequestMapping(method = POST, path = "/init_DB")
    public Content initDB(@RequestParam Map<String,String> requestParams) {

        String name = requestParams.get("name");
        if (name == null){
            name = "default_name";
        }

        try {
            DbManager.init(name);
        }catch (IllegalArgumentException ignored){
            return new Content("there is a db with this name", 400);
        }
        return new Content("DB created", 200);
    }

    @RequestMapping(method = POST, path = "/put")
    //todo : get two parameter for put in database
    public Content put(@RequestParam(name = "key", value = "content", required = true) String name, String key) {
        try {
            DbManager.put(name, key);
        }
    }

    @RequestMapping(method = GET, path = "/get_all")
    public Content getAll(@RequestParam(value = "key") String key) {
        return null;
    }

    @RequestMapping(method = DELETE, path = "/del")
    public Content del(@RequestParam(value = "key") String key) {
        return null;
    }

    @RequestMapping(method = PUT, path = "/update")
    public Content update(@RequestParam(value = "key") String key) {
        return null;
    }


}
