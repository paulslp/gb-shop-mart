package ru.gb.gbshopmart.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.gbshopmart.entity.common.InfoEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "gb_order")
@EntityListeners(AuditingEntityListener.class)
public class Order extends InfoEntity {

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "FIRST_NAME")
    private String firstname;

    @Column(name = "LAST_NAME")
    private String lastname;

    @Column(name = "PHONE_NUMBER")
    private String phone;

    @Column(name = "MAIL")
    private String mail;

    @Column(name = "STATUS")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "gb_order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Builder
    public Order(Long id, int version, String createdBy, LocalDateTime createdDate, String lastModifiedBy,
                 LocalDateTime lastModifiedDate, String address, String firstname, String lastname, String phone,
                 String mail, String status, List<Product> products) {
        super(id, version, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.mail = mail;
        this.status = status;
        this.products = products;
    }
}
