package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.ContactoRequest;
import com.santiago.joven.backend.dto.ContactoResponse;
import com.santiago.joven.backend.dto.ContactoUpdate;
import com.santiago.joven.backend.mapper.ContactoMapper;
import com.santiago.joven.backend.repository.ContactoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link ContactoService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class ContactoServiceImpl implements ContactoService {

  private final ContactoRepository repository;
  private final ContactoMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<ContactoResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public ContactoResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Contacto no encontrado con id: " + id));
  }

  @Override
  public ContactoResponse create(ContactoRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public ContactoResponse update(UUID id, ContactoUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Contacto no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Contacto no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ContactoResponse> findNoRespondidos() {
    return repository.findByRespondidoFalseOrderByFechaContactoDesc().stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ContactoResponse> findByEmail(String email) {
    return repository.findByEmail(email).stream().map(mapper::toResponse).toList();
  }
}
