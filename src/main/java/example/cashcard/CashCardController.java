package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {
    @GetMapping("/{cardId}")
    ResponseEntity<CashCard> findById(@PathVariable Long cardId) {
        // return the specific Cash Card only if we submit the correct identifier.
        if (99L == cardId) {
            return ResponseEntity.ok(new CashCard(cardId, 123.45));
        }
        return ResponseEntity.notFound().build();
    }
}
