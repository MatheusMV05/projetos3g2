package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.dto.HistoriaDTO;
import com.brasfi.siteinstitucional.entity.Governanca;
import com.brasfi.siteinstitucional.entity.Historia;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.repository.HistoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoriaService {
    //ao longo do código tem coisas para adicionar ainda
    @Autowired
    private HistoriaRepository historiaRepository;

    @Cacheable("historias")
    public List<Historia> listarTodas(){
        return historiaRepository.findAll();
    }

    @Cacheable(value = "historiaPorId", key = "#id")
    public Historia buscarPorId(Long id){
        return historiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Página de história não encontrada com o id: " + id));
    }

    @Cacheable(value = "historiaPorSlug", key = "#slug")
    public Historia buscarPorSlug(String slug) {
        return historiaRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("História não encontrada com o slug: " + slug));
    }

    public Historia salvar(HistoriaDTO historiaDTO){
        Historia historia = new Historia();
        historia.setSlug(historiaDTO.getSlug());
        //adicionar aqui os comandos referentes aos outros atributos das classes

        return historiaRepository.save(historia);
    }

    public Historia atualizar(Long id, HistoriaDTO historiaDTO){
        Historia historiaExistente = buscarPorId(id);

        //adicionar aqui os comandos referentes aos outros atributos das classes
        historiaExistente.setSlug(historiaDTO.getSlug());

        return historiaRepository.save(historiaExistente);
    }

    public void excluir(Long id){
        Historia historia = buscarPorId(id);
        historiaRepository.delete(historia);
    }
}