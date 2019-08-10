package com.cursor.jacksonandcontroller.demo.usermvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/{email}")
    public ResponseEntity<String> getUser(@PathVariable("email") String email) throws JsonProcessingException {
        User result = service.getByEmail(email);
        if (null == result) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ObjectMapper om = new ObjectMapper();
            String body = om.writeValueAsString(result);
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User userToSave) {
        boolean result = service.saveUser(userToSave);
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
