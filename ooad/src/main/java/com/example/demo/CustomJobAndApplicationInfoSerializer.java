package com.example.demo;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import com.example.demo.model.*;

public class CustomJobAndApplicationInfoSerializer extends JsonSerializer<CustomJobAndApplicationInfo> {

	@Override
	public void serialize(CustomJobAndApplicationInfo value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		// TODO Auto-generated method stub
		gen.writeStartArray();
		gen.writeStartObject();
		gen.writeNumberField("jobId", value.jobId);
		gen.writeStringField("description", value.description);
		gen.writeStringField("role", value.role);
		gen.writeStringField("location", value.location);
		gen.writeStringField("skills", value.skills);
		gen.writeObjectField("apps", value.apps);
		gen.writeEndObject();
		gen.writeEndArray();
	}

}
