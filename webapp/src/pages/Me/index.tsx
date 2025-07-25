import { FC } from "react";
import UserData from "./UserData";
import ChangeUserPassword from "./ChangeUserPassword";
import UpdateEmailAddress from "./UpdateEmailAddress";
import { useMeQuery } from "@/data/meQueries";
import DataField from "../../components/DataField";
import { useTranslation } from "react-i18next";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Card,
  CardContent,
  CardHeader,
  CardDescription,
  CardTitle,
} from "@/components/ui/card";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { useNavigate, useSearchParams } from "react-router";
import { Button } from "@/components/ui/button";
const MePage: FC = () => {
  const { t } = useTranslation();
  const { data } = useMeQuery();
  const [searchParams] = useSearchParams();
  const origin = searchParams.get("origin") ?? "/";
  const navigate = useNavigate();

  return (
    <div className="flex flex-col gap-2 pb-6">
      <Button
        onClick={() => navigate(origin)}
        variant="ghost"
        className="self-start"
      >
        Go back
      </Button>
      <Card className="mt-3 flex justify-center">
        <CardHeader>
          <CardTitle>{t("mePage.title")}</CardTitle>
        </CardHeader>
      </Card>
      <Tabs defaultValue="basic">
        <TabsList>
          <TabsTrigger value="basic">
            {t("mePage.basicInformation")}
          </TabsTrigger>
          <TabsTrigger value="updateData">{t("mePage.updateData")}</TabsTrigger>
          <TabsTrigger value="changePassword">
            {t("mePage.changePassword")}
          </TabsTrigger>
          <TabsTrigger value="changeEmail">
            {t("mePage.changeEmail")}
          </TabsTrigger>
        </TabsList>
        <TabsContent value="basic">
          <Card className="relative mt-3">
            <div className="absolute right-0 top-0">
              <RefreshQueryButton queryKeys={["meData"]} />
            </div>
            <div>
              <CardHeader className="items-center">
                <CardTitle>{t("mePage.basicInformation")}</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="flex justify-center">
                  <div className="grid- grid w-2/3 grid-cols-2 gap-2">
                    <DataField
                      label={t("mePage.firstNameLabel")}
                      value={data?.data.firstName ?? "-"}
                    />
                    <DataField
                      label={t("mePage.lastNamelabel")}
                      value={data?.data.lastName ?? "-"}
                    />
                    <DataField
                      label={t("mePage.emailLabel")}
                      value={data?.data.email ?? "-"}
                    />
                  </div>
                </div>
              </CardContent>
            </div>
          </Card>
        </TabsContent>
        <TabsContent value="updateData">
          <Card className="relative mt-3 self-center">
            <div className="absolute right-0 top-0">
              <RefreshQueryButton queryKeys={["meData"]} />
            </div>
            <CardHeader className="items-center">
              <CardTitle>{t("mePage.updateData")}</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="flex justify-center">
                <UserData />
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="changePassword">
          <Card className="mt-3">
            <CardHeader className="items-center">
              <CardTitle>{t("mePage.changePassword")}</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="flex justify-center">
                <ChangeUserPassword />
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="changeEmail">
          <Card className="mt-3">
            <CardHeader className="items-center">
              <CardTitle>{t("mePage.changeEmail")}</CardTitle>
            </CardHeader>
            <CardContent className="pb-2">
              <div className="flex justify-center">
                <UpdateEmailAddress />
              </div>
            </CardContent>
            <CardDescription className="flex justify-center px-6 pb-5">
              {t("mePage.changeEmailDescription")}
            </CardDescription>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  );
};

export default MePage;
