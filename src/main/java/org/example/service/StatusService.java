package org.example.service;

import org.example.entity.Status;

public interface StatusService {
    Status getById(Integer id);

    Status getByName(String name);

    void deleteStatusById(Integer id);

    void create(String statusName);
    void addStatus(String statusName);

    void addAllStatus();
}
