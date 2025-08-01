package pl.lodz.p.it.landlordkingdom.mok.services.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.exceptions.TokenGenerationException;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.NEVER)
public class AccountScheduler {
    private final SchedulerService userService;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void removeExpiredAccounts() {
        log.info("Removing expired accounts");
        userService.deleteNonVerifiedUsers();
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    public void resendVerificationEmails() {
        log.info("Resending verification emails");
        userService.sendEmailVerifyAccount();
    }

    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void deactivateInactiveUsers() {
        try {
            log.info("Deactivating inactive users");
            userService.checkForInactiveUsers();
        } catch (TokenGenerationException e) {
            log.error("Error while deactivating inactive users", e);
        }
    }
}