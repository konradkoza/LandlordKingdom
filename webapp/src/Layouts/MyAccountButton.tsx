import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useMeQuery } from "@/data/meQueries";
import { cn } from "@/lib/utils";
import { useUserStore } from "@/store/userStore";
import { FC, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { VscAccount } from "react-icons/vsc";
import { NavLink, useLocation, useNavigate } from "react-router-dom";
import { IoMdArrowDropdown } from "react-icons/io";
import { UserResponse } from "@/types/user/UserResponseType";

type MyAccountButtonProps = {
  hover: string;
};

const MyAccountButton: FC<MyAccountButtonProps> = ({ hover }) => {
  const { t } = useTranslation();
  const userStore = useUserStore();
  const navigate = useNavigate();
  const [user, setUser] = useState<UserResponse | undefined>(undefined);
  const { pathname } = useLocation();
  const { id } = useUserStore();
  const { data } = useMeQuery(id !== undefined && id !== null);

  useEffect(() => {
    if (userStore.id) {
      if (data) {
        setUser(data.data);
      }
    }
  }, [userStore.id]);

  const handleLoginButtonClick = () => {
    userStore.clearToken();
    userStore.clearRefreshToken();
    navigate("/login");
  };
  return userStore.id ? (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="ghost" className={cn("px-2 py-1", hover)}>
          <VscAccount className="mr-2 h-4 w-4" />
          {user?.firstName} {user?.lastName}
          <IoMdArrowDropdown />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        <DropdownMenuLabel asChild>
          <DropdownMenuItem
            onClick={() => navigate(`/account?origin=${pathname}`)}
          >
            {t("navLinks.account")}
          </DropdownMenuItem>
        </DropdownMenuLabel>
        <DropdownMenuItem onClick={handleLoginButtonClick}>
          {t("navLinks.signOut")}
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  ) : (
    <div className="flex flex-wrap gap-2">
      <Button asChild>
        <NavLink to="/login">{t("homePage.signIn")}</NavLink>
      </Button>
      <Button variant="outline" asChild>
        <NavLink to="/register">{t("homePage.signUp")}</NavLink>
      </Button>
    </div>
  );
};

export default MyAccountButton;
