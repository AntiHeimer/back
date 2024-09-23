package capstone.Antiheimer.feature.dementia_center.repository;

import capstone.Antiheimer.feature.dementia_center.entity.DementiaCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DementiaCenterRepository extends JpaRepository<DementiaCenter, Long> {
}
