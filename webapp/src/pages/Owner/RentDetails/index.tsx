import { FC } from "react";
import RentInformation from "./RentInformation";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardHeader, CardTitle } from "@/components/ui/card";
import { useLocation, useParams } from "react-router";
import { useOwnerRent } from "@/data/rent/useOwnerRent";
import { RentPayments } from "@/components/RentPayments";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { useTranslation } from "react-i18next";
import { RentFixedFees } from "@/components/RentFixedFees";
import { RentVariableFees } from "@/components/RentVariableFees";
import { LoadingData } from "@/components/LoadingData";
import CreatePaymentDialog from "./CreatePaymentDialog";

const OwnerRentDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { t } = useTranslation();
  const { data, isLoading } = useOwnerRent(id!);
  const path =
    new Date(data?.endDate ?? Date()) < new Date()
      ? "/owner/archival-rents"
      : "/owner/current-rents";
  const location = useLocation();
  const breadcrumbs = useBreadcrumbs(
    location.pathname.startsWith("/admin/")
      ? [
          { title: t("ownerRentDetails.adminMainPage"), path: "/admin" },
          {
            title:
              path === "/owner/current-rents"
                ? t("ownerRentDetails.rents")
                : t("ownerRentDetails.archivalRents"),
            path:
              path.replace("/owner/", "/admin/") ??
              "/owner/current-rents".replace("/owner/", "/admin/"),
          },
          {
            title: `${
              path === "/owner/current-rents"
                ? t("ownerRentDetails.rents")
                : t("ownerRentDetails.archivalRents")
            } ${data?.local.name ?? "Not found"}`,
            path: `${path}/rent/${id ?? ""}`.replace("/owner/", "/admin/"),
          },
        ]
      : [
          { title: t("ownerRentDetails.ownerMainPage"), path: "/owner" },
          {
            title:
              path === "/owner/current-rents"
                ? t("ownerRentDetails.rents")
                : t("ownerRentDetails.archivalRents"),
            path: path ?? "/owner/current-rents",
          },
          {
            title: `${
              path === "/owner/current-rents"
                ? t("ownerRentDetails.rents")
                : t("ownerRentDetails.archivalRents")
            } ${data?.local.name ?? "Not found"}`,
            path: `${path}/rent/${id ?? ""}`,
          },
        ]
  );
  if (isLoading) {
    return <LoadingData />;
  }

  if (!data) {
    return (
      <div className="flex flex-col">
        {breadcrumbs}
        <div className="mt-2 text-center text-2xl">
          {t("ownerRentDetails.rentNotFound")}
        </div>
      </div>
    );
  }

  const isFutureEndDate = data?.endDate && new Date(data.endDate) > new Date();
  return (
    <div className="flex flex-col">
      {breadcrumbs}
      <Tabs className="mt-2" defaultValue="rentInformation">
        <div className="flex flex-row justify-between">
          <TabsList>
            <TabsTrigger value="rentInformation">
              {t("ownerRentDetails.rentInfo")}
            </TabsTrigger>
            <TabsTrigger value="payments">
              {t("ownerRentDetails.payments")}
            </TabsTrigger>
            <TabsTrigger value="fixedFees">
              {t("ownerRentDetails.fixedFees")}
            </TabsTrigger>
            <TabsTrigger value="variableFees">
              {t("ownerRentDetails.variableFees")}
            </TabsTrigger>
          </TabsList>
          {isFutureEndDate && <CreatePaymentDialog rentId={data?.id} />}
        </div>
        <TabsContent value="rentInformation">
          <RentInformation rent={data} />
        </TabsContent>
        <TabsContent value="payments">
          {data ? (
            <RentPayments
              id={id!}
              startDate={data!.startDate}
              endDate={data!.endDate}
            />
          ) : (
            <Card>
              <CardHeader className="text-center">
                <CardTitle>{t("ownerRentDetails.noPayments")}</CardTitle>
              </CardHeader>
            </Card>
          )}
        </TabsContent>
        <TabsContent value="fixedFees">
          <RentFixedFees
            id={id!}
            startDate={data!.startDate}
            endDate={data!.endDate}
            isTenant={false}
          />
        </TabsContent>
        <TabsContent value="variableFees">
          <RentVariableFees
            id={id!}
            startDate={data!.startDate}
            endDate={data!.endDate}
          />
        </TabsContent>
      </Tabs>
    </div>
  );
};

export default OwnerRentDetailsPage;
