package br.felipe.hotel.hospede;

import br.felipe.hotel.checkin.Checkin;
import br.felipe.hotel.checkin.CheckinService;
import br.felipe.hotel.checkin.vo.CheckinVo;
import br.felipe.hotel.enums.Erros;
import br.felipe.hotel.hospede.vo.HospedeConsultaVo;
import br.felipe.hotel.hospede.vo.HospedeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepo hospedeRepo;
    @Autowired
    private CheckinService checkinService;

    public List<HospedeConsultaVo> listarHospedesPresentesNoHotel() throws Exception {
        try {
            Set<Hospede> hospedesPresentes = new HashSet<>();
            hospedeRepo.findAllByOrderByCheckin_IdDesc().ifPresent(hospedes -> {
                hospedesPresentes.addAll(hospedes);
            });

            hospedesPresentes.removeIf(hospede -> hospede.getCheckin().get(hospede.getCheckin().size() - 1).getDataSaida() != null);

            // Calcula o valor atual pra por no Vo
            List<HospedeConsultaVo> listaHospedesPresentes = new ArrayList<>();

            hospedesPresentes.forEach(hospede -> {
                HospedeConsultaVo hospedeConsultaVo = new HospedeConsultaVo();
                try {
                    BigDecimal valorTotalGasto = new BigDecimal(0);
                    List<CheckinVo> checkinVoList = checkinService.buscarCheckinsPorHospede(hospede.getId());
                    for (int i=0; i < checkinVoList.size(); i++){
                        valorTotalGasto = valorTotalGasto.add(checkinVoList.get(i).getValorCheckin());
                    }
                    hospedeConsultaVo.setValorTotalGastoHotel(valorTotalGasto);
                    hospedeConsultaVo.setValorUltimaHospedagem(checkinVoList.get(checkinVoList.size() - 1).getValorCheckin());
                    hospedeConsultaVo.setDocumento(hospede.getDocumento());
                    hospedeConsultaVo.setNome(hospede.getNome());
                    hospedeConsultaVo.setTelefone(hospede.getTelefone());

                    listaHospedesPresentes.add(hospedeConsultaVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            return listaHospedesPresentes;

        } catch (Exception e){
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    public List<HospedeConsultaVo> listarHospedesNaoPresentesNoHotel() throws Exception {
        try {
            Set<Hospede> listaDeHospedes = new HashSet<>();
            hospedeRepo.findAllByOrderByCheckin_IdDesc().ifPresent(hospedes -> {
                listaDeHospedes.addAll(hospedes);
            });

            listaDeHospedes.removeIf(hospede -> hospede.getCheckin() == null);
            listaDeHospedes.removeIf(hospede -> hospede.getCheckin().get(hospede.getCheckin().size() - 1).getDataSaida() == null);

            // Calcula o valor atual pra por no Vo
            List<HospedeConsultaVo> listaHospedesAusentes = new ArrayList<>();

            listaDeHospedes.forEach(hospede -> {
                HospedeConsultaVo hospedeConsultaVo = new HospedeConsultaVo();
                try {
                    BigDecimal valorTotalGasto = new BigDecimal(0);
                    List<CheckinVo> checkinVoList = checkinService.buscarCheckinsPorHospede(hospede.getId());
                    for (int i=0; i < checkinVoList.size(); i++){
                        valorTotalGasto = valorTotalGasto.add(checkinVoList.get(i).getValorCheckin());
                    }
                    hospedeConsultaVo.setValorTotalGastoHotel(valorTotalGasto);
                    hospedeConsultaVo.setValorUltimaHospedagem(checkinVoList.get(checkinVoList.size() - 1).getValorCheckin());
                    hospedeConsultaVo.setDocumento(hospede.getDocumento());
                    hospedeConsultaVo.setNome(hospede.getNome());
                    hospedeConsultaVo.setTelefone(hospede.getTelefone());

                    listaHospedesAusentes.add(hospedeConsultaVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            return listaHospedesAusentes;

        } catch (Exception e) {
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    public List<Hospede> buscarHospede(HospedeVo filtro) throws Exception {
        try {
            List<Hospede> hospedes = new ArrayList<>();
            hospedeRepo.findByNomeOrTelefoneOrDocumento(filtro.getNome(), filtro.getTelefone(), filtro.getDocumento()).ifPresent(hospedesEncontrados -> {
                hospedes.addAll(hospedesEncontrados);
            });
            return hospedes;
        } catch (Exception e){
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    /**
     * Cadastra novos clientes com os dados informados na tela
     * Realiza a verificação da existência dos dados antes do cadastro.
     * @param dadosHospede - Dados informados na tela para cadastro
     * @return {@link Hospede} cadastrado.
     * @throws Exception
     */
    public Hospede cadastrarHospede(HospedeVo dadosHospede) throws Exception {
        Hospede hospedeNovo = new Hospede(dadosHospede.getNome(), dadosHospede.getTelefone(), dadosHospede.getDocumento(), Boolean.TRUE);

        try {
            List<Hospede> listaHospede = new ArrayList<>();
            hospedeRepo.findByNomeOrTelefoneOrDocumento(hospedeNovo.getNome(), hospedeNovo.getTelefone(), hospedeNovo.getDocumento()).ifPresent(hospedes -> {
                listaHospede.addAll(hospedes);
            });
            if (!listaHospede.isEmpty()) {
                throw new Exception(Erros.DADOS_INFORMADOS_EXISTENTES.getDescricao());
            } else {
                return hospedeRepo.save(hospedeNovo);
            }
        } catch (Exception e) {
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    public void deletarHospede(Long idHospede) throws Exception {
        try {
            Hospede hospede = hospedeRepo.findById(idHospede).orElseThrow(Exception::new);
            hospede.setAtivo(Boolean.FALSE);
            hospedeRepo.save(hospede);
        } catch (Exception e){
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    public void atualizarCheckinDoHospede(Hospede hospede, Checkin checkinCriado) throws Exception {
        try {
            List<Checkin> listaCheckinsDoHospede = new ArrayList<>();
            if (hospede.getCheckin() == null) {
                listaCheckinsDoHospede.add(checkinCriado);
            } else {
                listaCheckinsDoHospede.addAll(hospede.getCheckin());
                listaCheckinsDoHospede.add(checkinCriado);
            }
            hospede.setCheckin(listaCheckinsDoHospede);
            hospedeRepo.save(hospede);
        } catch (Exception e){
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }

    }
}
