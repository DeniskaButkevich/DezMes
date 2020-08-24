package dez.DezMes.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("test")

public class TestController {

    @GetMapping()
    public Integer test(){
        Random rand = new Random();

        Integer test = rand.nextInt(50);
        return test;
    }
}
