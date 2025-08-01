import AdminLayout from "./Layouts/AdminLayout";
import OwnerLayout from "./Layouts/OwnerLayout";
import TenantLayout from "./Layouts/TenantLayout";
import AccountLayout from "./Layouts/AccountLayout";
import loadable from "@loadable/component";
import LocalsPage from "./pages/Admin/Locals";
import OwnLocalsPage from "./pages/Owner/Locals";
import OwnerPage from "./pages/Owner";
import TenantPage from "./pages/Tenant";
import AdminPage from "./pages/Admin";
import BaseMeLayout from "./Layouts/BaseMeLayout";
import { RouteObject } from "react-router";

const UserDetailsPage = loadable(() => import("./pages/Admin/UserDetailsPage"));
const MePage = loadable(() => import("./pages/Me"));
const UserListPage = loadable(() => import("./pages/Admin/UserListPage"));
const LoginPage = loadable(() => import("./pages/Login"));
const RegisterPage = loadable(() => import("./pages/Register"));
const RegistrationSuccessPage = loadable(
  () => import("./pages/RegistrationSuccess")
);
const ResetPasswordPage = loadable(
  () => import("./pages/User/ResetPasswordPage")
);
const VerifyAccountPage = loadable(() => import("./pages/VerifyAccount"));
const UpdateEmailPage = loadable(() => import("./pages/UpdateEmail"));
const ResetPasswordForm = loadable(() => import("./pages/ResetPasswordForm"));
const Callback = loadable(() => import("./pages/OauthCallback"));
// const HomePage = loadable(() => import("./pages/Home"));
const CurrentRentsPage = loadable(() => import("./pages/Tenant/CurrentRents"));
const CurrentOwnerRentsPage = loadable(
  () => import("./pages/Owner/CurrentRents")
);
const ArchivalOwnerRentsPage = loadable(
  () => import("./pages/Owner/ArchivalRents")
);
const ArchivalRentsPage = loadable(
  () => import("./pages/Tenant/ArchivalRents")
);
const LocalDetailsPage = loadable(() => import("./pages/Admin/LocalDetails"));
const OwnLocalDetailsPage = loadable(
  () => import("./pages/Owner/OwnLocalDetails")
);
const RentDetailsPage = loadable(() => import("./pages/Tenant/RentDetails"));
const ActiveLocalsPage = loadable(() => import("./pages/Tenant/Locals"));
const ActiveLocalDetailsPage = loadable(
  () => import("./pages/Tenant/ActiveLocalDetails")
);
const NotApprovedActionsPage = loadable(
  () => import("./pages/Admin/NotApprovedActions")
);
const OwnerRentDetailsPage = loadable(
  () => import("./pages/Owner/RentDetails")
);
const OwnApplicationPage = loadable(
  () => import("./pages/Tenant/Applications")
);
const LandingPage = loadable(() => import("./pages/Public"));

const AddLocalPage = loadable(() => import("./pages/Owner/addLocalForm"));

const TermsPage = loadable(() => import("./pages/StaticPages/Terms"));
const CookiesPage = loadable(() => import("./pages/StaticPages/Cookies"));
const AboutPage = loadable(() => import("./pages/StaticPages/About"));
const ContactPage = loadable(() => import("./pages/StaticPages/Contact"));

const AdminRoutes: RouteObject[] = [
  { index: true, Component: AdminPage },
  {
    path: "locals",
    children: [
      { index: true, Component: LocalsPage },
      { path: "local/:id", Component: LocalDetailsPage },
    ],
  },
  { path: "users", Component: UserListPage },
  { path: "users/:id", Component: UserDetailsPage },
  { path: "not-approved", Component: NotApprovedActionsPage },
  { path: "add-local", Component: AddLocalPage },
  {
    path: "current-rents",
    children: [
      { index: true, Component: CurrentOwnerRentsPage },
      { path: "rent/:id", Component: OwnerRentDetailsPage },
    ],
  },
  {
    path: "archival-rents",
    children: [
      { index: true, Component: ArchivalOwnerRentsPage },
      { path: "rent/:id", Component: OwnerRentDetailsPage },
    ],
  },
];
const OwnerRoutes: RouteObject[] = [
  { index: true, Component: OwnerPage },
  {
    path: "locals",
    children: [
      { index: true, Component: OwnLocalsPage },
      {
        path: "local/:id",
        children: [{ index: true, Component: OwnLocalDetailsPage }],
      },
    ],
  },
  { path: "add-local", Component: AddLocalPage },
  {
    path: "current-rents",
    children: [
      { index: true, Component: CurrentOwnerRentsPage },
      { path: "rent/:id", Component: OwnerRentDetailsPage },
    ],
  },
  {
    path: "archival-rents",
    children: [
      { index: true, Component: ArchivalOwnerRentsPage },
      { path: "rent/:id", Component: OwnerRentDetailsPage },
    ],
  },
];
const TenantRoutes: RouteObject[] = [
  { index: true, Component: TenantPage },
  { path: "current-rents", Component: CurrentRentsPage },
  { path: "archival-rents", Component: ArchivalRentsPage },
  { path: "applications", Component: OwnApplicationPage },
  { path: "rents/:id", Component: RentDetailsPage },
  { path: "locals", Component: ActiveLocalsPage },
  { path: "locals/:id", Component: ActiveLocalDetailsPage },
];
const AccountRoutes: RouteObject[] = [{ index: true, Component: MePage }];

export const UnprotectedRoutes: RouteObject[] = [
  {
    Component: BaseMeLayout,
    children: [
      { index: true, Component: LandingPage },
      { path: "/tenant/locals/:id", Component: ActiveLocalDetailsPage },
      { path: "locals", Component: ActiveLocalsPage },
      { path: "/terms", Component: TermsPage },
      { path: "/cookies", Component: CookiesPage },
      { path: "/about", Component: AboutPage },
      { path: "/contact", Component: ContactPage },
    ],
  },
  { path: "/login", Component: LoginPage },
  { path: "/register", Component: RegisterPage },
  { path: "/register-success", Component: RegistrationSuccessPage },
  { path: "/reset-password", Component: ResetPasswordPage },
  { path: "/verify/:token", Component: VerifyAccountPage },
  { path: "/update-email/:token", Component: UpdateEmailPage },
  { path: "/reset-password-form", Component: ResetPasswordForm },
  { path: "/auth/google/callback", Component: Callback },
];

export const ProtectedRoutes: RouteObject[] = [
  { path: "admin", Component: AdminLayout, children: AdminRoutes },
  { path: "owner", Component: OwnerLayout, children: OwnerRoutes },
  { path: "tenant", Component: TenantLayout, children: TenantRoutes },
  { path: "account", Component: AccountLayout, children: AccountRoutes },
];
