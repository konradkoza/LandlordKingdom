package pl.lodz.p.it.landlordkingdom.mol.mappers;

import pl.lodz.p.it.landlordkingdom.mol.dto.AllLocalsReport;
import pl.lodz.p.it.landlordkingdom.mol.dto.AllLocalsReportResponse;
import pl.lodz.p.it.landlordkingdom.mol.dto.LocalReport;
import pl.lodz.p.it.landlordkingdom.mol.dto.LocalReportResponse;

public class ReportMapper {
    private ReportMapper() {
    }

    public static LocalReportResponse toLocalReportResponse(LocalReport localReport) {
        return new LocalReportResponse(
                localReport.local().getId(),
                localReport.local().getName(),
                PaymentMapper.paymentResponseList(localReport.payments()),
                VariableFeeMapper.variableFeeResponseList(localReport.variableFees()),
                FixedFeeMapper.fixedFeeResponseList(localReport.fixedFees()),
                localReport.rentCount(),
                localReport.longestRentDays(),
                localReport.shortestRentDays()
        );
    }

    public static AllLocalsReportResponse toAllLocalsReportResponse(AllLocalsReport report) {
        return new AllLocalsReportResponse(
                PaymentMapper.paymentResponseList(report.payments()),
                VariableFeeMapper.variableFeeResponseList(report.variableFees()),
                FixedFeeMapper.fixedFeeResponseList(report.fixedFees())
        );
    }
}
