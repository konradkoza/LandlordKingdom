import { Outlet } from "react-router";
import BaseLayout from "./BaseLayout";

const BaseMeLayout = () => (
  <BaseLayout type="me">
    <Outlet />
  </BaseLayout>
);

export default BaseMeLayout;
