package pl.lodz.p.it.landlordkingdom.mol.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.landlordkingdom.exceptions.*;
import pl.lodz.p.it.landlordkingdom.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.landlordkingdom.messages.RentExceptionMessages;
import pl.lodz.p.it.landlordkingdom.model.Local;
import pl.lodz.p.it.landlordkingdom.model.Payment;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.landlordkingdom.exceptions.NotFoundException;
import pl.lodz.p.it.landlordkingdom.exceptions.WrongEndDateException;
import pl.lodz.p.it.landlordkingdom.exceptions.InvalidLocalState;
import pl.lodz.p.it.landlordkingdom.mol.dto.*;
import pl.lodz.p.it.landlordkingdom.mol.mappers.*;
import pl.lodz.p.it.landlordkingdom.mol.services.*;
import pl.lodz.p.it.landlordkingdom.util.Signer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class MeOwnerController {
    private final LocalService localService;
    private final ApplicationService applicationService;
    private final RentService rentService;
    private final PaymentService paymentService;
    private final Signer signer;


    @GetMapping("/locals")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<GetOwnLocalsPage> getOwnLocals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "ALL") String state
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID id = UUID.fromString(jwt.getSubject());
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(LocalMapper.toGetOwnLocalsResponseList(localService.getOwnLocals(id, pageable, state)));
    }

    @GetMapping("locals/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnLocalDetailsResponse> getLocal(@PathVariable UUID id) {
        UUID ownerId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        try {
            Local local = localService.getOwnLocal(id, ownerId);
            return ResponseEntity
                    .ok()
                    .eTag(signer.generateSignature(local.getId(), local.getVersion()))
                    .body(LocalMapper.toOwnLocalDetailsResponse(local));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/locals/{id}/applications")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<List<GetOwnLocalApplicationsResponse>> getOwnLocalApplications(@PathVariable UUID id) {
        UUID ownerId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        return ResponseEntity.ok(ApplicationMapper.getOwnLocalApplicationsResponses(applicationService.getLocalApplications(id, ownerId)));
    }

    @GetMapping("/locals/{id}/reports")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<LocalReportResponse> getReportData(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping("/locals/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<EditLocalResponse> editLocal(@PathVariable UUID id, @RequestHeader(HttpHeaders.IF_MATCH) String tagValue, @RequestBody @Valid EditLocalRequest editLocalRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(jwt.getSubject());
        try {
            return ResponseEntity.ok(LocalMapper.toEditLocalResponse(localService.editLocal(userId, id, editLocalRequest, tagValue)));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ApplicationOptimisticLockException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        }
    }

    @GetMapping("/rents/current")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<RentForOwnerResponsePage> getCurrentRents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        UUID ownerId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(RentMapper.rentForOwnerResponsePage(rentService.getCurrentOwnerRents(ownerId, pageable)));
    }

    @GetMapping("/owner/rents/archival")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<RentForOwnerResponsePage> getArchivalRents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(RentMapper.rentForOwnerResponsePage(rentService.getArchivalOwnerRents(userId, pageable)));
    }

    @PostMapping("/rents/{id}/payment")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    @Retryable(retryFor = {RuntimeException.class})
    public ResponseEntity<PaymentResponse> payRent(@PathVariable UUID id, @RequestBody @Valid NewPaymentRequest newPaymentRequest) throws NotFoundException {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            Payment payment = paymentService.create(userId, id, newPaymentRequest.amount());
            return ResponseEntity.ok(PaymentMapper.toPaymentResponse(payment));
        } catch (PaymentAlreadyExistsException | RentAlreadyEndedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PatchMapping("/locals/{id}/leave")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<LeaveLocalResponse> leaveLocal(@PathVariable UUID id) {
        try {
            UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
            localService.leaveLocal(userId, id);
            return ResponseEntity.ok(new LeaveLocalResponse(true));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidLocalState e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PatchMapping("/locals/{id}/fixed-fee")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<GetOwnLocalsResponse> setFixedFee(@PathVariable UUID id, @RequestBody @Valid SetFixedFeeRequest setFixedFeeRequest, @RequestHeader(HttpHeaders.IF_MATCH) String tagValue) throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID ownerId = UUID.fromString(jwt.getSubject());
        try {
            Local local = localService.setFixedFee(id, ownerId, setFixedFeeRequest.marginFee(), setFixedFeeRequest.rentalFee(), tagValue);
            return ResponseEntity.ok(LocalMapper.toGetOwnLocalsResponse(local));
        } catch (ApplicationOptimisticLockException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        }
    }

    @PatchMapping("/rents/{id}/end-date")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<RentForOwnerResponse> editEndDate(@PathVariable UUID id, @RequestBody @Valid SetEndDateRequest setEndDateRequest) {
        try {
            UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
            LocalDate newDate = LocalDate.parse(setEndDateRequest.newEndDate());
            return ResponseEntity.ok(RentMapper.rentForOwnerResponse(rentService.editEndDate(id, userId, newDate)));
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RentExceptionMessages.WRONG_DATE_FORMAT, new WrongEndDateException(RentExceptionMessages.WRONG_DATE_FORMAT, ErrorCodes.WRONG_END_DATE));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (WrongEndDateException | RentAlreadyEndedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/locals/reports")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<LocalReportResponse>> getAllReports() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/owner/rents/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMINISTRATOR')")
    public ResponseEntity<RentForOwnerResponse> getOwnerRent(@PathVariable UUID id) {
        try {
            UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
            return ResponseEntity.ok(RentMapper.rentForOwnerResponse(rentService.getOwnerRent(userId, id)));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
