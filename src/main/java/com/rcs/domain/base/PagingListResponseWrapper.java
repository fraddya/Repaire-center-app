package com.rcs.domain.base;


import com.rcs.enums.RestApiResponseStatus;

import java.util.List;

public class PagingListResponseWrapper<T> extends BaseResponseWrapper {

    private List<T> content;
    private Pagination pagination;

    public PagingListResponseWrapper(List<T> content, Pagination pagination) {
        super(RestApiResponseStatus.OK);
        this.content = content;
        this.pagination = pagination;
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public static class Pagination {
        private Integer pageNumber;
        private Integer pageSize;
        private Integer totalPages;
        private Long totalRecords;

        public Pagination(Integer pageNumber, Integer pageSize, Integer totalPages, Long totalRecords) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.totalPages = totalPages;
            this.totalRecords = totalRecords;
        }

        public Integer getPageNumber() {
            return this.pageNumber;
        }

        public void setPageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
        }

        public Integer getPageSize() {
            return this.pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getTotalPages() {
            return this.totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Long getTotalRecords() {
            return this.totalRecords;
        }

        public void setTotalRecords(Long totalRecords) {
            this.totalRecords = totalRecords;
        }
    }
}
