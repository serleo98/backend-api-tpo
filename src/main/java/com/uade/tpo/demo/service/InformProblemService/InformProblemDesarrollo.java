package com.uade.tpo.demo.service.InformProblemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.InformProblemEntity;
import com.uade.tpo.demo.entity.dto.InformProblemDTO;
import com.uade.tpo.demo.repository.db.InformProblemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InformProblemDesarrollo implements InformProblemInterface{
    @Autowired
    private InformProblemRepository informProblemRepository;
    @Override
    public void guardarContacto(InformProblemDTO informProblemDTO) {
        InformProblemEntity informproblem = new InformProblemEntity();
        informproblem.setNombreApellido(informProblemDTO.getNombreApellido());
        informproblem.setProblematica(informProblemDTO.getProblematica());
        informproblem.setFotos(informProblemDTO.getFotos());
        informproblem.setDescripcionProblema(informProblemDTO.getDescripcionProblema());
        
        informProblemRepository.save(informproblem);
    }
    
}
