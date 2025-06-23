import CookiesDialog from "@/components/CookiesDialog";
import React from "react";
import { useTranslation } from "react-i18next";

const CookiesPage: React.FC = () => {
  const [isOpen, setIsOpen] = React.useState(false);
  const { t } = useTranslation();

  return (
    <>
      <div>
        <h1 className="my-6 text-5xl font-semibold">
          {t("cookiesPage.title")}
        </h1>
        <h2 className="my-2 text-3xl">
          {t("cookiesPage.whatAreCookies.title")}
        </h2>
        <p>{t("cookiesPage.whatAreCookies.description")}</p>
        <h2 className="my-2 text-3xl">
          {t("cookiesPage.typesOfCookies.title")}
        </h2>
        <p>{t("cookiesPage.typesOfCookies.description")}</p>
        <ul>
          <li>
            <strong>{t("cookiesPage.typesOfCookies.necessary.title")}</strong> -{" "}
            {t("cookiesPage.typesOfCookies.necessary.description")}
          </li>
          <li>
            <strong>{t("cookiesPage.typesOfCookies.analytical.title")}</strong>{" "}
            - {t("cookiesPage.typesOfCookies.analytical.description")}
          </li>
          <li>
            <strong>{t("cookiesPage.typesOfCookies.functional.title")}</strong>{" "}
            - {t("cookiesPage.typesOfCookies.functional.description")}
          </li>
        </ul>
        <h2 className="my-2 text-3xl">
          {t("cookiesPage.manageCookies.title")}
        </h2>
        <p>{t("cookiesPage.manageCookies.description")}</p>
        <ul className="list-disc pl-5">
          <li>{t("cookiesPage.manageCookies.acceptAll")}</li>
          <li>{t("cookiesPage.manageCookies.rejectOptional")}</li>
        </ul>
        <p>
          {t("cookiesPage.manageCookies.changeSettings")}{" "}
          <button onClick={() => setIsOpen(true)}>
            <strong>{t("cookiesPage.manageCookies.here")}</strong>
          </button>{" "}
          {t("cookiesPage.manageCookies.orBrowser")}
        </p>
        <h2 className="my-2 text-3xl">
          {t("cookiesPage.policyChanges.title")}
        </h2>
        <p>{t("cookiesPage.policyChanges.description")}</p>
        <h2 className="my-2 text-3xl">{t("cookiesPage.contact.title")}</h2>
        <p>
          {t("cookiesPage.contact.description")}{" "}
          <span className="font-semibold">
            {t("cookiesPage.contact.email")}
          </span>
          .
        </p>
      </div>
      <CookiesDialog
        externalOpen={isOpen}
        onExternalClose={() => setIsOpen(false)}
      />
    </>
  );
};

export default CookiesPage;
