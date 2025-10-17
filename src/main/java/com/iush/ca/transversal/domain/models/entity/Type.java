package com.iush.ca.transversal.domain.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TICKET_TYPE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TICKET_TYPE")
    private Integer id;

    @Column(name = "NAME", length = 20)
    private String name;

    @Column(name = "DESCRIPTION", length = 200)
    private String description;

}
