import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useAuthenticate } from "@/data/useAuthenticate";
import { roleMapping, useUserStore } from "@/store/userStore";
import { isTokenValid } from "@/utils/jwt";
import { zodResolver } from "@hookform/resolvers/zod";
import i18next, { TFunction } from "i18next";
import { FC } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { NavLink, Navigate, useNavigate } from "react-router";
import { z } from "zod";

import LanguageSelector from "@/components/LanguageSelector";
import LoadingButton from "@/components/LoadingButton";
import { useTheme } from "@/components/ThemeProvider";
import { ModeToggle } from "@/components/ui/toggle-theme.tsx";

const getLoginSchema = (t: TFunction) =>
  z.object({
    login: z
      .string()
      .min(
        3,
        t("validation.minLength") +
          " " +
          3 +
          " " +
          t("validation.characters") +
          "."
      )
      .max(
        50,
        t("validation.maxLength") +
          " " +
          50 +
          " " +
          t("validation.characters") +
          "."
      ),
    password: z
      .string()
      .min(
        8,
        t("validation.minLength") +
          " " +
          8 +
          " " +
          t("validation.characters") +
          "."
      )
      .max(
        50,
        t("validation.maxLength") +
          " " +
          50 +
          " " +
          t("validation.characters") +
          "."
      ),
  });

type LoginSchema = z.infer<ReturnType<typeof getLoginSchema>>;

const Login2FaPage: FC = () => {
  const { t } = useTranslation();
  const { token, roles, activeRole, setToken, setRefreshToken } =
    useUserStore();
  const { authenticate, isPending } = useAuthenticate();
  const navigate = useNavigate();
  const { setTheme } = useTheme();

  const form = useForm<LoginSchema>({
    resolver: zodResolver(getLoginSchema(t)),
    values: {
      login: "",
      password: "",
    },
  });

  const onSubmit = form.handleSubmit(async ({ login, password }) => {
    const result = await authenticate({
      login,
      password,
      language: i18next.language,
    });
    setToken(result.token);
    setRefreshToken(result.refreshToken);
    setTheme(result.theme);
    if (roles === undefined) {
      return navigate("/login");
    } else {
      navigate(`/${roleMapping[activeRole!]}`);
    }
  });

  if (token && isTokenValid(token)) {
    return <Navigate to={`/${roleMapping[activeRole!]}`} replace />;
  }

  return (
    <div className="flex min-h-screen items-center justify-center">
      <Form {...form}>
        <form
          onSubmit={onSubmit}
          className="border-1 relative flex w-1/4 min-w-fit flex-col rounded-md bg-card p-7 pt-9 shadow-2xl shadow-shadowColor"
        >
          <div className="flex justify-center">
            <h1 className="w-fit text-3xl font-bold">{t("logoPlaceholder")}</h1>
            <div className="absolute right-14 top-1 w-fit self-end">
              <ModeToggle />
            </div>
            <LanguageSelector />
          </div>
          <h2 className="self-center pb-7 pt-3 text-2xl">
            {t("loginPage.loginHeader")}
          </h2>
          <FormField
            control={form.control}
            name="login"
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("loginPage.login")}</FormLabel>
                <FormControl>
                  <Input {...field} autoComplete="username" />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem className="mb-2">
                <FormLabel>{t("loginPage.password")}</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    type="password"
                    autoComplete="current-password"
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <NavLink
            to={"/reset-password-form"}
            className="self-end pb-2 text-sm text-slate-600 dark:text-slate-400"
          >
            {t("loginPage.forgotPassword")}
          </NavLink>
          <div className="flex flex-col gap-3">
            <LoadingButton
              type="submit"
              isLoading={isPending}
              text={t("loginPage.loginButton")}
            />
            <Button variant="link" asChild className="w-fit self-center">
              <NavLink to={"/register"}>{t("loginPage.register")}</NavLink>
            </Button>
            <Button variant="link" asChild>
              <NavLink to={"/"}>{t("common.backToHomePage")}</NavLink>
            </Button>
          </div>
        </form>
      </Form>
    </div>
  );
};

export default Login2FaPage;
