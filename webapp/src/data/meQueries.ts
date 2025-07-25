import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { UserResponse } from "@/types/user/UserResponseType.ts";
import { UserUpdateRequestType } from "@/types/user/UserUpdateRequestType.ts";
import { toast, useToast } from "@/components/ui/use-toast.ts";
import { useTranslation } from "react-i18next";
import { useLanguageStore } from "@/i18n/languageStore";
import useAxiosPrivate from "./useAxiosPrivate";
import { AxiosError, AxiosInstance } from "axios";
import { ErrorCode } from "@/@types/errorCode";
import { t } from "i18next";

interface UserUpdateRequest {
  request: UserUpdateRequestType;
  etag: string;
}

const putMeData = async (data: UserUpdateRequest, api: AxiosInstance) => {
  await api.put("/me", data.request, {
    headers: {
      "If-Match": data.etag,
    },
  });
};

export const useMeQuery = (execute: boolean = true) => {
  const { setLanguage } = useLanguageStore();
  const { api } = useAxiosPrivate();
  return useQuery({
    queryKey: ["meData"],
    queryFn: async () => {
      try {
        const response = await api.get<UserResponse>("/me");
        setLanguage(response.data.language);
        return response;
      } catch (error) {
        const axiosError = error as AxiosError;
        toast({
          variant: "destructive",
          title: t("userDataPage.error"),
          description: t(
            `errors.${(axiosError.response?.data as ErrorCode).exceptionCode}`
          ),
        });
        return Promise.reject(error);
      }
    },
    enabled: execute,
  });
};

export const useMeMutation = () => {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();
  return useMutation({
    mutationFn: (data: UserUpdateRequest) => putMeData(data, api),
    onSettled: async (_, error) => {
      if (error) {
        const axiosError = error as AxiosError;
        toast({
          variant: "destructive",
          title: t("userDataPage.error"),
          description: t(
            `errors.${(axiosError.response?.data as ErrorCode).exceptionCode}`
          ),
        });
      } else {
        await queryClient.invalidateQueries({ queryKey: ["meData"] });
        toast({
          title: t("userDataPage.success"),
        });
      }
    },
  });
};
