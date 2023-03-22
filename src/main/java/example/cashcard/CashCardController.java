package example.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    ResponseEntity<Iterable<CashCard>> findAll(Pageable pageable) {
        Page<CashCard> page = cashCardRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.DESC, "amount"))
                ));
        return ResponseEntity.ok(page.toList());
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
