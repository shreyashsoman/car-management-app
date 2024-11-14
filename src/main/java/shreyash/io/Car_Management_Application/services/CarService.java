package shreyash.io.Car_Management_Application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shreyash.io.Car_Management_Application.models.Car;
import shreyash.io.Car_Management_Application.repositories.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Create a new car (associates with current user)
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    // Get all cars (viewable by all users)
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Get car by ID (viewable by all users)
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    // Update a car (restricted to the car owner)
    public Car updateCar(Long id, Car carDetails, String username) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + id));

        // Only allow update if the car belongs to the current user
        if (!car.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to update this car.");
        }

        // Update car details
        car.setTitle(carDetails.getTitle());
        car.setDescription(carDetails.getDescription());
        car.setCarType(carDetails.getCarType());
        car.setCompany(carDetails.getCompany());
        car.setDealer(carDetails.getDealer());

        return carRepository.save(car);
    }

    // Delete a car (restricted to the car owner)
    public void deleteCar(Long id, String username) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + id));

        // Only allow delete if the car belongs to the current user
        if (!car.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("You don't have permission to delete this car.");
        }

        carRepository.delete(car);
    }

    // Search cars (viewable by all users)
    public List<Car> searchCars(String keyword) {
        return carRepository.searchCars(keyword);
    }
}

