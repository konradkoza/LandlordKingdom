import CookiesDialog from "@/components/CookiesDialog";
import React from "react";

const CookiesPage: React.FC = () => {
  const [isOpen, setIsOpen] = React.useState(false);

  return (
    <>
      <div>
        <h1 className="my-6 text-5xl font-semibold">Polityka plików cookies</h1>
        <h2 className="my-2 text-3xl">Czym są pliki cookies?</h2>
        <p>
          Pliki cookies (tzw. „ciasteczka”) to niewielkie pliki tekstowe
          zapisywane na Twoim urządzeniu (komputerze, tablecie, smartfonie)
          podczas korzystania z naszej strony internetowej. Umożliwiają one
          m.in. prawidłowe działanie serwisu, analizowanie ruchu i
          dostosowywanie treści do Twoich preferencji.
        </p>
        <h2 className="my-2 text-3xl">Jakich cookies używamy?</h2>
        <p>
          Na naszej stronie wykorzystujemy następujące rodzaje plików cookies:
        </p>
        <ul>
          <li>
            <strong>Niezbędne</strong> - umożliwiają prawidłowe działanie
            strony, np. logowanie, nawigację czy zapisanie ustawień prywatności.
            Nie wymagają zgody użytkownika.
          </li>
          <li>
            <strong>Analityczne</strong> - pozwalają zbierać anonimowe dane
            statystyczne o sposobie korzystania ze strony, np. za pomocą Google
            Analytics.
          </li>
          <li>
            <strong>Funkcjonalne</strong> - zapamiętują Twoje preferencje (np.
            język, lokalizację) w celu poprawy komfortu korzystania.
          </li>
        </ul>
        <h2 className="my-2 text-3xl">Jak zarządzać plikami cookies?</h2>
        <p>
          Podczas pierwszej wizyty na naszej stronie pojawia się baner z
          informacją o plikach cookies. Masz możliwość:
        </p>
        <ul className="list-disc pl-5">
          <li>zaakceptowania wszystkich cookies,</li>
          <li>odrzucenia ich z wyjątkiem niezbędnych</li>
        </ul>
        <p>
          W każdej chwili możesz zmienić swoje ustawienia cookies, klikając{" "}
          <button onClick={() => setIsOpen(true)}>
            <strong>tutaj</strong>
          </button>{" "}
          lub zmieniając ustawienia w swojej przeglądarce.
        </p>
        <h2 className="my-2 text-3xl">Zmiany w polityce cookies</h2>
        <p>
          Zastrzegamy sobie prawo do wprowadzania zmian w niniejszej polityce.
          Aktualna wersja zawsze będzie dostępna na tej stronie.
        </p>
        <h2 className="my-2 text-3xl">Kontakt</h2>
        <p>
          W przypadku pytań dotyczących polityki cookies, skontaktuj się z nami
          pod adresem:{" "}
          <a href="mailto:[adres e-mail kontaktowy]">
            [adres e-mail kontaktowy]
          </a>
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
