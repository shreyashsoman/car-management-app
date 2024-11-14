package shreyash.io.Car_Management_Application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shreyash.io.Car_Management_Application.models.CarImage;

import java.util.List;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
    List<CarImage> findByCarId(Long carId);
}
