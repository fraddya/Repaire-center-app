package com.rcs.domain.base;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Nuwan
 */
@Data
public class MapResponseWrapper<T> extends BaseResponseWrapper {

    private Map<String, List<T>> content;

    public MapResponseWrapper(Map<String, List<T>> content) {
        this.content = content;
    }

}
