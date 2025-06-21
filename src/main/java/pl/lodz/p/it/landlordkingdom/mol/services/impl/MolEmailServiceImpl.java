package pl.lodz.p.it.landlordkingdom.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.landlordkingdom.mol.services.MolEmailService;
import pl.lodz.p.it.landlordkingdom.services.HtmlEmailService;

import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class MolEmailServiceImpl implements MolEmailService {
    private final ResourceBundleMessageSource mailMessageSource;
    private final HtmlEmailService htmlEmailService;

    @Override
    public void sendRoleRequestAcceptedEmail(String to, String name, String lang){
        Map<String, Object> templateModel = Map.of(
                "name", name);
        String subject = mailMessageSource.getMessage("roleRequest.subjectAccepted", null, Locale.of(lang));

        htmlEmailService.createHtmlEmail(to, subject, "roleRequestAccepted", templateModel, lang);
    }

    @Override
    public void sendRoleRequestRejectedEmail(String to, String name, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name);
        String subject = mailMessageSource.getMessage("roleRequest.subjectRejected", null, Locale.of(lang));

        htmlEmailService.createHtmlEmail(to, subject, "roleRequestRejected", templateModel, lang);
    }

    @Override
    public void sendApplicationRejectedEmail(String to, String name, String localName, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "localName", localName);
        String subject = mailMessageSource.getMessage("localApplication.subjectRejected", null, Locale.of(lang));

        htmlEmailService.createHtmlEmail(to, subject, "localApplicationRejected", templateModel, lang);
    }

    @Override
    public void sendApplicationAcceptedEmail(String to, String name, String localName, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "localName", localName);
        String subject = mailMessageSource.getMessage("localApplication.subjectAccepted", null, Locale.of(lang));

        htmlEmailService.createHtmlEmail(to, subject, "localApplicationAccepted", templateModel, lang);
    }


}
