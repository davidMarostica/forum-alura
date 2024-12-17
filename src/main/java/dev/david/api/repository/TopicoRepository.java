package dev.david.api.repository;

import dev.david.api.modelo.Topico;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;

@Repository
public interface TopicoRepository  extends JpaRepository<Topico, Long>{

    Page<Topico> findAllByAutorActivoTrue(Pageable paginacion);
}
