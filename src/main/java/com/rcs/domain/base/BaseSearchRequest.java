package com.rcs.domain.base;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public abstract class BaseSearchRequest {

    private @Min(
            value = 1L,
            message = "{searchRequest.pagination.pageNumber.invalid}"
    ) @Max(
            value = 1000L,
            message = "{searchRequest.pagination.pageNumber.invalid}"
    ) Integer pageNumber = 1;
    private @Min(
            value = 10L,
            message = "{searchRequest.pagination.pageSize.invalid}"
    ) @Max(
            value = 1000L,
            message = "{searchRequest.pagination.pageSize.invalid}"
    ) Integer pageSize = 50;
    private @Pattern(
            regexp = "asc|desc",
            flags = {Pattern.Flag.CASE_INSENSITIVE},
            message = "{searchRequest.pagination.sortDirection.invalid}"
    ) String sortDirection = "desc";

    public abstract String getSortProperty();

    public BaseSearchRequest() {
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public String getSortDirection() {
        return this.sortDirection;
    }

    public void setPageNumber(final Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSortDirection(final String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseSearchRequest)) {
            return false;
        } else {
            BaseSearchRequest other = (BaseSearchRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Integer this$pageSize;
                Integer other$pageSize;
                label42: {
                    this$pageSize = this.getPageNumber();
                    other$pageSize = other.getPageNumber();
                    if (this$pageSize == null) {
                        if (other$pageSize == null) {
                            break label42;
                        }
                    } else if (this$pageSize.equals(other$pageSize)) {
                        break label42;
                    }

                    return false;
                }

                this$pageSize = this.getPageSize();
                other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                Object this$sortDirection = this.getSortDirection();
                Object other$sortDirection = other.getSortDirection();
                if (this$sortDirection == null) {
                    if (other$sortDirection != null) {
                        return false;
                    }
                } else if (!this$sortDirection.equals(other$sortDirection)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseSearchRequest;
    }

    public int hashCode() {
        int result = 1;
        Object $pageNumber = this.getPageNumber();
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $sortDirection = this.getSortDirection();
        result = result * 59 + ($sortDirection == null ? 43 : $sortDirection.hashCode());
        return result;
    }

    public String toString() {
        Integer var10000 = this.getPageNumber();
        return "BaseSearchRequest(pageNumber=" + var10000 + ", pageSize=" + this.getPageSize() + ", sortDirection=" + this.getSortDirection() + ")";
    }
}
