package com.jcms.service.agenda;

import com.jcms.domain.AgendaEntity;
import com.jcms.domain.JcmsAgendaEntity;
import com.jcms.repository.agenda.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    /**
     * 根据当前登录人获取到当前登录人的日程
     *
     * @return
     */
    public List<AgendaEntity> getAgendaListByUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AgendaEntity> list = new ArrayList<AgendaEntity>();
        List<JcmsAgendaEntity> entities = agendaRepository.findAllByUserName(userDetails.getUsername());
        if (null != entities && entities.size() > 0) {
            for (JcmsAgendaEntity entity : entities) {
                AgendaEntity agendaEntity = new AgendaEntity();
                agendaEntity.setId(entity.getAgendaKey().intValue());
                agendaEntity.setTitle(entity.getAgendaTitle());
                agendaEntity.setAllDay(entity.getAgendaAllDay() == 0 ? false : true);
                agendaEntity.setStart(entity.getAgendaStart());
                agendaEntity.setEnd(entity.getAgendaEnd());
                agendaEntity.setUrl(entity.getAgendaUrl());
                agendaEntity.setClassName(entity.getAgendaClassName());
                agendaEntity.setEditable(entity.getAgendaEditable() == 0 ? false : true);
                agendaEntity.setSource(entity.getAgendaSource());
                agendaEntity.setColor(entity.getAgendaColor());
                agendaEntity.setBackgroundColor(entity.getAgendaBackgroundColor());
                agendaEntity.setBorderColor(entity.getAgendaBorderColor());
                agendaEntity.setTextColor(entity.getAgendaTextColor());
                list.add(agendaEntity);
            }
        }
        return list;
    }
}
