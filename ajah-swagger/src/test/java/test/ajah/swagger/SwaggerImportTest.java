package test.ajah.swagger;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.ajah.swagger.out.SwaggerOut;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SwaggerImportTest {

	final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void importTest() throws IOException {
		SwaggerOut out = objectMapper.readValue(getClass().getResourceAsStream("/swagger.json"), SwaggerOut.class);
		objectMapper.writeValue(new File("/tmp/swagger-test.json"), out);
	}
}
