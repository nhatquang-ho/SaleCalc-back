package backend.SaleCalc_back.service;

import backend.SaleCalc_back.model.Image;
import backend.SaleCalc_back.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public Image saveImage(String title, String description, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setTitle(title);
        image.setDescription(description);
        image.setData(file.getBytes());

        return imageRepository.save(image);
    }

    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }
}
