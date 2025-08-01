package pl.lodz.p.it.landlordkingdom.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.landlordkingdom.exceptions.InvalidDataException;
import pl.lodz.p.it.landlordkingdom.messages.FilterMessages;
import pl.lodz.p.it.landlordkingdom.model.User;
import pl.lodz.p.it.landlordkingdom.model.filtering.builders.SpecificationBuilder;
import pl.lodz.p.it.landlordkingdom.model.filtering.builders.UserSpecificationBuilder;
import pl.lodz.p.it.landlordkingdom.mok.services.UserService;

import java.util.List;

@RequestMapping("/autocomplete")
@Scope("prototype")
@RestController
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class AutocompleteController {
    private final UserService userService;

    @GetMapping("/login")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<String>> getLoginAutocomplete(@RequestParam String query) {
        SpecificationBuilder<User> specificationBuilder = new UserSpecificationBuilder();
        specificationBuilder.with("login", "cn", query);
        try {
            List<User> result = userService.getAllFiltered(specificationBuilder.build());
            return ResponseEntity.ok(result.stream().map(User::getLogin).toList());
        } catch (InvalidDataException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FilterMessages.INVALID_DATA, e);
        }
    }
}
