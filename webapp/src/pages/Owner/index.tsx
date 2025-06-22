import { FC } from "react";
import { Navigate } from "react-router";

const OwnerPage: FC = () => {
  return <Navigate to="/owner/locals" />;
};

export default OwnerPage;
