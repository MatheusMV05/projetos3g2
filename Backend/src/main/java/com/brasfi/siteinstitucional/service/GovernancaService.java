package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.dto.GovernancaDTO;
import com.brasfi.siteinstitucional.entity.Governanca;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.model.Pagina;
import com.brasfi.siteinstitucional.repository.GovernancaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class GovernancaService {
    //ao longo do código tem coisas para adicionar ainda
    @Autowired
    private GovernancaRepository governancaRepository;

    public List<Governanca> listarTodas(){
        return governancaRepository.findAll();
    }

    public Governanca buscarPorId(Long id){
        return governancaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Governança não encontrada com o id: " + id));
    }

    public Governanca buscarPorSlug(String slug) {
        return governancaRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Governança não encontrada com o slug: " + slug));
    }

    public Governanca salvar(GovernancaDTO governancaDTO){
        Governanca governanca = new Governanca();
        governanca.setSlug(governancaDTO.getSlug());
        //adicionar aqui os comandos referentes aos outros atributos das classes

        return governancaRepository.save(governanca);
    }

    public Governanca atualizar(Long id, GovernancaDTO governancaDTO){
        Governanca governancaExistente = buscarPorId(id);

        //adicionar aqui os comandos referentes aos outros atributos das classes
        governancaExistente.setSlug(governancaDTO.getSlug());

        return governancaRepository.save(governancaExistente);
    }

    public void excluir(Long id){
        Governanca governanca = buscarPorId(id);
        governancaRepository.delete(governanca);
    }

}
