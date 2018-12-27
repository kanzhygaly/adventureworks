package kz.ya.adventureworks.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import kz.ya.adventureworks.AdventureworksApplication;
import kz.ya.adventureworks.entity.ProductReview;

/**
 * @author yerlana
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdventureworksApplication.class)
@WebAppConfiguration
public class ProductReviewControllerTest {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny().orElse(null);

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @After
    public void tearDown() throws IOException {
    }

    @Test
    public void testNewProductReviewByBean() throws Exception {
        ProductReview review = new ProductReview("Elvis Presley", "theking@elvismansion.com", 
                3l, 4, "I really love the product and will recommend!");
        String requestJson = convertToJson(review);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                .contentType(this.contentType).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
    @Test
    public void testNewProductReviewByText() throws Exception {
        String requestJson = "{"
                + "\"name\":\"John Smith\","
                + "\"email\":\"john@fourthcoffee.com\","
                + "\"productid\":709,"
                + "\"rating\":5,"
                + "\"review\":\"I really love the product and will recommend!\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                .contentType(this.contentType).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testNewProductReviewFailed() throws Exception {
        String requestJson = convertToJson(new ProductReview());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                .contentType(this.contentType).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    protected String convertToJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
