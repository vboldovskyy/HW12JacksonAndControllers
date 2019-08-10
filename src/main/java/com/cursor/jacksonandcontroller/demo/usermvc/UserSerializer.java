package com.cursor.jacksonandcontroller.demo.usermvc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    private UserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(
            User value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("surname", value.getSurname());
        jgen.writeStringField("accessId", value.getAccessId());
        jgen.writeStringField("lastLoginDate", value.getLastLoginDate());
        jgen.writeStringField("email", value.getEmail());
        jgen.writeObjectField("homeworkToIsDone", value.getHomeworkToIsDone());
        jgen.writeEndObject();
    }
}

