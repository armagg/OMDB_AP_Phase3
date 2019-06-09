package api;

import DB.Db_manager;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.;

@RestController
public class Controller {
    private static final String template = "api %s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method =POST,  path = "/init_DB")
    public Response initDB(@RequestParam(value="name", defaultValue="defaultDB") String name) {

    }

    @RequestMapping(method =POST,  path = "/put")
    //todo : get two parameter for put in database
    public Content put(@RequestParam(name = "key", value = "content", required = true) String name, String key){
        return null;
    }

    @RequestMapping(method =GET, path = "/get_all")
    public Content getAll(@RequestParam(value = "key" ) String key){
        return null;
    }

    @RequestMapping(method=DELETE  ,path = "/del")
    public Content del(@RequestParam(value = "key") String key){
        return null;
    }

    @RequestMapping(method=PUT  ,path = "/update")
    public Content update(@RequestParam(value = "key") String key){
        return null;
    }

}
