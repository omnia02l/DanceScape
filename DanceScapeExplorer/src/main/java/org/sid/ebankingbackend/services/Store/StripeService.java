package org.sid.ebankingbackend.services.Store;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.api.secretKey}")
    private String secretKey;
    public String createToken() throws StripeException {
        Stripe.apiKey = secretKey;

        TokenCreateParams params = TokenCreateParams.builder()
                .setCard(
                        TokenCreateParams.Card.builder()
                                .setNumber("4242424242424242")
                                .setExpMonth("12")  // Changed to String
                                .setExpYear("2024") // Changed to String
                                .setCvc("123")
                                .build()
                )
                .build();

        Token token = Token.create(params);
        return token.getId();
    }

    public void chargeCreditCard(BigDecimal amount, String email, String token) throws StripeException {
        Stripe.apiKey = secretKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", "usd");
        params.put("description", "Payment for order");
        params.put("source", token);
        params.put("receipt_email", email);

        Charge.create(params);
    }


}