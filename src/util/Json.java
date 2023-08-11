package util;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

	public static final ObjectMapper MAPPER = new ObjectMapper()
			.setSerializationInclusion(Include.NON_NULL)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	private Json() {}

	public static String jsonFormatado(Object objeto) throws JsonProcessingException {
		return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(objeto);
	}
	
	public static String json(Object objeto) throws JsonProcessingException {
		return MAPPER.writeValueAsString(objeto);
	}
	
	public static <T> T objeto(String json, Class<T> classe) throws IOException {
		return MAPPER.readValue(json, classe);
	}
}
