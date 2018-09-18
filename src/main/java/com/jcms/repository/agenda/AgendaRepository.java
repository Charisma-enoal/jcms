package com.jcms.repository.agenda;

import com.jcms.domain.JcmsAgendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendaRepository extends JpaRepository<JcmsAgendaEntity,Long> {
    List<JcmsAgendaEntity> findAllByUserName(String userName);
}
