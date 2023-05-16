package org.example.repository;

import org.example.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findByName(String name);

    void deleteById(Integer id);
}
