package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.entity.Depoimento;
import com.brasfi.siteinstitucional.exception.ResourceNotFoundException;
import com.brasfi.siteinstitucional.repository.DepoimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepoimentoService {

    @Autowired
    private DepoimentoRepository depoimentoRepository;

    @Cacheable("depoimentos")
    public List<Depoimento> listarTodosSemFiltro() {
        return depoimentoRepository.findAll();
    }

    public Page<Depoimento> listarTodos(String nome, Pageable pageable) {
        if (nome != null && !nome.isEmpty()) {
            return depoimentoRepository.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            return depoimentoRepository.findAll(pageable);
        }
    }

    public Depoimento buscarPorId(Long id) {
        return depoimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Depoimento não encontrado com o id: " + id));
    }
}
