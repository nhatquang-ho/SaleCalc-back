package backend.SaleCalc_back.service;

import backend.SaleCalc_back.dto.CommandDTO;
import backend.SaleCalc_back.dto.ImageDTO;
import backend.SaleCalc_back.dto.ProductDTO;
import backend.SaleCalc_back.model.Image;
import backend.SaleCalc_back.model.Product;
import backend.SaleCalc_back.repository.ImageRepository;
import backend.SaleCalc_back.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> {
                    Image image = product.getImage();
                    return new ProductDTO(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getStock(),
                            product.getSold(),
                            0,
                            image != null ? new ImageDTO(
                                    image.getId(),
                                    image.getTitle(),
                                    image.getDescription(),
                                    image.getData()
                            ) : null
                    );
                })
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(product -> {
            Image image = product.getImage();
            return new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    product.getSold(),
                    0,
                    image != null ? new ImageDTO(
                            image.getId(),
                            image.getTitle(),
                            image.getDescription(),
                            image.getData()
                    ) : null
            );
        });
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public boolean addImageToProduct(Long productId, Long imageId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Optional<Image> image = imageRepository.findById(imageId);

        if (image.isPresent()) {
            if (image.get() != product.getImage()) {
                product.setImage(image.get());
                productRepository.save(product);
            }
            return true;
        }
        return false;
    }
}
