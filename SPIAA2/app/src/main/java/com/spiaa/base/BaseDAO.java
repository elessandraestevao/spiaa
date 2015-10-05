package com.spiaa.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.spiaa.dados.DatabaseHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by eless on 05/10/2015.
 */
public interface BaseDAO<E extends BaseEntity> {

    void insert(E entity) throws Exception;

    E select(E entity) throws Exception;

    void update(E entity) throws Exception;

    void delete(E entity) throws Exception;
}
