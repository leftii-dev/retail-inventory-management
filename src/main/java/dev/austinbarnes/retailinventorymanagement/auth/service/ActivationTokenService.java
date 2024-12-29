package dev.austinbarnes.retailinventorymanagement.auth.service;


import dev.austinbarnes.retailinventorymanagement.auth.entity.ActivationToken;
import dev.austinbarnes.retailinventorymanagement.auth.repo.ActivationTokenRepository;
import dev.austinbarnes.retailinventorymanagement.common.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableJpaRepositories
@Slf4j
public class ActivationTokenService {
    private final ActivationTokenRepository activationTokenRepository;
    private final EmailService mailer;

    @Transactional
    @Scheduled(cron = "0 */30 * * * *")
    public void clearExpiredActivationTokens() {
        List<ActivationToken> expiredTokens = activationTokenRepository.findAllByExpireAtBefore(Instant.now());
        activationTokenRepository.deleteAll(expiredTokens);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ActivationToken activateAndSendEmail(UUID userId, String userEmail) {
        log.info("Beginning create new activation token for user {}", userId);
        ActivationToken newActivationToken = new ActivationToken();
        newActivationToken.setUserId(userId);
        newActivationToken = activationTokenRepository.save(newActivationToken);
        log.info("Created new activation token {} for user {}", newActivationToken, userId);
        mailer.sendActivationEmail(userEmail, newActivationToken.getId());
        return newActivationToken;
    }
}
