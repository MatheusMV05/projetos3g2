package com.brasfi.siteinstitucional.controller;

import com.brasfi.siteinstitucional.entity.Evento;
import com.brasfi.siteinstitucional.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos") // Define o path base para todos os endpoints deste controlador
public class EventoController {

    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // GET /api/eventos
    // Busca todos os eventos ou filtra por título/local
    @GetMapping
    public ResponseEntity<List<Evento>> getAllEventos(@RequestParam(required = false) String titulo,
                                                      @RequestParam(required = false) String local) {
        if (titulo != null) {
            return ResponseEntity.ok(eventoService.findEventosByTitulo(titulo));
        }
        if (local != null) {
            return ResponseEntity.ok(eventoService.findEventosByLocal(local));
        }
        return ResponseEntity.ok(eventoService.findAllEventos());
    }

    // GET /api/eventos/{id}
    // Busca um evento específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Long id) {
        Optional<Evento> evento = eventoService.findEventoById(id);
        return evento.map(ResponseEntity::ok) // Se o evento for encontrado, retorna 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Caso contrário, retorna 404 Not Found
    }

    // POST /api/eventos
    // Cria um novo evento
    @PostMapping
    public ResponseEntity<Evento> createEvento(@RequestBody Evento evento) {
        Evento savedEvento = eventoService.saveEvento(evento);
        return new ResponseEntity<>(savedEvento, HttpStatus.CREATED); // Retorna 201 Created
    }

    // PUT /api/eventos/{id}
    // Atualiza um evento existente
    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id, @RequestBody Evento eventoDetails) {
        Optional<Evento> eventoOptional = eventoService.findEventoById(id);
        if (eventoOptional.isPresent()) {
            Evento eventoExistente = eventoOptional.get();
            eventoExistente.setTitulo(eventoDetails.getTitulo());
            eventoExistente.setDescricao(eventoDetails.getDescricao());
            eventoExistente.setDataInicio(eventoDetails.getDataInicio());
            eventoExistente.setDataFim(eventoDetails.getDataFim());
            eventoExistente.setLocal(eventoDetails.getLocal());

            Evento updatedEvento = eventoService.saveEvento(eventoExistente);
            return ResponseEntity.ok(updatedEvento); // Retorna 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    // DELETE /api/eventos/{id}
    // Deleta um evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        if (eventoService.findEventoById(id).isPresent()) {
            eventoService.deleteEvento(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }
}