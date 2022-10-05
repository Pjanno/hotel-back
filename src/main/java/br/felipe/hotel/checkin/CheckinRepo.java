package br.felipe.hotel.checkin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckinRepo extends JpaRepository<Checkin, Long> {

    Optional<List<Checkin>> findAllByHospede_Id(Long id);

    Optional<List<Checkin>> findAllByHospedeIdAndDataSaidaNull(Long id);
}
