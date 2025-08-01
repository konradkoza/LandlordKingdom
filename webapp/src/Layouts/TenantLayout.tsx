import { FC } from "react";
import BaseLayout, { NavigationLink } from "./BaseLayout";
import { Navigate, Outlet } from "react-router";
import { useUserStore } from "@/store/userStore";

const links: NavigationLink[] = [
  {
    label: "locals",
    path: "./locals",
  },
  {
    label: "currentRents",
    path: "./current-rents",
  },
  {
    label: "archivalRents",
    path: "./archival-rents",
  },
  {
    label: "applications",
    path: "./applications",
  },
];

const TenantLayout: FC = () => {
  const { roles, activeRole } = useUserStore();
  if (!roles?.includes("TENANT")) {
    return <Navigate to={"/error"} />;
  }

  if (activeRole !== "TENANT") {
    return <Navigate to={"/error"} />;
  }

  return (
    <BaseLayout type="tenant" links={links}>
      <Outlet />
    </BaseLayout>
  );
};

export default TenantLayout;
