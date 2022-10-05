package br.felipe.hotel.checkin;

import br.felipe.hotel.checkin.vo.CheckinVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v0/checkin")
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    @GetMapping(path = "/listar-checkins/{idHospede}")
    public List<CheckinVo> listarCheckinsPorHospede(@PathVariable Long idHospede) throws Exception {
        return checkinService.buscarCheckinsPorHospede(idHospede);
    }

    @GetMapping(path = "/buscar/{idCheckin}")
    public Checkin buscarCheckinPorId(@PathVariable Long idCheckin) throws Exception {
        return checkinService.buscarCheckinPorId(idCheckin);
    }

    @GetMapping(path = "/deletar/{idCheckin}")
    public ResponseEntity<?> deletarCheckinPorId(@PathVariable Long idCheckin) throws Exception {
        checkinService.cancelarCheckin(idCheckin);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/cadastrar")
    public Checkin cadastrarCheckin(@RequestBody CheckinVo dadosCheckin) throws Exception {
        return checkinService.cadastrarCheckin(dadosCheckin);
    }

}
