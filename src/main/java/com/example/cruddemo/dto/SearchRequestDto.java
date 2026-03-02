package com.example.cruddemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchRequestDto {

    private Integer page = 0;
    private Integer size = 10;
    private List<SearchedBy> searchedFields = new ArrayList<>();
    private List<SortBy> sortByFields = new ArrayList<>();

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<SearchedBy> getSearchedfields() {
        return searchedFields;
    }

    public List<SortBy> getSortbyfields() {
        return sortByFields;
    }

    public void setSearchedfields(List<SearchedBy> searchedFields) {
        this.searchedFields = searchedFields;
    }

    public void setSortbyfields(List<SortBy> sortByFields) {
        this.sortByFields = sortByFields;
    }
}

