package com.iush.ca.user.domain.models.entity;


import com.iush.ca.ticket.domain.models.entity.Ticket;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "USER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    private Integer id;

    @Column(name = "USERNAME", length = 20)
    private String username;

    @Column(name = "PASSWORD", length = 150)
    private String password;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ROLE" , length = 5)
    private String role;

    @Column(name = "ACTIVE" , length = 1)
    private String active;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> createdTickets;

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> assignedTickets;


}
