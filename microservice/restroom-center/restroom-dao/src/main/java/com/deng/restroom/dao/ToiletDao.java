package com.deng.restroom.dao;

import com.deng.restroom.entity.ToiletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToiletDao extends JpaRepository<ToiletEntity, Long> {

    List<ToiletEntity> findByCleanAndAvailable(boolean clean, boolean available);
}
