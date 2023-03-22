package example.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    void shouldNotReturnACashCardWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_ENDPOINT + "/100", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldCreateANewCashCard() {
        CashCard newCashCard = new CashCard(250.00);
        ResponseEntity<Void> createResponse = restTemplate.postForEntity(BASE_ENDPOINT, newCashCard, Void.class);
        URI locationOfNewCard = createResponse.getHeaders().getLocation();

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(locationOfNewCard).isNotNull();

        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewCard, String.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext parsedBody = JsonPath.parse(getResponse.getBody());
        Long id = parsedBody.read("$.id", Long.class);
        assertThat(id).isNotNull();

        Double amount = parsedBody.read("$.amount", Double.class);
        assertThat(amount).isEqualTo(250.00);
    }

}
