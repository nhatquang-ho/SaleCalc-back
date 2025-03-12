package backend.SaleCalc_back.controller;


import backend.SaleCalc_back.dto.CommandDTO;
import backend.SaleCalc_back.dto.ProductDTO;
import backend.SaleCalc_back.dto.requestBody.ProductQuantity;
import backend.SaleCalc_back.model.Command;
import backend.SaleCalc_back.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/commands")
public class CommandController {
    @Autowired
    private CommandService commandService;

    @GetMapping("/")
    public ResponseEntity<List<CommandDTO>> getAllCommands() {
        return new ResponseEntity<>(commandService.getAllCommands(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandDTO> getCommand(@PathVariable Long id) {
        Optional<CommandDTO> commandDTO = commandService.getCommandById(id);
        return commandDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<Command> createCommand(@RequestBody Command command) {
        Command createdCommand = commandService.saveCommand(command);
        return new ResponseEntity<>(createdCommand, HttpStatus.CREATED);
    }

    @PostMapping("/{commandId}/addProducts/")
    public ResponseEntity<Boolean> addProductsToCommand(@PathVariable Long commandId, @RequestBody List<ProductQuantity> productQuantitys) {
        try {
            Boolean addStatus = commandService.addProductToCommand(commandId, productQuantitys);
            return new ResponseEntity<>(addStatus, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
