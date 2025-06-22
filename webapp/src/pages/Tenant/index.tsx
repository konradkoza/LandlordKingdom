import { FC } from "react";
import { Navigate } from "react-router";

const TenantPage: FC = () => {
  return <Navigate to="current-rents" />;
};

export default TenantPage;
