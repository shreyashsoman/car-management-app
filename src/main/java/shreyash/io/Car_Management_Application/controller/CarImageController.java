package shreyash.io.Car_Management_Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shreyash.io.Car_Management_Application.models.CarImage;
import shreyash.io.Car_Management_Application.services.CarImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarImageController {

    @Autowired
    private CarImageService carImageService;

    // Upload multiple images for a car, with a maximum limit of 10
    @PostMapping("/{carId}/images")
    public ResponseEntity<?> uploadCarImages(@PathVariable Long carId, @RequestParam("images") List<MultipartFile> images) {
        try {
            List<CarImage> savedImages = carImageService.saveCarImages(carId, images);
            return new ResponseEntity<>(savedImages, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Error uploading images", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get all images for a specific car
    @GetMapping("/{carId}/images")
    public ResponseEntity<List<CarImage>> getCarImages(@PathVariable Long carId) {
        List<CarImage> images = carImageService.getImagesByCarId(carId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}