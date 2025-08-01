package pl.lodz.p.it.landlordkingdom.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.landlordkingdom.exceptions.CreationException;
import pl.lodz.p.it.landlordkingdom.exceptions.InvalidLocalState;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.WrongEndDateException;
import pl.lodz.p.it.landlordkingdom.model.Application;
import pl.lodz.p.it.landlordkingdom.model.Rent;
import pl.lodz.p.it.landlordkingdom.mol.dto.AcceptApplicationRequest;
import pl.lodz.p.it.landlordkingdom.mol.dto.OwnApplicationResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.RentForOwnerResponse;
import pl.lodz.p.it.landlordkingdom.mol.mappers.ApplicationMapper;
import pl.lodz.p.it.landlordkingdom.mol.mappers.RentMapper;
import pl.lodz.p.it.landlordkingdom.mol.services.ApplicationService;

import java.time.LocalDate;
import java.util.UUID;


@RestController
@RequestMapping("/locals")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping("/{id}/applications/me")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<OwnApplicationResponse> getApplicationForLocal(@PathVariable UUID id) {
        try {
            UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
            Application application = applicationService.getUserApplication(userId, id);
            return ResponseEntity.ok(ApplicationMapper.toOwnApplicationResponse(application));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/{id}/applications")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<OwnApplicationResponse> addApplicationForLocal(@PathVariable UUID id) {
        try {
            UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
            Application application = applicationService.createApplication(id, userId);
            return ResponseEntity.ok(ApplicationMapper.toOwnApplicationResponse(application));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidLocalState | CreationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }

    }

    @DeleteMapping("/{id}/applications")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<Void> deleteApplicationForLocal(@PathVariable UUID id) {
        try {
            UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
            applicationService.removeApplication(id, userId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/applications/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<RentForOwnerResponse> acceptApplication(@PathVariable UUID id, @RequestBody AcceptApplicationRequest request) {
        try {
            UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
            Rent rent = applicationService.acceptApplication(id, userId, LocalDate.parse(request.endDate()));
            return ResponseEntity.status(HttpStatus.CREATED).body(RentMapper.rentForOwnerResponse(rent));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidLocalState e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (WrongEndDateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/applications/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<Void> rejectApplication(@PathVariable UUID id) {
        try {
            UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
            applicationService.rejectApplication(id, userId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
