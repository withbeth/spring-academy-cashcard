package example.cashcard;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class CashCard {

    @Id
    private final Long id;
    private final Double amount;

    // TODO : do we need default constructor?
    // TODO : do we need a constructor with Id?

    public CashCard(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public CashCard(Double amount) {
        this(null, amount);
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashCard cashCard = (CashCard) o;
        return Objects.equals(id, cashCard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CashCard{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
