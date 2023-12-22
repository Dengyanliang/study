package com.deng.employee.entity;

import com.deng.employee.pojo.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 16:30
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "employee_activity")
public class EmployeeActivityEntity {

    @Id // 这个导入的包一定是 javax.persistence.Id，不能是org.springframework.data.annotation.Id，否则会报错
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "resource_id")
    private Long resourceId;

    // 把枚举类中的序列号作为参数保存到数据库中
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type")
    private ActivityType activityType;

//    @CreatedDate
    @CreationTimestamp
    @Column(name = "start_time")
    private Date startTime;

    @UpdateTimestamp
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "active")
    private boolean active;

}
