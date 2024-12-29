package com.ams.developer.pizza.service.dto.Pageable;

public class PageableDto {
    private Integer pageNumber;
    private Integer pageSize;
    private SortDto sort;
    private Integer offset;
    private Boolean paged;
    private Boolean unpaged;

    public PageableDto() {
    }

    public PageableDto(Integer pageNumber, Integer pageSize, SortDto sort, Integer offset, Boolean paged, Boolean unpaged) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.offset = offset;
        this.paged = paged;
        this.unpaged = unpaged;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public SortDto getSort() {
        return sort;
    }

    public void setSort(SortDto sort) {
        this.sort = sort;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Boolean getPaged() {
        return paged;
    }

    public void setPaged(Boolean paged) {
        this.paged = paged;
    }

    public Boolean getUnpaged() {
        return unpaged;
    }

    public void setUnpaged(Boolean unpaged) {
        this.unpaged = unpaged;
    }
}
