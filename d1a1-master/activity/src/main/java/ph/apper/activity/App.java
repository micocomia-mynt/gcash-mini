package ph.apper.activity;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

    @RestController
    @RequestMapping("activity")
    public static class ActivityController{
        @PostMapping
        public ResponseEntity create(@RequestBody Request request){
            System.out.println(request);

            return ResponseEntity.ok().build();
        }
    }

    @Data
    public static class Request{
        private String action;
        private String identifier;
    }
}
