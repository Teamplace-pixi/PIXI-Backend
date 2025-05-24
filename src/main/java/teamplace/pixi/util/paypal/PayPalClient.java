package teamplace.pixi.util.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class PayPalClient {
    private final APIContext apiContext;

    public PayPalClient(PayPalProperties properties) {
        this.apiContext = new APIContext(
                properties.getClientId(), properties.getClientSecret(), "sandbox"
        );
    }

    public Payment createPayment(Double total, String currency, String method,
                                 String intent, String description, String cancelUrl,
                                 String successUrl) {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = Collections.singletonList(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        try {
            return payment.create(apiContext);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
    }

    public Payment executePayment(String paymentId, String payerId) {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            return payment.execute(apiContext, paymentExecution);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
    }
}

