package com.example.cruddemo.fcm;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class NotificationlogPK implements Serializable {

    private Integer userId;
    private LocalDateTime createtime;
}

