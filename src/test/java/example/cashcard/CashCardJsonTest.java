package example.cashcard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test class for verifying data contracts between client and server
 *
 * Q. @JsonTest?
 * A. Annotation for a JSON test that focuses only on JSON serialization.
 * Using this annotation will disable full auto-configuration and instead apply only configuration relevant to JSON tests (i.e. @JsonComponent, Jackson Module)
 * By default, tests annotated with JsonTest will also initialize JacksonTester, JsonbTester and GsonTester fields.
 * More fine-grained control can be provided through the @AutoConfigureJsonTesters annotation.
 */
@JsonTest
class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> json;

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = new CashCard[]{
                new CashCard(99L, 123.45),
                new CashCard(100L, 1.00),
                new CashCard(101L, 150.00)
        };
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        CashCard cashCard = cashCards[0];
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("single.json");

        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);

        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
                {
                    "id":99,
                    "amount":123.45
                }
                """;
        assertThat(json.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45));
        assertThat(json.parseObject(expected).getId()).isEqualTo(99L);
        assertThat(json.parseObject(expected).getAmount()).isEqualTo(123.45);
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected = """
                [
                    {"id":99, "amount": 123.45},
                    {"id":100, "amount": 1.00},
                    {"id":101, "amount": 150.00}
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
    }

}
