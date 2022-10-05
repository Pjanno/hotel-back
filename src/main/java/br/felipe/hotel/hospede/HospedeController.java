package br.felipe.hotel.hospede;

import br.felipe.hotel.checkin.Checkin;
import br.felipe.hotel.hospede.vo.HospedeConsultaVo;
import br.felipe.hotel.hospede.vo.HospedeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v0/hospede")
public class HospedeController {

    @Autowired
    private HospedeService hospedeService;

    @GetMapping(path = "/listaPresentes")
    public List<HospedeConsultaVo> listarHospedesPresentesNoHotel() throws Exception {
        return hospedeService.listarHospedesPresentesNoHotel();
    }

    @GetMapping(path = "/listaAusentes")
    public List<HospedeConsultaVo> listarHospedesAusentesNoHotel() throws Exception {
        return hospedeService.listarHospedesNaoPresentesNoHotel();
    }

    @PostMapping(path = "/busca")
    public List<Hospede> buscaHospede(@RequestBody HospedeVo filtroBusca) throws Exception {
        return hospedeService.buscarHospede(filtroBusca);
    }

    @PostMapping(path = "/cadastrar")
    public ResponseEntity<?> cadastraHospede(@RequestBody HospedeVo dadosHospede) throws Exception {
        return new ResponseEntity<>(hospedeService.cadastrarHospede(dadosHospede), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deletar/{id}")
    public ResponseEntity<?> deletarHospede(@PathVariable Long id) throws Exception {
        hospedeService.deletarHospede(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/atualizar-checkin")
    public ResponseEntity<?> atualizarCheckinHospede(@RequestBody Hospede hospede, Checkin checkin) throws Exception {
        hospedeService.atualizarCheckinDoHospede(hospede, checkin);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
