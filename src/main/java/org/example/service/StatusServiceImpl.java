package org.example.service;

import org.example.entity.Status;
import org.example.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    StatusRepository statusRepository;

    @Override
    public Status getById(Integer id) {

        Optional<Status> optionalStatus = statusRepository.findById(id);

        if (optionalStatus.isPresent()) {

            return optionalStatus.get();

        } else {

            throw new NoSuchElementException("No se encontró el objeto con el ID: " + id);
        }
    }

    @Override
    public Status getByName(String name) {

        return statusRepository.findByName(name);
    }

    @Transactional
    @Override
    public void deleteStatusById(Integer id) {
        statusRepository.deleteById(id);
    }

    //Iría con atributo private en caso de no usarse en otro lado
    @Transactional
    @Override
    public void create(String statusName){

        Status status = new Status();

        status.setName(statusName);

        statusRepository.save(status);
    }

    @Transactional
    @Override
    public void addStatus(String statusName){

        create(statusName);
    }

    @Transactional
    @Override
    public void addAllStatus(){

        create("Pending");
        create("In Progress");
        create("Cancelled");
        create("Completed");
    }

}
