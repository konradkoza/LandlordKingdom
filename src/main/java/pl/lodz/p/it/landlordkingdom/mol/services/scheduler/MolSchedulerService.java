package pl.lodz.p.it.landlordkingdom.mol.services.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.model.Local;
import pl.lodz.p.it.landlordkingdom.model.LocalState;
import pl.lodz.p.it.landlordkingdom.model.Rent;
import pl.lodz.p.it.landlordkingdom.mol.repositories.LocalRepository;
import pl.lodz.p.it.landlordkingdom.mol.repositories.RentRepository;
import pl.lodz.p.it.landlordkingdom.mol.services.FixedFeeService;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MolSchedulerService {
    private final RentRepository rentRepository;
    private final LocalRepository localRepository;
    private final FixedFeeService fixedFeeService;

    @PreAuthorize("permitAll()")
    public void changeLocalState() {
        List<Rent> rents = rentRepository.findAllByEndDateBeforeAndLocal_State(LocalDate.now(), LocalState.RENTED);

        for (Rent rent : rents) {
            Local local = rent.getLocal();
            local.setState(LocalState.INACTIVE);
            localRepository.saveAndFlush(local);
        }
    }

    @PreAuthorize("permitAll()")
    public void createFixedFee(){
        List<Rent> rents = rentRepository.findAllByEndDateGreaterThanEqual(LocalDate.now());

        for (Rent rent : rents) {
            fixedFeeService.createFixedFeeForEndOfBillingPeriod(rent.getId());
        }
    }
}
