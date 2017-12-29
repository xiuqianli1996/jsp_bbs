package com.jspbbs.core.service;

import java.util.List;

public interface ServiceInterface<T> {
    List<T> list();

    T get(int id);

    boolean save(T t);

    boolean update(T t);

    boolean deleteByID(int id);
}
