package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.entity.Evento;
import com.brasfi.siteinstitucional.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // Criar/Atualizar Evento
    public Evento saveEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    // Buscar todos os eventos
    public List<Evento> findAllEventos() {
        return eventoRepository.findAll();
    }

    // Buscar evento por ID
    public Optional<Evento> findEventoById(Long id) {
        return eventoRepository.findById(id);
    }

    // Deletar evento por ID
    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    // Buscar eventos por título (case-insensitive)
    public List<Evento> findEventosByTitulo(String titulo) {
        return eventoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Buscar eventos por local (case-insensitive)
    public List<Evento> findEventosByLocal(String local) {
        return eventoRepository.findByLocalContainingIgnoreCase(local);
    }
}