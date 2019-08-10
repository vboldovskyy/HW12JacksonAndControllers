package com.cursor.jacksonandcontroller.demo.usermvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    private Map<String, User> userMap = new HashMap<>();

    @PostConstruct
    private void populateUsers() {
        Map<String, Boolean> homeworks = new HashMap<>();
        homeworks.put("HW1", false);
        homeworks.put("HW2", true);
        User user1 = new User("Vlad", "Bold", new SimpleDateFormat().format(new Date()),
                "12345", "vlad@gmail.com", homeworks);
        userMap.put(user1.getEmail(), user1);

        Map<String, Boolean> hws = new HashMap<>();
        hws.put("Homework1", true);
        hws.put("Homework2", true);
        User user2 = new User("Olga", "Maslova", new SimpleDateFormat().format(new Date()),
                "678910", "olga@gmail.com", hws);
        userMap.put(user2.getEmail(), user2);
    }

    User getByEmail(String email) {
        User result = userMap.get(email);
        return result;
    }

    boolean saveUser(User userToSave) {
        try {
            String accessId = UUID.randomUUID().toString();
            userToSave.setAccessId(accessId);
            userMap.put(userToSave.getEmail(), userToSave);
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(User.class, new UserSerializer());
            mapper.registerModule(module);
            mapper.writeValue(new File("savedUser.json"), userToSave);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}

