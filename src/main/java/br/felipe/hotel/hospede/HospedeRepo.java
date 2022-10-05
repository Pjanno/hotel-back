package br.felipe.hotel.hospede;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface HospedeRepo extends JpaRepository<Hospede,Long> {

    /**
     * Retorna lista de clientes ordenada pelo último checkin
     * @return Set<Hospede> de clientes
     */
    Optional<Set<Hospede>> findAllByOrderByCheckin_IdDesc();

    /**
     * Retorna clientes de acordo com os critérios dos filtros informados
     * @param String nome, String telefone, String documento
     * @return List<Hospede> com os resultados
     */
    Optional<List<Hospede>> findByNomeOrTelefoneOrDocumento(String nome, String telefone, String documento);
}
