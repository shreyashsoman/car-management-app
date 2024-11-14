package shreyash.io.Car_Management_Application.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shreyash.io.Car_Management_Application.models.Car;
import shreyash.io.Car_Management_Application.models.CarImage;
import shreyash.io.Car_Management_Application.repositories.CarImageRepository;
import shreyash.io.Car_Management_Application.repositories.CarRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarImageService {

    private final CarImageRepository carImageRepository;
    private final CarRepository carRepository;

    public CarImageService(CarImageRepository carImageRepository, CarRepository carRepository) {
        this.carImageRepository = carImageRepository;
        this.carRepository = carRepository;
    }

    // Save multiple images for a car, with a maximum of 10 images
    public List<CarImage> saveCarImages(Long carId, List<MultipartFile> images) throws IOException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + carId));

        // Check if adding these images would exceed the 10-image limit
        List<CarImage> existingImages = carImageRepository.findByCarId(carId);
        if (existingImages.size() + images.size() > 10) {
            throw new RuntimeException("Cannot upload more than 10 images for this car");
        }

        List<CarImage> carImages = new ArrayList<>();
        for (MultipartFile image : images) {
            // Convert image to Base64
            String base64Image = Base64.encodeBase64String(image.getBytes());

            // Create and save CarImage entity with the Base64 data
            CarImage carImage = new CarImage();
            carImage.setCar(car);
            carImage.setImageData(base64Image); // Ensure CarImage has @Lob annotation on imageData
            carImages.add(carImageRepository.save(carImage));
        }

        return carImages;
    }

    // Retrieve all images for a specific car
    public List<CarImage> getImagesByCarId(Long carId) {
        return carImageRepository.findByCarId(carId);
    }
}
