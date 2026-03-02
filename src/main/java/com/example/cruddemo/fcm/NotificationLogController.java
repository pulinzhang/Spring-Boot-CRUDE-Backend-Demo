package com.example.cruddemo.fcm;

import com.example.cruddemo.dto.SearchRequestDto;
import com.example.cruddemo.dto.SearchedBy;
import com.example.cruddemo.dto.SortBy;
import com.example.cruddemo.enums.NotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v3/notificationlog")
@RequiredArgsConstructor
public class NotificationLogController {

    private final NotificationLogService notificationLogService;

    // Search with pagination
    @PostMapping("/list")
    public ResponseEntity<Page<NotificationLog>> search(@RequestBody SearchRequestDto request) {
        // Limit size to 100
        if (request.getSize() != null && request.getSize() > 100) {
            request.setSize(100);
        }
        // If searchedFields is null, set default
        if (request.getSearchedfields() == null) {
            List<SearchedBy> list = new ArrayList<>();
            SearchedBy sb = new SearchedBy();
            sb.setFieldName("userId");
            sb.setValues(Arrays.asList("0"));
            list.add(sb);
            request.setSearchedfields(list);
        }

        // If sortByFields is empty, set default sort
        if (request.getSortbyfields() == null || request.getSortbyfields().size() == 0) {
            List<SortBy> defaultSort = new ArrayList<>();
            SortBy sortBy = new SortBy();
            sortBy.setFieldName("createtime");
            sortBy.setDirection("DESC");
            defaultSort.add(sortBy);
            request.setSortbyfields(defaultSort);
        }

        return ResponseEntity.ok(notificationLogService.findXByCriteria(request));
    }

    @PostMapping("/byid")
    public ResponseEntity<List<NotificationLog>> notificationlogbyid(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(notificationLogService.findById(ids));
    }

    // find all
    @GetMapping("/findall")
    public ResponseEntity<List<NotificationLog>> findAll() {
        return ResponseEntity.ok(notificationLogService.findAll());
    }

    // Create
    @PostMapping
    public ResponseEntity<NotificationLog> create(@RequestBody NotificationLog notificationLog) {
        NotificationLog created = notificationLogService.create(notificationLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Update multiple
    @PutMapping
    public ResponseEntity<List<NotificationLog>> notificationlogupdate(@RequestBody List<NotificationLog> notificationlogs) {
        return ResponseEntity.ok(notificationLogService.update(notificationlogs));
    }

    // Delete by list of IDs
    @DeleteMapping
    public ResponseEntity<List<NotificationlogPK>> notificationlogdeletebyids(@RequestBody List<NotificationlogPK> ids) {
        return ResponseEntity.ok(notificationLogService.deleteAllByIds(ids));
    }
}

