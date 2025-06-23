import { useTranslation } from "react-i18next";

const AboutPage: React.FC = () => {
  const { t } = useTranslation();

  return (
    <div>
      <h1 className="my-6 text-5xl font-semibold">{t("aboutPage.title")}</h1>
      <p className="mb-4">{t("aboutPage.description")}</p>

      <h2 className="my-4 text-3xl font-semibold">
        {t("aboutPage.mission.title")}
      </h2>
      <p className="mb-4">{t("aboutPage.mission.description")}</p>

      <h2 className="my-4 text-3xl font-semibold">
        {t("aboutPage.vision.title")}
      </h2>
      <p className="mb-4">{t("aboutPage.vision.description")}</p>

      <h2 className="my-4 text-3xl font-semibold">
        {t("aboutPage.values.title")}
      </h2>
      <ul className="mb-4 list-disc pl-6">
        <li className="mb-2">{t("aboutPage.values.transparency")}</li>
        <li className="mb-2">{t("aboutPage.values.reliability")}</li>
        <li className="mb-2">{t("aboutPage.values.innovation")}</li>
        <li className="mb-2">{t("aboutPage.values.customerService")}</li>
      </ul>
    </div>
  );
};

export default AboutPage;
