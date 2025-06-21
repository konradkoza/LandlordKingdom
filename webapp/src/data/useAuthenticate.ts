import { ErrorCode } from "@/@types/errorCode";
import { useToast } from "@/components/ui/use-toast";
import { useMutation } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { useTranslation } from "react-i18next";
import { api } from "./api";

type AuthenticateRequest = {
  login: string;
  password: string;
  language: string;
};


export const useAuthenticate = () => {
  const { toast } = useToast();
  const { t } = useTranslation();

  const { mutateAsync, isSuccess, isPending } = useMutation({
    mutationFn: async (data: AuthenticateRequest) => {
      const response = await api.post("/auth/signin", data);
      return response.data;
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("loginPage.loginError"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { authenticate: mutateAsync, isSuccess, isPending };
};
