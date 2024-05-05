package org.sid.ebankingbackend.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class StripeConfig {

    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @Value("${stripe.api.secretKey}")
    private String secretKey;

    @Bean
    public Stripe stripe() {
        Stripe.apiKey = secretKey;
        return new Stripe() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }
}
