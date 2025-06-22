const TermsPage: React.FC = () => {
  return (
    <div style={{ maxWidth: 800, margin: "0 auto", padding: "2rem" }}>
      <h1 className="my-6 text-5xl font-semibold">
        Regulamin korzystania z serwisu
      </h1>
      <h2 className="my-2 text-3xl">§1 Postanowienia ogólne</h2>
      <p>
        Niniejszy regulamin określa zasady korzystania z serwisu internetowego{" "}
        <strong>[TwojaNazwa.pl]</strong>.
      </p>
      <p>
        Każdy użytkownik korzystający z serwisu akceptuje warunki określone w
        niniejszym regulaminie.
      </p>
      <p>
        Właścicielem serwisu jest{" "}
        <strong>[Nazwa firmy / osoby fizycznej]</strong>, z siedzibą w{" "}
        <strong>[adres]</strong>, NIP: <strong>[numer]</strong>.
      </p>

      <h2 className="my-2 text-3xl">§2 Zasady korzystania</h2>
      <ul>
        <li>
          Serwis jest dostępny dla wszystkich użytkowników bez konieczności
          rejestracji.
        </li>
        <li>
          Użytkownik zobowiązuje się do korzystania z serwisu zgodnie z
          obowiązującym prawem oraz dobrymi obyczajami.
        </li>
        <li>
          Zabronione jest:
          <ul>
            <li>
              podejmowanie działań, które mogą zakłócić działanie serwisu,
            </li>
            <li>
              publikowanie treści o charakterze bezprawnym, obraźliwym lub
              spamerskim,
            </li>
            <li>wykorzystywanie treści ze strony bez zgody właściciela.</li>
          </ul>
        </li>
      </ul>

      <h2 className="my-2 text-3xl">§3 Prawa autorskie</h2>
      <p>
        Wszelkie treści, grafiki, zdjęcia i materiały opublikowane na stronie są
        chronione prawem autorskim.
      </p>
      <p>
        Kopiowanie, rozpowszechnianie lub wykorzystywanie ich w celach
        komercyjnych bez zgody właściciela jest zabronione.
      </p>

      <h2 className="my-2 text-3xl">§4 Odpowiedzialność</h2>
      <p>
        Właściciel serwisu dokłada starań, aby informacje zawarte na stronie
        były aktualne i poprawne, ale nie ponosi odpowiedzialności za ich
        wykorzystanie przez użytkowników.
      </p>
      <p>
        Serwis może zawierać linki do zewnętrznych stron – właściciel nie ponosi
        odpowiedzialności za ich treść.
      </p>

      <h2 className="my-2 text-3xl">§5 Zmiany regulaminu</h2>
      <p>
        Właściciel zastrzega sobie prawo do wprowadzania zmian w regulaminie w
        dowolnym czasie.
      </p>
      <p>Zmiany wchodzą w życie z dniem ich publikacji na stronie.</p>

      <h2 className="my-2 text-3xl">§6 Kontakt</h2>
      <p>
        W razie pytań dotyczących regulaminu prosimy o kontakt:{" "}
        <strong>[adres e-mail kontaktowy]</strong>
      </p>
    </div>
  );
};

export default TermsPage;
