package backend.SaleCalc_back.service;

import backend.SaleCalc_back.dto.CommandDTO;
import backend.SaleCalc_back.dto.ImageDTO;
import backend.SaleCalc_back.dto.ProductDTO;
import backend.SaleCalc_back.dto.requestBody.ProductQuantity;
import backend.SaleCalc_back.model.*;
import backend.SaleCalc_back.repository.CommandRepository;
import backend.SaleCalc_back.repository.ImageRepository;
import backend.SaleCalc_back.repository.ProductCommandRepository;
import backend.SaleCalc_back.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCommandRepository productCommandRepository;

    @Autowired
    private ImageRepository imageRepository;

    public List<CommandDTO> getAllCommands() {
        List<Command> commands = commandRepository.findAll();
        return commands.stream()
                .map(command -> {
                    List<ProductDTO> products = command.getProducts().stream()
                            .map(productCommand -> {
                                Image image = productCommand.getProduct().getImage();
                                return new ProductDTO(
                                        productCommand.getProduct().getId(),
                                        productCommand.getProduct().getName(),
                                        productCommand.getProduct().getDescription(),
                                        productCommand.getProduct().getPrice(),
                                        productCommand.getProduct().getStock(),
                                        productCommand.getProduct().getSold(),
                                        productCommand.getQuantity(),
                                        image != null ? new ImageDTO(
                                                image.getId(),
                                                image.getTitle(),
                                                image.getDescription(),
                                                image.getData()
                                        ) : null
                                );
                            })
                            .collect(Collectors.toList());
                    return new CommandDTO(
                            command.getId(),
                            command.getTotal(),
                            command.getClientPaid(),
                            command.getInChange(),
                            products
                    );
                })
                .collect(Collectors.toList());
    }

    public Optional<CommandDTO> getCommandById(Long id) {
        return commandRepository.findById(id).map(command -> {
            List<ProductDTO> products = command.getProducts().stream()
                    .map(productCommand -> {
                        Image image = productCommand.getProduct().getImage();
                        return new ProductDTO(
                                productCommand.getProduct().getId(),
                                productCommand.getProduct().getName(),
                                productCommand.getProduct().getDescription(),
                                productCommand.getProduct().getPrice(),
                                productCommand.getProduct().getStock(),
                                productCommand.getProduct().getSold(),
                                productCommand.getQuantity(),
                                image != null ? new ImageDTO(
                                        image.getId(),
                                        image.getTitle(),
                                        image.getDescription(),
                                        image.getData()
                                ) : null
                        );
                    })
                    .collect(Collectors.toList());

            return new CommandDTO(
                    command.getId(),
                    command.getTotal(),
                    command.getClientPaid(),
                    command.getInChange(),
                    products
            );
        });
    }

    public Command saveCommand(Command command) {
        return commandRepository.save(command);
    }

    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }

    public Boolean addProductToCommand(Long commandId, List<ProductQuantity> productQuantities) {
        Command command = commandRepository.findById(commandId).orElseThrow(() -> new RuntimeException("Command not found"));

        productQuantities.forEach(productQuantity -> {
            Product product = productRepository.findById(productQuantity.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            // Create and set ProductCommandId
            ProductCommandId productCommandId = new ProductCommandId();
            productCommandId.setCommandId(commandId);
            productCommandId.setProductId(productQuantity.getProductId());

            // Create and set ProductCommand
            ProductCommand productCommand = new ProductCommand();
            productCommand.setId(productCommandId);
            productCommand.setCommand(command);
            productCommand.setProduct(product);
            productCommand.setQuantity(productQuantity.getQuantity());

            productCommandRepository.save(productCommand);
            commandRepository.save(command);
        });

        return true;
    }
}