package shreyash.io.Car_Management_Application.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shreyash.io.Car_Management_Application.models.Car;
import shreyash.io.Car_Management_Application.models.CarImage;
import shreyash.io.Car_Management_Application.repositories.CarImageRepository;
import shreyash.io.Car_Management_Application.repositories.CarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarImageService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private CarImageRepository carImageRepository;

    @Autowired
    private CarRepository carRepository;

    // Save multiple images for a car, with a maximum of 10 images
    public List<CarImage> saveCarImages(Long carId, List<MultipartFile> images) throws IOException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + carId));

        // Check current number of images for this car
        List<CarImage> existingImages = carImageRepository.findByCarId(carId);
        if (existingImages.size() + images.size() > 10) {
            throw new RuntimeException("Cannot upload more than 10 images for this car");
        }

        List<CarImage> carImages = new ArrayList<>();
        for (MultipartFile image : images) {
            String imagePath = saveImageToFileSystem(image);
            CarImage carImage = new CarImage();
            carImage.setCar(car);
            carImage.setImageUrl(imagePath);
            carImages.add(carImageRepository.save(carImage));
        }

        return carImages;
    }

    // Helper method to save an image to the file system
    private String saveImageToFileSystem(MultipartFile image) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        image.transferTo(filePath.toFile());

        return filePath.toString();
    }

    // Retrieve all images for a specific car
    public List<CarImage> getImagesByCarId(Long carId) {
        return carImageRepository.findByCarId(carId);
    }
}