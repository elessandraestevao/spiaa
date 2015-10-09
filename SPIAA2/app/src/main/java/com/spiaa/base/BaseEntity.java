package com.spiaa.base;

import java.io.Serializable;

/**
 * Created by eless on 03/10/2015.
 */
public abstract class BaseEntity implements Serializable {
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
