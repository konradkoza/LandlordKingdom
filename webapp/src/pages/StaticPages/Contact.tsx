import { useTranslation } from "react-i18next";

const ContanctPage: React.FC = () => {
  const { t } = useTranslation();

  return (
    <div>
      <h1 className="my-6 text-5xl font-semibold">{t("contactPage.title")}</h1>
      <p>{t("contactPage.description")}</p>

      <h2 className="my-2 text-3xl">{t("contactPage.emailTitle")}</h2>
      <p>
        <a href="mailto:kontakt@twojadomena.pl">
          {t("contactPage.emailAddress")}
        </a>
      </p>

      <h2 className="my-2 text-3xl">{t("contactPage.phoneTitle")}</h2>
      <p>
        <a href="tel:+48123456789">{t("contactPage.phoneNumber")}</a>
        <br />
        <small>{t("contactPage.phoneHours")}</small>
      </p>

      <h2 className="my-2 text-3xl">{t("contactPage.addressTitle")}</h2>
      <address>
        {t("contactPage.companyName")}
        <br />
        {t("contactPage.street")}
        <br />
        {t("contactPage.postalCode")}
      </address>
    </div>
  );
};

export default ContanctPage;
