const ContanctPage: React.FC = () => {
  return (
    <div>
      <h1 className="my-6 text-5xl font-semibold">Kontakt</h1>
      <p>
        Masz pytania, sugestie lub potrzebujesz więcej informacji? Skontaktuj
        się z nami - chętnie pomożemy!
      </p>

      <h2 className="my-2 text-3xl">E-mail</h2>
      <p>
        <a href="mailto:kontakt@twojadomena.pl">kontakt@twojadomena.pl</a>
      </p>

      <h2 className="my-2 text-3xl">Telefon</h2>
      <p>
        <a href="tel:+48123456789">+48 123 456 789</a>
        <br />
        <small>(Pon.-Pt. 9:00-17:00)</small>
      </p>

      <h2 className="my-2 text-3xl">Adres korespondencyjny</h2>
      <address>
        Twoja Firma Sp. z o.o.
        <br />
        ul. Przykładowa 1<br />
        00-000 Warszawa
      </address>
    </div>
  );
};

export default ContanctPage;
