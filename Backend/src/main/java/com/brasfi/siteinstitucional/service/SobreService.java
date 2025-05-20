package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.dto.SobreDTO;
import com.brasfi.siteinstitucional.entity.Governanca;
import com.brasfi.siteinstitucional.entity.Sobre;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.repository.SobreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SobreService {
    //ao longo do código tem coisas para adicionar ainda
    @Autowired
    private SobreRepository sobreRepository;

    @Cacheable("sobres")
    public List<Sobre> listarTodos(){
        return sobreRepository.findAll();
    }

    @Cacheable(value = "sobresPorId", key = "#id")
    public Sobre buscarPorId(Long id){
        return sobreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Página sobre não encontrada com o id: " + id));
    }

    @Cacheable(value = "sobrePorSlug", key = "#slug")
    public Sobre buscarPorSlug(String slug) {
        return sobreRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException(" 'Pilar' não encontrado com o slug: " + slug));
    }

    public Sobre salvar(SobreDTO sobreDTO){
        Sobre sobre = new Sobre();
        sobre.setSlug(sobreDTO.getSlug());
        //adicionar aqui os comandos referentes aos outros atributos das classes

        return sobreRepository.save(sobre);
    }

    public Sobre atualizar(Long id, SobreDTO sobreDTO){
        Sobre sobreExistente = buscarPorId(id);

        //adicionar aqui os comandos referentes aos outros atributos das classes
        sobreExistente.setSlug(sobreDTO.getSlug());

        return sobreRepository.save(sobreExistente);
    }

    public void excluir(Long id){
        Sobre sobre = buscarPorId(id);
        sobreRepository.delete(sobre);
    }
}