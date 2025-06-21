import { Outlet } from "react-router-dom";
import BaseLayout from "./BaseLayout";

const BaseMeLayout = () => (
  <BaseLayout type="me">
    <Outlet />
  </BaseLayout>
);

export default BaseMeLayout;
