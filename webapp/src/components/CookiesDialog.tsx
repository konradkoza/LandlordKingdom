import React from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
} from "./ui/dialog";
import { Button } from "./ui/button";

type CookiesDialogProps = {
  externalOpen?: boolean;
  onExternalClose?: () => void;
};

const CookiesDialog: React.FC<CookiesDialogProps> = ({
  externalOpen,
  onExternalClose,
}) => {
  const isAlreadyAccepted = localStorage.getItem("cookiesAccepted");
  const initialOpen = isAlreadyAccepted !== "true";
  const [isOpen, setOpen] = React.useState(initialOpen);

  const handleAccept = () => {
    localStorage.setItem("cookiesAccepted", "true");
    setOpen(false);
    onExternalClose?.();
  };

  const handleDecline = () => {
    setOpen(false);
    onExternalClose?.();
  };

  return (
    <Dialog open={isOpen || externalOpen} onOpenChange={setOpen}>
      <DialogContent onInteractOutside={(e) => e.preventDefault()}>
        <DialogHeader>🍪 Używamy plików cookies</DialogHeader>
        <DialogDescription>
          Nasza strona korzysta z plików cookies, aby zapewnić Ci jak najlepsze
          doświadczenie, analizować ruch i dostosowywać treści.
        </DialogDescription>
        <DialogFooter>
          <Button onClick={handleDecline}>Odrzuć</Button>
          <Button onClick={handleAccept}>Akceptuję</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default CookiesDialog;
