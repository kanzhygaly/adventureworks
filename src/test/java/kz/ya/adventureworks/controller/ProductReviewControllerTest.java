package kz.ya.adventureworks.controller;

import kz.ya.adventureworks.AdventureworksApplication;
import kz.ya.adventureworks.dto.ProductReviewDTO;
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
import javax.servlet.Filter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

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
    private Filter springSecurityFilterChain;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny().orElse(null);

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        final MockHttpServletRequestBuilder defaultRequestBuilder = MockMvcRequestBuilders.get("/dummy-path");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .defaultRequest(defaultRequestBuilder)
                .alwaysDo(result -> setSessionBackOnRequestBuilder(defaultRequestBuilder, result.getRequest()))
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();

        // login
        try {
            performLogin("advUser", "advPass");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @After
    public void tearDown() {
        // logout
        try {
            performLogout();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void performLogin(final String username, final String password) throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(
                SecurityMockMvcRequestBuilders.formLogin().user(username).password(password));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    public void performLogout() throws Exception {
        final ResultActions resultActions = this.mockMvc.perform(SessionLogoutRequestBuilder.sessionLogout());

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    private MockHttpServletRequest setSessionBackOnRequestBuilder(
            final MockHttpServletRequestBuilder requestBuilder, final MockHttpServletRequest request) {

        requestBuilder.session((MockHttpSession) request.getSession());
        return request;
    }

    @Test
    public void testNewProductReviewByDTO() throws Exception {
        ProductReviewDTO review = new ProductReviewDTO("Elvis Presley", "theking@elvismansion.com",
                3, 4, "I really love the product and will recommend!");
        String requestJson = convertToJson(review);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                .contentType(this.contentType).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
    public void testNewProductReviewByText() throws Exception {
        String requestJson = "{"
                + "\"name\":\"John Smith\","
                + "\"email\":\"john@fourthcoffee.com\","
                + "\"productid\":709,"
                + "\"rating\":5,"
                + "\"review\":\"I really love the product and will recommend!\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                .contentType(this.contentType).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testNewProductReviewFailed() throws Exception {
        String requestJson = convertToJson(new ProductReviewDTO());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                .contentType(this.contentType).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String convertToJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
