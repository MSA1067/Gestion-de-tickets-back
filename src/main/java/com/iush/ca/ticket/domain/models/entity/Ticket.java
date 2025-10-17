package com.iush.ca.ticket.domain.models.entity;

import com.iush.ca.transversal.domain.models.entity.Status;
import com.iush.ca.transversal.domain.models.entity.Type;
import com.iush.ca.user.domain.models.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TICKET")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TICKET")
    private Integer id;

    @Column(name = "TITLE", length = 80)
    private String title;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_STATUS_TYPE" , referencedColumnName = "ID_STATUS_TYPE")
    private Status status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime  updatedAt;

    @ManyToOne
    @JoinColumn(name = "ID_CREATED_BY_USER" , referencedColumnName = "ID_USER")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "ID_ASSIGNED_USER" , referencedColumnName = "ID_USER")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "ID_TICKET_TYPE" , referencedColumnName = "ID_TICKET_TYPE")
    private Type type;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketHistory> ticketHistories;

}
