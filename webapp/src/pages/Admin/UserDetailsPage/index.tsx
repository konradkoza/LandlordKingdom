import { FC, useState } from "react";
import { useTranslation } from "react-i18next";
import { Navigate, NavLink, useParams } from "react-router";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useGetUserQuery } from "@/data/useUser";
import { useBlockUser } from "@/data/useBlockUser.ts";
import { useUnblockUser } from "@/data/useBlockUser.ts";
import { useTenantRole } from "@/data/roles/useTenantRole";
import { useOwnerRole } from "@/data/roles/useOwnerRole";
import { useAdminRole } from "@/data/roles/useAdminRole";
import UpdateUserDataDialog from "./updateUserDataDialog";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import DataField from "@/components/DataField";
import UpdateUserEmailAddress from "./UpdateUserEmailAddress";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";

const UserDetailsPage: FC = () => {
  const { t } = useTranslation();
  const { id } = useParams<{ id: string }>();
  const { blockUser } = useBlockUser();
  const { unblockUser } = useUnblockUser();
  const { addTenantRole, removeTenantRole } = useTenantRole();
  const { addOwnerRole, removeOwnerRole } = useOwnerRole();
  const { addAdminRole, removeAdminRole } = useAdminRole();
  const { data } = useGetUserQuery(id!);
  const breadcrumbs = useBreadcrumbs([
    { title: "Admin", path: "/admin" },
    { title: "Users", path: "/admin/users" },
    { title: data?.data.login ?? "", path: `/admin/users/${id}` },
  ]);
  const [openUpdateUserDataDialog, setOpenUpdateUserDataDialog] =
    useState<boolean>(false);

  if (data?.status && (data.status < 200 || data?.status >= 300)) {
    return <Navigate to="/admin/users" />;
  }

  const handleUpdateUserDataDialogOpen = () => {
    setOpenUpdateUserDataDialog(true);
  };

  return (
    <div className="flex flex-col">
      {breadcrumbs}
      {openUpdateUserDataDialog && id && data && (
        <UpdateUserDataDialog
          id={id}
          userData={data.data}
          etag={data.headers.etag}
          setOpenUpdateUserDataDialog={setOpenUpdateUserDataDialog}
          openUpdateUserDataDialog={openUpdateUserDataDialog}
        />
      )}
      {data && (
        <Card className="relative mt-3">
          <div className="absolute right-0 top-0 flex">
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost">...</Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent>
                <DropdownMenuLabel>
                  {t("userDetailsPage.actions")}
                </DropdownMenuLabel>
                {data.data.blocked ? (
                  <DropdownMenuItem
                    onClick={async () => {
                      await unblockUser(data.data.id);
                    }}
                  >
                    {t("block.unblockUserAction")}
                  </DropdownMenuItem>
                ) : (
                  <DropdownMenuItem
                    onClick={async () => {
                      await blockUser(data.data.id);
                    }}
                  >
                    {t("block.blockUserAction")}
                  </DropdownMenuItem>
                )}
                <DropdownMenuItem
                  onClick={() => handleUpdateUserDataDialogOpen()}
                >
                  {t("updateDataForm.updateUserData")}
                </DropdownMenuItem>
                {data.data.roles.includes("OWNER") && (
                  <DropdownMenuItem>
                    <NavLink to={`/admin/add-local?ownerId=${data.data.id}`}>
                      {t("userDetailsPage.addLocal")}
                    </NavLink>
                  </DropdownMenuItem>
                )}
              </DropdownMenuContent>
            </DropdownMenu>
            <RefreshQueryButton queryKeys={["user"]} />
          </div>
          <div>
            <CardHeader className="items-center">
              <CardTitle>{t("mePage.basicInformation")}</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="flex justify-center">
                <div className="grid w-2/3 grid-cols-2 gap-2">
                  <DataField
                    label={t("userDetailsPage.firstName")}
                    value={data?.data.firstName ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.lastName")}
                    value={data?.data.lastName ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.email")}
                    value={data?.data.email ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.login")}
                    value={data?.data.login ?? "-"}
                  />
                  <DataField
                    label={t("userDetailsPage.blocked")}
                    value={
                      data?.data.blocked ? t("common.yes") : t("common.no")
                    }
                  />
                  <DataField
                    label={t("userDetailsPage.verified")}
                    value={
                      data?.data.verified ? t("common.yes") : t("common.no")
                    }
                  />
                  <DataField
                    label={t("userDetailsPage.language")}
                    value={data?.data.language ?? "-"}
                  />
                </div>
              </div>
            </CardContent>
          </div>
        </Card>
      )}
      <Card className="mt-3">
        <CardHeader className="items-center">
          <CardTitle>{t("userDetailsPage.role.title")}</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex justify-center gap-2">
            {data?.data.roles.includes("TENANT") ? (
              <Button
                onClick={() => removeTenantRole(data?.data.id || "")}
                disabled={data.data.roles.length === 1}
              >
                {t("userDetailsPage.role.remove.tenant")}
              </Button>
            ) : (
              <Button onClick={() => addTenantRole(data?.data.id || "")}>
                {t("userDetailsPage.role.add.tenant")}
              </Button>
            )}
            {data?.data.roles.includes("OWNER") ? (
              <Button
                onClick={() => removeOwnerRole(data?.data.id || "")}
                disabled={data.data.roles.length === 1}
              >
                {t("userDetailsPage.role.remove.owner")}
              </Button>
            ) : (
              <Button onClick={() => addOwnerRole(data?.data.id || "")}>
                {t("userDetailsPage.role.add.owner")}
              </Button>
            )}
            {data?.data.roles.includes("ADMINISTRATOR") ? (
              <Button
                onClick={() => removeAdminRole(data?.data.id || "")}
                disabled={data.data.roles.length === 1}
              >
                {t("userDetailsPage.role.remove.administrator")}
              </Button>
            ) : (
              <Button onClick={() => addAdminRole(data?.data.id || "")}>
                {t("userDetailsPage.role.add.administrator")}
              </Button>
            )}
          </div>
        </CardContent>
      </Card>
      <Card className="mt-3">
        <CardHeader className="items-center">
          <CardTitle>{t("userDetailsPage.changeEmail")}</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex justify-center">
            <UpdateUserEmailAddress id={id!} />
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default UserDetailsPage;
