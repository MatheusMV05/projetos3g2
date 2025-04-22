package com.brasfi.siteinstitucional.dao.impl;

import com.brasfi.siteinstitucional.dao.PaginaDAO;
import com.brasfi.siteinstitucional.entity.Pagina;
import com.brasfi.siteinstitucional.repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaginaDAOImpl implements PaginaDAO {

    @Autowired
    private PaginaRepository paginaRepository;

    @Override
    public List<Pagina> findAll() {
        return paginaRepository.findAll();
    }

    @Override
    public Optional<Pagina> findById(Long id) {
        return paginaRepository.findById(id);
    }

    @Override
    public Optional<Pagina> findBySlug(String slug) {
        return paginaRepository.findBySlug(slug);
    }

    @Override
    public Pagina save(Pagina pagina) {
        return paginaRepository.save(pagina);
    }

    @Override
    public void delete(Pagina pagina) {
        paginaRepository.delete(pagina);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return paginaRepository.existsBySlug(slug);
    }
}