package com.cjstore.api.controller;

import com.cjstore.api.service.RazorpayService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${app.razorpay.key-id}")
    private String keyId;

    private final RazorpayService razorpayService;

    public PaymentController(RazorpayService razorpayService) {
        this.razorpayService = razorpayService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
        try {
            double amount = Double.parseDouble(data.get("amount").toString());
            String currency = data.getOrDefault("currency", "INR").toString();
            String receipt = data.getOrDefault("receipt", "rcpt_" + System.currentTimeMillis()).toString();

            String orderId = razorpayService.createOrder(amount, currency, receipt);
            return ResponseEntity.ok(Map.of(
                    "orderId", orderId,
                    "amount", amount * 100,
                    "currency", currency,
                    "key", keyId));
        } catch (RazorpayException | NumberFormatException e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> data) {
        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        boolean isValid = razorpayService.verifySignature(orderId, paymentId, signature);
        if (isValid) {
            return ResponseEntity.ok(Map.of("status", "success"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("status", "failure"));
        }
    }
}
