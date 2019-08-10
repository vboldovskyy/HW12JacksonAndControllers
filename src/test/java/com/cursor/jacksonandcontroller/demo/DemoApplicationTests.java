package com.cursor.jacksonandcontroller.demo;

import com.cursor.jacksonandcontroller.demo.usermvc.User;
import com.cursor.jacksonandcontroller.demo.usermvc.UserSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import okhttp3.*;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemoApplicationTests {
OkHttpClient client = new OkHttpClient();
ObjectMapper objectMapper = new ObjectMapper();
static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
static final String BASE_URL = "http://localhost:8888/";

    @Test
    public void testGetUserPositive() throws Exception{
        Request req =  new Request.Builder().get().url(BASE_URL+"vlad@gmail.com").build();
        Response response = client.newCall(req).execute();
        assertTrue(response.code()==200);
        String jsonResponse = response.body().string();
        User user = objectMapper.readValue(jsonResponse, User.class);
        assertEquals(user.getName(), "Vlad");
        assertNull (user.getAccessId());
    }

    @Test
    public void testGetUserNegative() throws Exception{
        Request req =  new Request.Builder().get().url(BASE_URL+"gonevo@gmail.com").build();
        Response response = client.newCall(req).execute();
        assertTrue(response.code()==404);
    }

    @Test
    public void testSerializingAUserAndSavingFile() throws Exception{
        Map<String, Boolean> homeworks = new HashMap<>();
        homeworks.put("HW1", false);
        homeworks.put("HW2", true);
        User testUser = new User("Ivan", "Lev",new SimpleDateFormat().format(new Date()),
                null, "ivan@gmail.com", homeworks);
        String json = objectMapper.writeValueAsString(testUser);
        Request req = new Request.Builder().url(BASE_URL+"save/").post( RequestBody.create(json, JSON)).build();
        Response response = client.newCall(req).execute();
        assertTrue(response.code()==200);
        File file = new File("savedUser.json");
        assertTrue(file.exists());
        User saved = objectMapper.readValue(file, User.class);
        assertEquals(saved.getName(), "Ivan");
        assertEquals(saved.getEmail(), "ivan@gmail.com");
        assertNotNull(saved.getAccessId());
    }
}
