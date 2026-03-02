package com.example.cruddemo.fcm;

import com.example.cruddemo.fcm.NotificationLog;
import com.example.cruddemo.enums.NotificationStatus;
import com.example.cruddemo.fcm.NotificationlogPK;
import com.example.cruddemo.fcm.NotificationLogRepository;
import com.example.cruddemo.dto.SearchRequestDto;
import com.example.cruddemo.dto.SearchedBy;
import com.example.cruddemo.dto.SortBy;
import com.example.cruddemo.repository.SearchRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationLogService {

    private final NotificationLogRepository xRepository;
    private final SearchRepository searchRepository;

    // Create
    @Transactional
    public NotificationLog create(NotificationLog notificationLog) {
        if (notificationLog.getCreatetime() == null) {
            notificationLog.setCreatetime(LocalDateTime.now());
        }
        return xRepository.save(notificationLog);
    }

    // Read all
    public List<NotificationLog> findAll() {
        return xRepository.findAll();
    }

    // Read by list of userIds
    public List<NotificationLog> findById(List<Integer> ids) {
        return xRepository.findByUserIdIn(ids);
    }


    // Update multiple
    @Transactional
    public List<NotificationLog> update(List<NotificationLog> notificationLogs) {
        return xRepository.saveAll(notificationLogs);
    }

    // Delete by list of IDs
    @Transactional
    public List<NotificationlogPK> deleteAllByIds(List<NotificationlogPK> ids) {
        xRepository.deleteAllById(ids);
        return ids;
    }

    public Page<NotificationLog> findXByCriteria(SearchRequestDto request) {
        return searchRepository.findXByCriteria(NotificationLog.class, request);

    }
}

