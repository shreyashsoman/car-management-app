package shreyash.io.Car_Management_Application.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import shreyash.io.Car_Management_Application.models.Car;
import shreyash.io.Car_Management_Application.models.User;
import shreyash.io.Car_Management_Application.repositories.UserRepository;
import shreyash.io.Car_Management_Application.services.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private UserRepository userRepository;

    // Create a new car (requires authentication)
    @PostMapping
    public Car createCar(@RequestBody Car car, @AuthenticationPrincipal UserDetails userDetails) {
        // Retrieve the User object from the database
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        car.setOwner(user); // Associate the car with the authenticated user
        return carService.createCar(car);
    }

    // Get all cars (publicly viewable)
    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    // Get car by ID (publicly viewable)
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update an existing car (restricted to the car owner)
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return ResponseEntity.ok(carService.updateCar(id, carDetails, username));
    }

    // Delete a car (restricted to the car owner)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        carService.deleteCar(id, username);
        return ResponseEntity.noContent().build();
    }

    // Search for cars (publicly viewable)
    @GetMapping("/search")
    public ResponseEntity<List<Car>> searchCars(@RequestParam("keyword") String keyword) {
        List<Car> results = carService.searchCars(keyword);
        return ResponseEntity.ok(results);
    }
}