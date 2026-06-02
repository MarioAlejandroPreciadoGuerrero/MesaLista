/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import Interface.IPasarelaPago;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.Refund;
import com.paypal.payments.RefundRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class PayPalAdapter implements IPasarelaPago {

    private final String CLIENT_ID = "AVEMtjYljZG27Ooang-EFzbJ4FsZNz0EPBSlXDzxL2gLQOLF0QIz473w5WGrQcwjbGO9Dh6cfGA5PadE";
    private final String SECRET = "EEqXOcmLcczfDfie3wRWCIe0juDqeZwbFf-tlIxxkOiNzUn7df37kPbwf-RxSFAkk13liSD2PQdQAIsl";
    private final PayPalEnvironment environment;
    private final PayPalHttpClient client;

    public PayPalAdapter() {
        this.environment = new PayPalEnvironment.Sandbox(CLIENT_ID, SECRET);
        this.client = new PayPalHttpClient(environment);
    }

    @Override
    public String procesarCobro(Double monto, String concepto) {
        if (monto == null || monto <= 0) {
            return null;
        }
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.checkoutPaymentIntent("CAPTURE");
            List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
            purchaseUnits.add(new PurchaseUnitRequest().amountWithBreakdown(new AmountWithBreakdown().currencyCode("MXN").value(String.valueOf(monto))).description(concepto));
            orderRequest.purchaseUnits(purchaseUnits);
            OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);
            HttpResponse<Order> response = client.execute(request);
            if (response.statusCode() == 201) {
                return response.result().id(); 
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public boolean procesarReembolso(String transaccionId) {
        if (transaccionId == null || transaccionId.isEmpty()) {
            return false;
        }
        try {
            CapturesRefundRequest request = new CapturesRefundRequest(transaccionId);
            request.requestBody(new RefundRequest());
            HttpResponse<Refund> response = client.execute(request);
            if (response.statusCode() == 201) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
