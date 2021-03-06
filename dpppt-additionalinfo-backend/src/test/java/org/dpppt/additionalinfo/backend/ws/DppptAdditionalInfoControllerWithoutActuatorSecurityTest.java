package org.dpppt.additionalinfo.backend.ws;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(
        properties = {
            "management.endpoints.enabled-by-default=true",
            "management.endpoints.web.exposure.include=*"
        })
public class DppptAdditionalInfoControllerWithoutActuatorSecurityTest extends BaseControllerTest {
    @Autowired ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        this.publicKey = filter.getPublicKey();
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(webApplicationContext)
                        .addFilter(filter, "/*")
                        .build();
        this.objectMapper = new ObjectMapper(new JsonFactory());
        this.objectMapper.registerModule(new JavaTimeModule());
        // this makes sure, that the objectmapper does not fail, when a filter is not provided.
        this.objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
    }

    @Test
    public void testHello() throws Exception {
        super.testHello();
    }

    private Map<String, String> headers =
            Map.of(
                    "X-Content-Type-Options",
                    "nosniff",
                    "X-Frame-Options",
                    "DENY",
                    "X-Xss-Protection",
                    "1; mode=block");

    @Test
    public void testSecurityHeaders() throws Exception {
        super.testSecurityHeaders();
    }
}
