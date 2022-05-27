package com.sdp3.SDP3.entites;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class Favourite {
    @Id
    @SequenceGenerator(
            name = "favourite_id_sequence",
            sequenceName = "favourite_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "favourite_id_sequence"
    )
    private Long favId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  Users users;

}
