package com.hubstream.online.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubstream.online.api.model.Transfert;
import com.hubstream.online.api.repository.TransfertRepository;

@Service
public class TransfertService {
    @Autowired
    private TransfertRepository TransfertRepository;

    public Optional<Transfert> getTransfert(int id){
        return TransfertRepository.findById(id);
    }

    public List<Transfert> getTransferts(){
        return TransfertRepository.findAll();
    }

    public Transfert save(Transfert Transfert){
        return TransfertRepository.save(Transfert);
    }

    
}
