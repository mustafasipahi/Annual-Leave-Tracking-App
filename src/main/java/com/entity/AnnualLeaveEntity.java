package com.entity;

import com.enums.AnnualLeaveStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "holidays_count", nullable = false)
    private int holidaysCount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnnualLeaveStatus status;

    @CreatedDate
    private Date createdDate;
}
