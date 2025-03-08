package backend.SaleCalc_back.controller;


import backend.SaleCalc_back.dto.CommandDTO;
import backend.SaleCalc_back.dto.ProductDTO;
import backend.SaleCalc_back.model.Command;
import backend.SaleCalc_back.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/commands")
public class CommandController {
    @Autowired
    private CommandService commandService;

    @PostMapping
    public ResponseEntity<Command> createCommand(@RequestBody Command command) {
        Command createdCommand = commandService.saveCommand(command);
        return new ResponseEntity<>(createdCommand, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandDTO> getCommand(@PathVariable Long id) {
        CommandDTO commandDTO = commandService.getCommandById(id);
        if (commandDTO != null) {
            return new ResponseEntity<>(commandDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{commandId}/products/{productId}")
    public ResponseEntity<Command> addProductToCommand(@PathVariable Long commandId, @PathVariable Long productId, @RequestParam int quantity) {
        Command updatedCommand = commandService.addProductToCommand(commandId, productId, quantity);
        return new ResponseEntity<>(updatedCommand, HttpStatus.OK);
    }
}
