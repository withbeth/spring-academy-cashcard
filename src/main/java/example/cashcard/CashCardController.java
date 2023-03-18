package example.cashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{cardId}")
    ResponseEntity<CashCard> findById(@PathVariable Long cardId) {
        return cashCardRepository.findById(cardId)
                .map(cashCard -> ResponseEntity.ok(cashCard))
                .orElse(ResponseEntity.notFound().build());
    }
}
