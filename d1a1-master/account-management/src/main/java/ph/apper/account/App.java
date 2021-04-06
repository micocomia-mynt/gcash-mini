package ph.apper.account;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @RestController
    @RequestMapping("account")
    public static class AccountController{
        /* Rest template is used to create
        applications that consume RESTful Web Services. Allows program
        to connect to another program through HTTP*/
        private final RestTemplate restTemplate;

        @Value("${activity.url}")
        private String activityUrl;

        public AccountController(RestTemplate restTemplate){
            this.restTemplate = restTemplate;
        }

        @PostMapping
        public ResponseEntity register(@RequestBody CreateAccountRequest request){
            System.out.println(request);

            // Preparing request
            Activity activity = new Activity();
            activity.setAction("REGISTRATION");
            activity.setIdentifier("email="+request.getEmail());

            // Create a POST for Activity endpoint
            // We return Object.class since we're only returning a status code
            // You may use 'exchange' for other methods such as PATCH
            ResponseEntity<Object> response
                    = restTemplate.postForEntity(activityUrl, activity, Object.class);

            // Check the response of Activity
            if (response.getStatusCode().is2xxSuccessful()){
                System.out.println("Success");
            }else{
                System.out.println("Err: " + response.getStatusCode());
            }

            return ResponseEntity.ok().build();
        }
    }

    @Data
    public static class Activity {
        private String action;
        private String identifier;
    }

    @Data
    public static class CreateAccountRequest{
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    }
}
