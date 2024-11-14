package shreyash.io.Car_Management_Application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shreyash.io.Car_Management_Application.models.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE " +
            "c.title LIKE %:keyword% OR c.description LIKE %:keyword% OR " +
            "c.carType LIKE %:keyword% OR c.company LIKE %:keyword% OR c.dealer LIKE %:keyword%")
    List<Car> searchCars(@Param("keyword") String keyword);
}
