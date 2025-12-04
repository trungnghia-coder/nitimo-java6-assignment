package poly.edu.java6.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString(exclude = { "cart", "variant" })
@Entity
@Table(name = "cart_items")
public class CartItem {
    @EmbeddedId
    private CartItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cartCode")
    @JoinColumn(name = "cartCode", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("variantId")
    @JoinColumn(name = "variantId", nullable = false)
    private ProductVariant variant;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    @Data
    @Embeddable
    public static class CartItemId implements Serializable {
        private String cartCode;
        private Integer variantId;
    }

    public BigDecimal getLineTotalPrice() {
        BigDecimal unitPrice = this.variant.getProduct().getPrice().subtract(this.variant.getProduct().getDiscount());
        return unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
