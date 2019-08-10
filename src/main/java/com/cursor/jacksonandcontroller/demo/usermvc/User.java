package com.cursor.jacksonandcontroller.demo.usermvc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;
    private String surname;
    private String lastLoginDate;
    private String accessId;
    private String email;
    private Map<String, Boolean> homeworkToIsDone = new HashMap<>();

    @JsonIgnore
    public String getAccessId() {
        return accessId;
    }
}
