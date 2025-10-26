package Konkuk.U2E.domain.pin.repository;

import Konkuk.U2E.domain.pin.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    //문자열을 포함하는 모든 지역을 찾는 메서드
    @Query("SELECT r FROM Region r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :region, '%'))")
    List<Region> findRegionsByName(String region);
}
