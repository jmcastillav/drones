package musala.soft.drones.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import musala.soft.drones.domain.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {

  Optional<MedicationEntity> findByCode(String code);

  List<MedicationEntity> findAllByCodeIn(Collection<String> codes);

  List<MedicationEntity> findAllByIdIn(Collection<Long> ids);
}
