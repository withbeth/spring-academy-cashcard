package example.cashcard;

import java.util.Objects;

public class CashCard {
    private final Long id;
    private final Double amount;

    public CashCard(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
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
