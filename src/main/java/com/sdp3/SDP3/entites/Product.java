package com.sdp3.SDP3.entites;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class Product {
    @Id
    @SequenceGenerator(
            name = "product_id_sequence",
            sequenceName = "product_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_id_sequence"
    )
    private Long productId;
    @Column(length = 500)
    private String productTitle;
    @Column(length = 500)
    private String productDescription;
    private String productImage1;
    private String productImage2;
    private String productVideoUrl;
    private String productCost;

    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "productId"
    )
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name="store_id", nullable=false)
    private Store store;
}
