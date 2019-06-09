package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class Controller {
    private static final String template = "hello %s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method =POST,  path = "/init_DB")
    public Greeting initDB(@RequestParam(value="name", defaultValue="defaultDB") String name) {
        return null;
    }

    @RequestMapping(method =PUT,  path = "/put")
    //todo : get two parameter for put in database
    public Greeting put(@RequestParam(name = "key", value = "content", required = true) String name, String key){
        return null;
    }

    @RequestMapping(method =GET, path = "/get_all")
    public Greeting getAll(@RequestParam(value = "key" ) String key){
        return null;
    }

}
