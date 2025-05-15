package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.dto.PilaresDTO;
import com.brasfi.siteinstitucional.entity.Pilares;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.repository.PilaresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PilaresService {
    //ao longo do código tem coisas para adicionar ainda
    @Autowired
    private PilaresRepository pilaresRepository;

    public List<Pilares> listarTodos(){
        return pilaresRepository.findAll();
    }

    public Pilares buscarPorId(Long id){
        return pilaresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Página de pilares não encontrada com o id: " + id));
    }

    public Pilares salvar(PilaresDTO pilaresDTO){
        Pilares pilares = new Pilares();
        pilares.setSlug(pilaresDTO.getSlug());
        //adicionar aqui os comandos referentes aos outros atributos das classes

        return pilaresRepository.save(pilares);
    }

    public Pilares atualizar(Long id, PilaresDTO pilaresDTO){
        Pilares pilaresExistente = buscarPorId(id);

        //adicionar aqui os comandos referentes aos outros atributos das classes
        pilaresExistente.setSlug(pilaresDTO.getSlug());

        return pilaresRepository.save(pilaresExistente);
    }

    public void excluir(Long id){
        Pilares pilares = buscarPorId(id);
        pilaresRepository.delete(pilares);
    }
}