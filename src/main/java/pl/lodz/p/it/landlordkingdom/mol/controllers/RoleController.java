package pl.lodz.p.it.landlordkingdom.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.model.RoleRequest;
import pl.lodz.p.it.landlordkingdom.mol.dto.RoleRequestPageResponse;
import pl.lodz.p.it.landlordkingdom.mol.mappers.RoleRequestMapper;
import pl.lodz.p.it.landlordkingdom.mol.services.RoleService;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<RoleRequestPageResponse> getRoleRequests(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoleRequest> requests = roleService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(RoleRequestMapper.toRoleRequestPageResponse(requests));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Retryable(retryFor = {RuntimeException.class})
    public ResponseEntity<Void> acceptRoleRequest(@PathVariable UUID id) {
        try {
            roleService.accept(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Retryable(retryFor = {RuntimeException.class})
    public ResponseEntity<Void> rejectRoleRequest(@PathVariable UUID id) {
        try {
            roleService.reject(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
