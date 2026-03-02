package com.example.cruddemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchedBy {

    private String fieldName;
    private List<String> values;
    private String operator; // EQ, NE, LIKE, IN, BETWEEN, etc.
}

