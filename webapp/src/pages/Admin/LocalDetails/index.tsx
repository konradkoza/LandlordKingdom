import { useGetLocalDetailsForAdmin } from "@/data/local/useGetLocalDetailsForAdmin";
import { FC } from "react";
import { useNavigate, useParams } from "react-router";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import DataField from "@/components/DataField";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { LoadingData } from "@/components/LoadingData";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogClose,
} from "@/components/ui/dialog";
import { useArchiveLocal } from "@/data/local/useArchiveLocal";
import ChangeAddressFormComponent from "./ChangeAddressFormComponent";
import UpdateLocal from "@/pages/Admin/LocalDetails/UpdateLocal.tsx";
import { toLocaleFixed } from "@/utils/currencyFormat";
import UpdateOwnLocalFixedFee from "@/pages/Owner/OwnLocalDetails/UpdateOwnLocalFixedFee";
import LeaveLocalCard from "@/pages/Owner/OwnLocalDetails/LeaveLocalCard";
import LocalApplications from "@/pages/Owner/OwnLocalDetails/ShowApplications";
import { LocalState } from "@/@types/localState";
import UploadImageCard from "@/pages/Owner/OwnLocalDetails/UploadImage";
import { useGetLocalImages } from "@/data/local/useImage";

const LocalDetailsPage: FC = () => {
  const { id } = useParams<{ id: string }>();
  const { data, isLoading } = useGetLocalDetailsForAdmin(id!);
  const { archiveLocal } = useArchiveLocal();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const { data: imagesIds } = useGetLocalImages(id!);

  const breadcrumbs = useBreadcrumbs([
    { title: t("roles.administrator"), path: "/admin" },
    { title: t("allLocals.title"), path: "/admin/locals" },
    { title: data?.data.name ?? "", path: `/admin/locals/local/${id}` },
  ]);

  if (isLoading) {
    return <LoadingData />;
  }

  const handleArchiveLocal = async () => {
    await archiveLocal(id!);
  };

  return (
    <div className="flex flex-col gap-2">
      {breadcrumbs}
      {data && (
        <>
          <Card className="mt-2">
            <CardHeader>
              <CardTitle className="text-center">{data.data.name}</CardTitle>
            </CardHeader>
          </Card>
          <Tabs defaultValue="basic">
            {data.data.state !== "ARCHIVED" && (
              <TabsList className="mt-1">
                <TabsTrigger value="basic">
                  {t("localDetails.basicInformation")}
                </TabsTrigger>
                <TabsTrigger value="updateData">
                  {t("localDetails.updateData")}
                </TabsTrigger>
                <TabsTrigger value="changeAddress">
                  {t("localDetails.changeAddress")}
                </TabsTrigger>
                <TabsTrigger value="changeFixedFee">
                  {t("ownLocalDetails.changeFixedFee")}
                </TabsTrigger>
                <TabsTrigger value="leaveLocal">
                  {t("ownLocalDetails.leaveLocal")}
                </TabsTrigger>
                <TabsTrigger value="checkApplications">
                  {t("ownLocalDetails.showApplications")}
                </TabsTrigger>
                <TabsTrigger value="uploadImage">
                  {t("ownLocalDetails.uploadImage")}
                </TabsTrigger>
              </TabsList>
            )}
            <TabsContent value="basic">
              <Card className="relative mb-2">
                <CardHeader>
                  <CardTitle className="text-center">
                    {t("localDetails.localInformation")}
                  </CardTitle>
                </CardHeader>
                <CardContent className="flex justify-center">
                  <div className="grid w-2/3 grid-cols-2 gap-2">
                    <DataField
                      label={t("localDetails.size")}
                      value={data.data.size.toString() + " m²"}
                    />
                    <DataField
                      label={t("localDetails.rentalFee")}
                      value={toLocaleFixed(data.data.rentalFee)}
                    />
                    <DataField
                      label={t("localDetails.marginFee")}
                      value={toLocaleFixed(data.data.marginFee)}
                    />
                    <DataField
                      label={t("localDetails.state")}
                      value={t(`localState.${data.data.state}`)}
                    />

                    {data.data.owner && (
                      <>
                        <p className="col-span-2 text-xl font-semibold">
                          {t("localDetails.ownerInformation")}
                        </p>
                        <DataField
                          label={t("localDetails.firstName")}
                          value={data.data.owner.firstName}
                        />
                        <DataField
                          label={t("localDetails.lastName")}
                          value={data.data.owner.lastName}
                        />
                        <DataField
                          label={t("localDetails.login")}
                          value={data.data.owner.login}
                        />
                        <DataField
                          label={t("localDetails.email")}
                          value={data.data.owner.email}
                        />
                      </>
                    )}

                    <p className="col-span-2 text-xl font-semibold">
                      {t("localDetails.addressInformation")}{" "}
                    </p>

                    <DataField
                      label={t("localDetails.country")}
                      value={data.data.address.country}
                    />
                    <DataField
                      label={t("localDetails.city")}
                      value={data.data.address.city}
                    />
                    <DataField
                      label={t("localDetails.street")}
                      value={data.data.address.street}
                    />
                    <DataField
                      label={t("localDetails.number")}
                      value={data.data.address.number}
                    />
                    <DataField
                      label={t("localDetails.zipCode")}
                      value={`${data.data.address.zipCode}`}
                    />

                    <div className="col-span-2 flex flex-col">
                      <div className="text-sm font-semibold">
                        {t("localDetails.description")}
                      </div>
                      <div>{data.data.description}</div>
                    </div>

                    <div className="col-span-2 flex gap-3">
                      {data.data.state !== "WITHOUT_OWNER" &&
                        data.data.state !== "ARCHIVED" && (
                          <Button
                            variant="secondary"
                            className="mt-3 w-full text-lg font-normal"
                            onClick={() => {
                              navigate(
                                `/admin/users/${data.data.owner!.userId}`
                              );
                            }}
                          >
                            {t("localDetails.showOwnerDetails")}
                          </Button>
                        )}
                      {data.data.state === "WITHOUT_OWNER" && (
                        <Dialog>
                          <DialogTrigger asChild>
                            <Button
                              variant="destructive"
                              className="mt-3 w-full text-lg font-normal"
                            >
                              {t("localDetails.archiveLocal")}
                            </Button>
                          </DialogTrigger>
                          <DialogContent>
                            <DialogHeader>
                              {t("localDetails.archiveLocal")}
                            </DialogHeader>
                            <p>{t("localDetails.archiveLocalDescription")}</p>
                            <div className="flex gap-2">
                              <DialogClose asChild>
                                <Button
                                  variant="default"
                                  className="mt-3 w-full text-lg font-normal"
                                >
                                  {t("localDetails.close")}
                                </Button>
                              </DialogClose>
                              <DialogClose asChild>
                                <Button
                                  variant="destructive"
                                  className="mt-3 w-full text-lg font-normal"
                                  onClick={handleArchiveLocal}
                                >
                                  {t("localDetails.archiveLocal")}
                                </Button>
                              </DialogClose>
                            </div>
                          </DialogContent>
                        </Dialog>
                      )}
                      {data.data.state === "UNAPPROVED" && (
                        <Button
                          variant="default"
                          className="mt-3 w-full text-lg font-normal"
                        >
                          {t("localDetails.approveLocal")}
                        </Button>
                      )}
                    </div>
                  </div>
                </CardContent>
                <RefreshQueryButton
                  className="absolute right-1 top-1"
                  queryKeys={["localDetailsForAdmin"]}
                />
              </Card>
            </TabsContent>
            <TabsContent value="updateData">
              <UpdateLocal localId={id!} />
            </TabsContent>
            <TabsContent value="changeAddress">
              <ChangeAddressFormComponent localId={id!} />
            </TabsContent>
            <TabsContent value="changeFixedFee">
              <Card>
                <CardHeader>
                  <CardTitle className="text-center">
                    {t("ownLocalDetails.changeFixedFee")}
                  </CardTitle>
                </CardHeader>
                <CardContent className="pb-2">
                  <div className="flex justify-center">
                    <UpdateOwnLocalFixedFee
                      id={id || ""}
                      initialRentalFee={
                        data.data.nextRentalFee ?? data.data.rentalFee
                      }
                      initialMarginFee={
                        data.data.nextMarginFee ?? data.data.marginFee
                      }
                      etag={data.headers.etag! ?? ""}
                    />
                  </div>
                </CardContent>
                <CardDescription className="flex justify-center px-6 pb-5">
                  {t("ownLocalDetails.changeFixedFeeDescription")}
                </CardDescription>
              </Card>
            </TabsContent>
            <TabsContent value="leaveLocal">
              <LeaveLocalCard state={data.data.state as LocalState} id={id!} />
            </TabsContent>
            <TabsContent value="checkApplications">
              <LocalApplications />
            </TabsContent>
            <TabsContent value="uploadImage">
              <UploadImageCard id={id!} images={imagesIds ?? []} />
            </TabsContent>
          </Tabs>
        </>
      )}
    </div>
  );
};

export default LocalDetailsPage;
