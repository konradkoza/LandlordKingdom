package pl.lodz.p.it.landlordkingdom.mol.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.landlordkingdom.model.Payment;
import pl.lodz.p.it.landlordkingdom.mol.dto.PaymentResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.RentPaymentsResponse;

import java.util.List;

public class PaymentMapper {
    private PaymentMapper() {
    }

    public static PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getDate().toString(),
                payment.getAmount()
        );
    }

    public static RentPaymentsResponse toRentPaymentsResponse(Page<Payment> rentPayments) {
        return new RentPaymentsResponse(
                rentPayments.map(PaymentMapper::toPaymentResponse).getContent(),
                rentPayments.getTotalPages()
        );
    }
    public static List<PaymentResponse> paymentResponseList(List<Payment> paymentList) {
        return paymentList.stream().map(PaymentMapper::toPaymentResponse).toList();
    }
}
