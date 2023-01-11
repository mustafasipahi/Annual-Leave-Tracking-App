package com.entity;

import com.enums.AnnualLeaveStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@Table(name = "annual_leave")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AnnualLeaveEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnnualLeaveStatus status;

    @CreatedDate
    private Date createdDate;
}
