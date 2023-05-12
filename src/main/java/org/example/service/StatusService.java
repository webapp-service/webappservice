package org.example.service;

import org.example.entity.Status;
import org.example.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    @Autowired
    StatusRepository statusRepository;

    public Status findById(Integer id) {


        return statusRepository.findById(id).get();


    }


}
