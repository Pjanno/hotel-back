package br.felipe.hotel;

import br.felipe.hotel.checkin.CheckinService;
import br.felipe.hotel.checkin.vo.CheckinVo;
import br.felipe.hotel.hospede.Hospede;
import br.felipe.hotel.hospede.HospedeService;
import br.felipe.hotel.hospede.vo.HospedeVo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelApplicationTests {

	@Autowired
	CheckinService checkinService;
	@Autowired
	HospedeService hospedeService;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	void criaUsuarioDummy() throws Exception {
		HospedeVo dadosHospede = new HospedeVo();
		dadosHospede.setNome("João");
		dadosHospede.setDocumento("12345678900");
		dadosHospede.setTelefone("+5521999999999");

		Hospede hospede = hospedeService.cadastrarHospede(dadosHospede);

		CheckinVo dadosCheckin = new CheckinVo();
		dadosCheckin.setHospedes(hospede);
		dadosCheckin.setAdicionalVeiculo(Boolean.TRUE);
		dadosCheckin.setDataEntrada(LocalDateTime.now());
		dadosCheckin.setDataSaida(LocalDateTime.now().plusDays(7));
		checkinService.cadastrarCheckin(dadosCheckin);
	}

	@Test
	void cadastrarHospedeNaoExistente() throws Exception {
		HospedeVo dadosHospede = new HospedeVo();
		dadosHospede.setNome("Maria");
		dadosHospede.setDocumento("98765432100");
		dadosHospede.setTelefone("+5521888888888");
		assertEquals(hospedeService.cadastrarHospede(dadosHospede).getClass(), Hospede.class);
	}

	@Test
	void buscarHospedeExistentePorTelefone() throws Exception {
		HospedeVo filtro = new HospedeVo();
		filtro.setTelefone("+5521999999999");
		Hospede hospede = hospedeService.buscarHospede(filtro).get(0);
		assertEquals(hospede.getClass(), Hospede.class);
	}

	@Test
	void buscarHospedeExistentePorDocumento() throws Exception {
		HospedeVo filtro = new HospedeVo();
		filtro.setDocumento("12345678900");
		Hospede hospede = hospedeService.buscarHospede(filtro).get(0);
		assertEquals(hospede.getClass(), Hospede.class);
	}

	@Test
	void buscarHospedeExistentePorNome() throws Exception {
		HospedeVo filtro = new HospedeVo();
		filtro.setNome("João");
		Hospede hospede = hospedeService.buscarHospede(filtro).get(0);
		assertEquals(hospede.getClass(), Hospede.class);
	}



}
