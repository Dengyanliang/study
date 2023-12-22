package com.deng.employee.dao;

import com.deng.employee.entity.EmployeeActivityEntity;
import com.deng.employee.pojo.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 16:37
 */
public interface EmployeeActivityDao extends JpaRepository<EmployeeActivityEntity,Long> {

    int countByEmployeeIdAndActivityTypeAndActive(Long employeeId, ActivityType activityType,boolean active);
}
