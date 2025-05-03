package teamplace.pixi.Device.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import teamplace.pixi.Device.domain.Device;

import java.util.List;


public interface DeviceRepository extends JpaRepository<Device, Long> {
    @Query("SELECT d FROM Device d WHERE d.deviceName LIKE %:name%")
    List<Device> findByNameLike(@Param("name") String name);
}
