package com.jcms.web.agenda;

import com.jcms.domain.AgendaEntity;
import com.jcms.service.agenda.AgendaService;
import com.jcms.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping(value = "/agenda")
public class AgendaController extends BaseController {
    @Autowired
    private AgendaService agendaService;

    @RequestMapping(value = "/list")
    public String agendaList(){
        return "agendaList";
    }

    @RequestMapping(value = "/getAgendaListByUsername")
    @ResponseBody
    public List<AgendaEntity> getAgendaListByUsername(){
        return agendaService.getAgendaListByUsername();
    }
}
