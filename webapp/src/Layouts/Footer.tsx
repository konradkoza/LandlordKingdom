import { cn } from "@/lib/utils";
import { useTranslation } from "react-i18next";
import Icon from "@/assets/ikona.svg";
import { Link } from "react-router";

type FooterProps = {
  className?: string;
};

const Footer: React.FC<FooterProps> = ({ className }) => {
  const { t } = useTranslation();
  return (
    <footer
      className={cn(
        "flex items-center justify-center px-10 py-10 text-xl text-black dark:text-white",
        className
      )}
    >
      <div className="flex w-full flex-row justify-center gap-40">
        <div className="flex flex-row items-center gap-5 text-base">
          <Link to={"/"}>Strona główna</Link>
          <Link to={"/contact"}>Kontakt</Link>
          <Link to={"/terms"}>Regulamin</Link>
          <Link to={"/cookies"}>Polityka Cookies</Link>
        </div>
        <div className="flex flex-row items-center justify-center gap-5">
          <img src={Icon} alt="Logo" className="h-14 w-14" />
          {t("logoPlaceholder")} 2025
        </div>
      </div>
    </footer>
  );
};

export default Footer;
