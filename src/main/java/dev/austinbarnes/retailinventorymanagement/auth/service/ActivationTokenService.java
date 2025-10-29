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

/**
 * ActivationTokenService is a service class that handles the creation and management of activation tokens.
 * It provides methods to create new activation tokens, send activation emails, and clear expired tokens.
 */
@Service
@RequiredArgsConstructor
@EnableJpaRepositories
@Slf4j
public class ActivationTokenService {
    private final ActivationTokenRepository activationTokenRepository;
    private final EmailService mailer;

    /**
     * Clears expired activation tokens from the database.
     * This method is scheduled to run every 30 minutes
     */
    @Transactional
    @Scheduled(cron = "0 */30 * * * *")
    public void clearExpiredActivationTokens() {
        List<ActivationToken> expiredTokens = activationTokenRepository.findAllByExpireAtBefore(Instant.now());
        activationTokenRepository.deleteAll(expiredTokens);
    }

    /**
     * Creates a new activation token for the specified user and sends an activation email.
     * This method is marked as REQUIRES_NEW to ensure that it runs in a separate transaction.
     *
     * @param userId    the ID of the user
     * @param userEmail the email address of the user
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void activateAndSendEmail(UUID userId, String userEmail) {
        log.info("Beginning create new activation token for user {}", userId);
        ActivationToken newActivationToken = new ActivationToken();
        newActivationToken.setUserId(userId);
        newActivationToken = activationTokenRepository.save(newActivationToken);
        log.info("Created new activation token {} for user {}", newActivationToken, userId);
        mailer.sendActivationEmail(userEmail, newActivationToken.getId());
    }

    /**
     * Creates a new ActivationToken for linking credential account to existing OAuth2 account
     *
     * @param userId the ID of the user
     * @param userEmail the email address of the user
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void linkAndSendEmail(UUID userId, String userEmail) {
        log.info("Beginning create new link token for user {}", userId);
        ActivationToken linkToken = new ActivationToken();
        linkToken.setUserId(userId);
        linkToken = activationTokenRepository.save(linkToken);
        log.info("Created link token {} for user {}", linkToken, userId);
        mailer.sendOAuthLinkEmail(userEmail, linkToken.getId());
    }
}
