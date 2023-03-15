package example.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApplicationTests {

    private static final String BASE_ENDPOINT = "/cashcards";

    /**
     * If you are using the @SpringBootTest annotation with an embedded server,
     * a TestRestTemplate is automatically available and can be @Autowired into your test.
     */
    @Autowired
    TestRestTemplate restTemplate;
    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnACashCardWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_ENDPOINT + "/99", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext parsedBody = JsonPath.parse(response.getBody());
        Long id = parsedBody.read("$.id", Long.class);
        assertThat(id).isEqualTo(99);

        Double amount = parsedBody.read("$.amount", Double.class);
        assertThat(amount).isEqualTo(123.45);
    }

}
