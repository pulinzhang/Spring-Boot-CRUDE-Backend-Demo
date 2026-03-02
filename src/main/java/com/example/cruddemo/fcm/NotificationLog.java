package com.example.cruddemo.fcm;

import com.example.cruddemo.enums.DevicePlatform;
import com.example.cruddemo.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(NotificationlogPK.class)
@Table(name = "notificationlog")
@EntityListeners(AuditingEntityListener.class)
public class NotificationLog {

    @Id
    @Column(name = "userid", nullable = false)
    private Integer userId;

    @Id
    @CreatedDate
    @Column(name = "createtime", nullable = false, updatable = false)
    private LocalDateTime createtime;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "click_action")
    private String clickAction;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private DevicePlatform platform;

    @Column(name = "device_token", length = 500)
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NotificationStatus status;

    @Column(name = "fcm_message_id")
    private String fcmMessageId;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "data_payload", columnDefinition = "TEXT")
    private String dataPayload;

    @Column(name = "creator", nullable = false, updatable = false)
    private Integer creator;

    @LastModifiedBy
    @Column(name = "modifier")
    private Integer modifier;

    @LastModifiedDate
    @Column(name = "lastupdated")
    private LocalDateTime lastupdated;
}

