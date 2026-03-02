package com.example.cruddemo.fcm;

import com.example.cruddemo.fcm.NotificationLog;
import com.example.cruddemo.fcm.NotificationlogPK;
import com.example.cruddemo.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, NotificationlogPK>, JpaSpecificationExecutor<NotificationLog> {

    List<NotificationLog> findByUserId(Integer userId);

    List<NotificationLog> findByStatus(NotificationStatus status);

    List<NotificationLog> findByUserIdAndStatus(Integer userId, NotificationStatus status);

    List<NotificationLog> findByCreatetimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<NotificationLog> findByUserIdOrderByCreatetimeDesc(Integer userId);

    List<NotificationLog> findByUserIdIn(List<Integer> userIds);
}

