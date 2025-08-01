import { useMutation, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";
import { useNavigate } from "react-router";

export const useLeaveLocal = () => {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();
  const navigate = useNavigate();
  return useMutation({
    mutationFn: async (id: string) => {
      await api.patch(`/me/locals/${id}/leave`);
    },
    onSuccess: () => {
      navigate("/owner/locals");
      queryClient.invalidateQueries({ queryKey: ["ownLocals"] });
      toast({
        title: t("leaveLocal.successTitle"),
        description: t("leaveLocal.successDescription"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("leaveLocal.errorTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });
};
