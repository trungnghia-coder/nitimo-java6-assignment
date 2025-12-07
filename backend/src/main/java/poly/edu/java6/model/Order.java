package poly.edu.java6.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NamedEntityGraph(
    name = "order-with-all-details",
    attributeNodes = {
        @NamedAttributeNode("details"),
        @NamedAttributeNode(value = "user"),
        @NamedAttributeNode(value = "details", subgraph = "detail-subgraph")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "detail-subgraph",
            attributeNodes = {
                    @NamedAttributeNode("product"),
                    @NamedAttributeNode("variant")
            }
        )
    }
)
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "orderCode", length = 16)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Column(name = "orderDate", updatable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('PENDING','CONFIRMED','SHIPPING','COMPLETED','CANCELLED')")
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "totalAmount", precision = 14, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "shippingAddress")
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentMethod", columnDefinition = "ENUM('COD','BANK','MOMO')")
    private PaymentMethod paymentMethod = PaymentMethod.COD;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus", columnDefinition = "ENUM('PAID','UNPAID','REFUNDED')")
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    @Column(name = "transactionId", length = 16)
    private String transactionId;

    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details;

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        SHIPPING,
        COMPLETED,
        CANCELLED
    }

    public enum PaymentMethod {
        COD,
        BANK,
        MOMO
    }

    public enum PaymentStatus {
        PAID,
        UNPAID,
        REFUNDED
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


