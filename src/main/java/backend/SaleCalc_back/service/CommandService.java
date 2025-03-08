package backend.SaleCalc_back.service;

import backend.SaleCalc_back.dto.CommandDTO;
import backend.SaleCalc_back.dto.ProductDTO;
import backend.SaleCalc_back.model.Command;
import backend.SaleCalc_back.model.Product;
import backend.SaleCalc_back.model.ProductCommand;
import backend.SaleCalc_back.model.ProductCommandId;
import backend.SaleCalc_back.repository.CommandRepository;
import backend.SaleCalc_back.repository.ProductCommandRepository;
import backend.SaleCalc_back.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCommandRepository productCommandRepository;

    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    public CommandDTO getCommandById(Long id) {
        Command command = commandRepository.findById(id).orElse(null);
        CommandDTO commandDTO = null;
        if (command != null) {
            List<ProductDTO> products = command.getProducts().stream()
                    .map(productCommand -> new ProductDTO(
                            productCommand.getProduct().getId(),
                            productCommand.getProduct().getName(),
                            productCommand.getProduct().getDescription(),
                            productCommand.getProduct().getPrice(),
                            productCommand.getQuantity()
                    ))
                    .collect(Collectors.toList());

            commandDTO = new CommandDTO(
                    command.getId(),
                    command.getCreatedAt(),
                    command.getModifiedAt(),
                    command.getExpiredAt(),
                    command.getTotal(),
                    command.getClientPaid(),
                    command.getInChange(),
                    products
            );
        }
        return commandDTO;
    }

    public Command saveCommand(Command command) {
        return commandRepository.save(command);
    }

    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }

    public Command addProductToCommand(Long commandId, Long productId, int quantity) {
        Command command = commandRepository.findById(commandId).orElseThrow(() -> new RuntimeException("Command not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        // Create and set ProductCommandId
        ProductCommandId productCommandId = new ProductCommandId();
        productCommandId.setCommandId(commandId);
        productCommandId.setProductId(productId);

        // Create and set ProductCommand
        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(productCommandId);
        productCommand.setCommand(command);
        productCommand.setProduct(product);
        productCommand.setQuantity(quantity);

        productCommandRepository.save(productCommand);  // Save ProductCommand explicitly

        return commandRepository.save(command);

    }
}