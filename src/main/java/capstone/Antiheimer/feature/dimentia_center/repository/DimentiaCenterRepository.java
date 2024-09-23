package capstone.Antiheimer.feature.dimentia_center.repository;

import capstone.Antiheimer.feature.dimentia_center.entity.DimentiaCenter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimentiaCenterRepository extends JpaRepository<DimentiaCenter, Long> {
}
