package pl.lodz.p.it.landlordkingdom.mol.services;

public interface MolEmailService {

    void sendRoleRequestAcceptedEmail(String to, String name,  String lang);

    void sendRoleRequestRejectedEmail(String to, String name,  String lang);

    void sendApplicationRejectedEmail(String to, String name, String localName, String lang);

    void sendApplicationAcceptedEmail(String to, String name, String localName, String lang);
}
