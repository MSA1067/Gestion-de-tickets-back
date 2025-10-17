package com.iush.ca.ticket.domain.models.entity;

import com.iush.ca.transversal.domain.models.entity.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TICKET_HISTORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TICKET_HISTORY")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_TICKET" , referencedColumnName = "ID_TICKET")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "ID_STATUS_TYPE" , referencedColumnName = "ID_STATUS_TYPE")
    private Status status;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "ACTIVE")
    private String active;


}
