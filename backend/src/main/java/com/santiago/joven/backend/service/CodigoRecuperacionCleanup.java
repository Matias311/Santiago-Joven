package com.santiago.joven.backend.service;

import com.santiago.joven.backend.repository.CodigoRecuperacionRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CodigoRecuperacionCleanup {

  private final CodigoRecuperacionRepository repository;

  @Transactional
  @Scheduled(fixedRate = 3_600_000, initialDelay = 3_600_000)
  public void limpiarExpirados() {
    var antesDe = LocalDateTime.now().minusHours(24);
    var eliminados = repository.deleteExpiradosAntesDe(antesDe);
    log.debug("OTPs expirados limpiados: {}", eliminados);
  }
}
