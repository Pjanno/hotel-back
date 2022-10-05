package br.felipe.hotel.checkin;

import br.felipe.hotel.checkin.vo.CheckinVo;
import br.felipe.hotel.enums.Erros;
import br.felipe.hotel.hospede.HospedeRepo;
import br.felipe.hotel.hospede.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepo checkinRepo;
    @Autowired
    private HospedeRepo hospedeRepo;

    public List<CheckinVo> buscarCheckinsPorHospede(Long idHospede) throws Exception {
        try {
            List<Checkin> checkinList = new ArrayList<>();
            checkinRepo.findAllByHospede_Id(idHospede).ifPresent(checkins -> {
                checkinList.addAll(checkins);
            });

            List<CheckinVo> checkinVoList = new ArrayList<>();
            checkinList.forEach(checkin -> {
                CheckinVo checkinVo = new CheckinVo(checkin);
                checkinVoList.add(checkinVo);
            });

            checkinVoList.forEach(vo ->{
                vo.setValorCheckin(this.valorAtualCheckin(vo));
            });

            return checkinVoList;
        } catch (Exception e){
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    public Checkin buscarCheckinPorId(Long idCheckin) throws Exception {
        try {
            Optional<Checkin> checkin = checkinRepo.findById(idCheckin);
            if (checkin.isPresent()){
                return checkin.get();
            } else{
                return null;
            }
        } catch (Exception e) {
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    public void cancelarCheckin(Long idCheckin) throws Exception {
        try {
            checkinRepo.findById(idCheckin).ifPresent(checkin -> {
                checkinRepo.delete(checkin);
            });
        } catch (Exception e){
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    public Checkin cadastrarCheckin(CheckinVo dadosCheckin) throws Exception {
        if (dadosCheckin.getHospede() == null) {
            throw new Exception(Erros.HOSPEDE_VAZIO.getDescricao());
        }

        Checkin novoCheckin = new Checkin(dadosCheckin.getHospede(), dadosCheckin.getDataEntrada(), dadosCheckin.getDataSaida(), dadosCheckin.getAdicionalVeiculo());

        try {
            List<Checkin> listaCheckinsVigentes = new ArrayList<>();
                checkinRepo.findAllByHospedeIdAndDataSaidaNull(dadosCheckin.getHospede().getId()).ifPresent(checkins -> {
                    listaCheckinsVigentes.addAll(checkins);
                });

            if (!listaCheckinsVigentes.isEmpty()) {
                throw new Exception(Erros.CHECKIN_VIGENTE_ENCONTRADO.getDescricao());
            }

            Checkin checkinCriado = checkinRepo.save(novoCheckin);

            try {
                List<Checkin> listaCheckinsDoHospede = new ArrayList<>();
                if (dadosCheckin.getHospede().getCheckin() == null) {
                    listaCheckinsDoHospede.add(checkinCriado);
                } else {
                    listaCheckinsDoHospede.addAll(dadosCheckin.getHospede().getCheckin());
                    listaCheckinsDoHospede.add(checkinCriado);
                }
                dadosCheckin.getHospede().setCheckin(listaCheckinsDoHospede);
                hospedeRepo.save(dadosCheckin.getHospede());
            } catch (Exception e){
                throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
            }

            return checkinCriado;
        } catch (Exception e) {
            throw new Exception(Erros.ERRO_500.getDescricao() + e.getMessage());
        }
    }

    /**
     * Retorna o valor atual do checkin
     * Obs: Caso a data de saída não esteja ainda presente, será considerado o momento atual da pesquisa.
     * @return BigDecimal com o Valor Calculado
     */
    private BigDecimal valorAtualCheckin(CheckinVo checkinVo) {
        BigDecimal valor = new BigDecimal(0);
        LocalDate entrada = checkinVo.getDataEntrada().toLocalDate();
        LocalDate saida = checkinVo.getDataSaida() != null ? checkinVo.getDataSaida().toLocalDate() : LocalDate.now();

        valor = valor.add(calculaValorTotalPeriodo(entrada, saida, valor, checkinVo.getAdicionalVeiculo()));

        valor = valor.add(verificaHorarioDiariaExtra(
                checkinVo.getDataSaida() != null ? checkinVo.getDataSaida() : LocalDateTime.now(),
                valor));

        return valor;
    }

    /**
     * Calcula o valor atual do período de permanência do cliente.
     * Considera a regra dos valores para finais de semana e também o adicional de veículos do cliente
     * @param entrada
     * @param saida
     * @param valorAtual
     */
    private BigDecimal calculaValorTotalPeriodo(LocalDate entrada, LocalDate saida, BigDecimal valorAtual, Boolean adicionalVeiculo) {
        while (entrada.isBefore(saida)){
            if (entrada.getDayOfWeek().equals(DayOfWeek.SATURDAY) || entrada.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                valorAtual = valorAtual.add(new BigDecimal(150));
                if (adicionalVeiculo) {
                    valorAtual = valorAtual.add(new BigDecimal(20));
                }
            } else {
                valorAtual = valorAtual.add(new BigDecimal(120));
                if (adicionalVeiculo) {
                    valorAtual = valorAtual.add(new BigDecimal(15));
                }
            }
            entrada = entrada.plusDays(1);
        }
        return valorAtual;
    }

    /**
     * Verifica se no dia da saída informado o horário padrão de checkout às 16:30 foi respeitado
     * senão, soma o adicional de uma diária extra (considerando também a regra dos finais de semana)
     * @param diaDaSaida dia da saída
     * @param valorTotalCheckin valor calculado caso se encontre após o horário de checkout
     */
    private BigDecimal verificaHorarioDiariaExtra(LocalDateTime diaDaSaida, BigDecimal valorTotalCheckin){
        if (diaDaSaida.getHour() >= 16 && diaDaSaida.getMinute() > 30) {
            if (diaDaSaida.getDayOfWeek().equals(DayOfWeek.SATURDAY) || diaDaSaida.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                valorTotalCheckin = valorTotalCheckin.add(new BigDecimal(150));
            } else {
                valorTotalCheckin = valorTotalCheckin.add(new BigDecimal(120));
            }
        }
        return valorTotalCheckin;
    }

}
