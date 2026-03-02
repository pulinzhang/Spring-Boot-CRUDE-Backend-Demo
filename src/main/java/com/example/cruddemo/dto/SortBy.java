package com.example.cruddemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SortBy {

    private String fieldName;
    private String direction; // ASC, DESC
}

