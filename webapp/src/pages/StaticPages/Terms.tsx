import { useTranslation } from "react-i18next";

const TermsPage: React.FC = () => {
  const { t } = useTranslation();

  return (
    <div style={{ maxWidth: 800, margin: "0 auto", padding: "2rem" }}>
      <h1 className="my-6 text-5xl font-semibold">{t("termsPage.title")}</h1>
      <h2 className="my-2 text-3xl">{t("termsPage.section1.title")}</h2>
      <p>{t("termsPage.section1.paragraph1")}</p>
      <p>{t("termsPage.section1.paragraph2")}</p>
      <p>{t("termsPage.section1.paragraph3")}</p>

      <h2 className="my-2 text-3xl">{t("termsPage.section2.title")}</h2>
      <ul>
        <li>{t("termsPage.section2.point1")}</li>
        <li>{t("termsPage.section2.point2")}</li>
        <li>
          {t("termsPage.section2.point3.main")}
          <ul>
            <li>{t("termsPage.section2.point3.sub1")}</li>
            <li>{t("termsPage.section2.point3.sub2")}</li>
            <li>{t("termsPage.section2.point3.sub3")}</li>
          </ul>
        </li>
      </ul>

      <h2 className="my-2 text-3xl">{t("termsPage.section3.title")}</h2>
      <p>{t("termsPage.section3.paragraph1")}</p>
      <p>{t("termsPage.section3.paragraph2")}</p>

      <h2 className="my-2 text-3xl">{t("termsPage.section4.title")}</h2>
      <p>{t("termsPage.section4.paragraph1")}</p>
      <p>{t("termsPage.section4.paragraph2")}</p>

      <h2 className="my-2 text-3xl">{t("termsPage.section5.title")}</h2>
      <p>{t("termsPage.section5.paragraph1")}</p>
      <p>{t("termsPage.section5.paragraph2")}</p>

      <h2 className="my-2 text-3xl">{t("termsPage.section6.title")}</h2>
      <p>{t("termsPage.section6.paragraph")}</p>
    </div>
  );
};

export default TermsPage;
