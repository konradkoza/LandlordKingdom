import DataField from "@/components/DataField";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useGetActiveLocal } from "@/data/local/useGetActiveLocal";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { t } from "i18next";
import { FC } from "react";
import { Button } from "@/components/ui/button";
import { useParams } from "react-router";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import LoadingButton from "@/components/LoadingButton";
import { useGetUserApplication } from "@/data/application/useGetUserApplication";
import { useCreateApplication } from "@/data/application/useCreateApplication";
import { useQueryClient } from "@tanstack/react-query";
import { toLocaleFixed } from "@/utils/currencyFormat";
import LocalImages from "./LocalImages";
import { useUserStore } from "@/store/userStore";

const ActiveLocalDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { local } = useGetActiveLocal(id!);
  const { createApplication } = useCreateApplication();
  const { id: userId } = useUserStore();
  const { application, isError } = useGetUserApplication(
    id!,
    userId != undefined && userId !== null
  );
  const queryClient = useQueryClient();

  const breadcrumbs = useBreadcrumbs(
    userId
      ? [
          {
            title: t("breadcrumbs.tenant"),
            path: "/tenant",
          },
          {
            title: t("breadcrumbs.locals"),
            path: "/tenant/locals",
          },
          {
            title: local?.name ?? "",
            path: `/tenant/locals/${id}`,
          },
        ]
      : [
          {
            title: t("breadcrumbs.locals"),
            path: "/",
          },
          {
            title: local?.name ?? "",
            path: `/tenant/locals/${id}`,
          },
        ]
  );

  const handleCreateApplication = async () => {
    await createApplication(id!);
    queryClient.invalidateQueries({ queryKey: ["userApplication"] });
  };

  return (
    <>
      {breadcrumbs}
      {local && (
        <div className="mt-2 flex flex-col gap-4">
          <Card>
            <CardHeader>
              <CardTitle className="text-center">{local.name}</CardTitle>
            </CardHeader>
          </Card>
          <Card className="relative pt-6">
            <CardContent>
              <div className="grid w-2/3 grid-cols-3 gap-2">
                <p className="col-span-3 text-xl font-semibold">
                  {t("activeLocalDetails.localInformation")}
                </p>
                <DataField
                  label={t("activeLocalDetails.city")}
                  value={local.city}
                />
                <DataField
                  label={t("activeLocalDetails.size")}
                  value={local.size + " m²"}
                />
                <DataField
                  label={t("activeLocalDetails.price")}
                  value={toLocaleFixed(local?.price ?? 0.0)}
                />

                <p className="col-span-3 text-xl font-semibold">
                  {t("activeLocalDetails.ownerInformation")}
                </p>
                <DataField
                  label={t("activeLocalDetails.firstName")}
                  value={local.ownerName}
                />
              </div>

              <p className="col-span-2 mt-2 text-xl font-semibold">
                {t("localDetails.description")}
              </p>
              <div>{local.description}</div>

              <LocalImages id={id!} />

              {userId && (
                <AlertDialog>
                  <AlertDialogTrigger asChild>
                    <Button className="mt-4">
                      {t("activeLocalDetails.apply")}
                    </Button>
                  </AlertDialogTrigger>
                  <AlertDialogContent>
                    <AlertDialogHeader>
                      <AlertDialogTitle>
                        {t("activeLocalDetails.applicationTitle")}
                      </AlertDialogTitle>
                      <AlertDialogDescription>
                        {application
                          ? t(
                              "activeLocalDetails.applicationExistsDescription"
                            ) + application.createdAt
                          : t("activeLocalDetails.applicationDescription")}
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>{t("cancel")}</AlertDialogCancel>
                      <AlertDialogAction asChild>
                        <LoadingButton
                          text={t("confirm")}
                          isLoading={!isError && !application}
                          disableButton={application != undefined}
                          onClick={async () => await handleCreateApplication()}
                        />
                      </AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
              )}

              <RefreshQueryButton
                className="absolute right-1 top-1"
                queryKeys={["activeLocalDetails"]}
              />
            </CardContent>
          </Card>
        </div>
      )}
    </>
  );
};

export default ActiveLocalDetailsPage;
