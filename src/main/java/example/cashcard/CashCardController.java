package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping
    ResponseEntity<Iterable<CashCard>> findAll() {
        return ResponseEntity.ok(cashCardRepository.findAll());
    }

    @GetMapping("/{cardId}")
    ResponseEntity<CashCard> findById(@PathVariable Long cardId) {
        return cashCardRepository.findById(cardId)
                .map(cashCard -> ResponseEntity.ok(cashCard))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<Void> create(@RequestBody CashCard cashCard) {
        CashCard saved = cashCardRepository.save(cashCard);
        return ResponseEntity.created(URI.create("/cashcards/" + saved.getId())).build();
    }
}
