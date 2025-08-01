import { FC } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Building2, TrendingUp, Users } from "lucide-react";

const LandingPage: FC = () => {
  const { t } = useTranslation();

  return (
    <Card>
      <div className="min-h-screen bg-gradient-to-br from-background to-muted">
        {/* Hero Section */}
        <div className="container mx-auto px-4 py-16">
          <div className="mb-16 text-center">
            {/* Logo */}
            <div className="mb-8 flex justify-center">
              <div className="flex h-24 w-24 items-center justify-center rounded-full bg-primary shadow-lg">
                <svg
                  width="70"
                  height="70"
                  viewBox="10 5 101.75717 101.75718"
                  className="text-primary-foreground"
                  fill="currentColor"
                >
                  <path d="m 46.997281,31.913393 1.499438,-7.071972 c 0,0 0.583923,-2.164471 1.042842,-3.058573 0.45892,-0.894102 1.321372,-1.463794 1.550832,-1.606218 0.22946,-0.142423 0.989049,-0.474744 1.392584,-0.490569 0.403532,-0.01582 1.622041,-0.07912 2.37372,0.482657 0.751678,0.56178 3.877077,3.014626 3.877077,3.014626 0,0 0.704204,0.672554 1.914803,0.957401 1.210598,0.284846 2.563619,-0.126599 2.563619,-0.126599 0,0 1.440058,-0.838714 2.255036,-1.558743 0.814977,-0.720029 2.23921,-1.946452 3.133312,-2.334159 0.894101,-0.387708 1.234335,-0.522219 2.444933,-0.411445 1.210597,0.110773 2.175911,1.542918 2.175911,1.542918 0,0 0.664642,0.538044 1.392583,3.32321 0.727941,2.785166 1.503357,7.421835 1.503357,7.421835 l 0.110773,1.724904 c 0,0 -2.943414,3.418159 -13.767583,3.639706 C 51.636349,37.58392 46.888907,33.754316 46.888907,33.754316 Z" />
                  <path d="m 45.638746,32.250959 -0.253196,4.90569 c 0,0 4.494244,3.703005 16.236252,3.892902 11.742007,0.189899 16.141305,-3.734655 16.141305,-3.734655 L 77.41496,32.21931 c 0,0 9.653133,1.550831 10.476024,4.779092 0.822888,3.22826 -4.968989,5.918477 -4.968989,5.918477 0,0 -3.924554,2.057226 -6.045078,2.310421 -2.120524,0.253199 -30.668478,-0.03165 -30.668478,-0.03165 0,0 -10.63427,-3.544755 -10.950766,-7.057862 -0.316496,-3.513108 5.222186,-4.684143 5.222186,-4.684143 z" />
                  <path d="m 46.124476,45.475466 0.984705,4.834006 c 0,0 0.257365,1.074222 1.700853,1.387539 l 1.421109,0.02238 c 0,0 0.850427,4.442362 2.182017,5.393497 1.33159,0.951136 0.02238,-0.03357 0.02238,-0.03357 l -0.03357,6.333443 9.16447,5.404688 9.20923,-5.371118 0.04476,-6.400583 c 0,0 2.047738,-1.89108 2.092497,-5.348737 l 1.611336,-0.01119 c 0,0 0.738528,0.05595 1.353969,-1.118983 0.61544,-1.174932 0.917566,-5.035423 0.917566,-5.035423 z" />
                  <path d="m 61.637626,70.183025 -4.066974,5.063937 1.898976,3.528933 -1.424231,11.916081 6.82049,0.0633 -1.171035,-11.900256 1.962277,-3.465634 z" />
                  <path d="m 50.264713,64.453415 -3.670266,3.177913 -17.321855,7.430045 c 0,0 -1.700854,0.850426 -1.92465,3.133153 -0.07291,0.743731 -0.136326,3.525747 -0.188688,6.93892 -0.02575,1.678705 -0.200041,5.655055 2.095357,5.709124 7.267814,0.171196 22.675155,0.04178 22.675155,0.04178 0,0 13.386856,0.01482 17.35244,0.108831 2.257384,0.05352 25.306952,0.0633 25.306952,0.0633 0,0 1.580072,-1.003239 1.624832,-2.972649 0.04476,-1.969414 0.01854,-6.398082 0.01854,-6.398082 0,0 0.581872,-5.326358 -2.551279,-6.848176 -3.133154,-1.521817 -17.008541,-7.16149 -17.008541,-7.16149 l -3.670263,-3.133153 -0.984705,6.758657 c 0,0 -1.118984,7.11673 -1.611336,8.593788 -0.492353,1.477057 -2.279028,10.772169 -3.612781,10.907539 -1.151411,0.116863 -10.220177,-0.02125 -10.220177,-0.02125 0,0 -2.459433,-5.425645 -2.638471,-7.171257 -0.179038,-1.745615 -3.446468,-15.88956 -3.670263,-19.156989 z" />
                </svg>
              </div>
            </div>

            <h1 className="mb-4 text-5xl font-bold text-foreground md:text-6xl">
              {t("landingPage.title")}
            </h1>
            <p className="mb-6 text-xl text-muted-foreground md:text-2xl">
              {t("landingPage.subtitle")}
            </p>
            <p className="mx-auto mb-8 max-w-3xl text-lg text-muted-foreground">
              {t("landingPage.description")}
            </p>

            {/* CTA Buttons */}
            <div className="mb-16 flex flex-col items-center justify-center gap-4 sm:flex-row">
              <Button size="lg" asChild className="px-8 py-6 text-lg">
                <NavLink to="/register">{t("landingPage.cta.signUp")}</NavLink>
              </Button>
              <Button
                size="lg"
                variant="outline"
                asChild
                className="px-8 py-6 text-lg"
              >
                <NavLink to="/locals">{t("landingPage.viewLocals")}</NavLink>
              </Button>
            </div>
          </div>

          {/* Features Section */}
          <div className="mb-16">
            <h2 className="mb-12 text-center text-3xl font-bold md:text-4xl">
              {t("landingPage.features.title")}
            </h2>
            <div className="grid grid-cols-1 gap-8 md:grid-cols-3">
              <Card className="text-center transition-shadow hover:shadow-lg">
                <CardHeader>
                  <div className="mb-4 flex justify-center">
                    <Building2 className="h-12 w-12 text-primary" />
                  </div>
                  <CardTitle className="text-xl">
                    {t("landingPage.features.propertyManagement.title")}
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <CardDescription className="text-base">
                    {t("landingPage.features.propertyManagement.description")}
                  </CardDescription>
                </CardContent>
              </Card>

              <Card className="text-center transition-shadow hover:shadow-lg">
                <CardHeader>
                  <div className="mb-4 flex justify-center">
                    <Users className="h-12 w-12 text-primary" />
                  </div>
                  <CardTitle className="text-xl">
                    {t("landingPage.features.rentTracking.title")}
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <CardDescription className="text-base">
                    {t("landingPage.features.rentTracking.description")}
                  </CardDescription>
                </CardContent>
              </Card>

              <Card className="text-center transition-shadow hover:shadow-lg">
                <CardHeader>
                  <div className="mb-4 flex justify-center">
                    <TrendingUp className="h-12 w-12 text-primary" />
                  </div>
                  <CardTitle className="text-xl">
                    {t("landingPage.features.reporting.title")}
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <CardDescription className="text-base">
                    {t("landingPage.features.reporting.description")}
                  </CardDescription>
                </CardContent>
              </Card>
            </div>
          </div>

          {/* Final CTA Section */}
          <div className="rounded-lg bg-card p-8 text-center shadow-lg">
            <h3 className="mb-4 text-2xl font-bold md:text-3xl">
              {t("landingPage.cta.getStarted")}
            </h3>
            <p className="mb-6 text-lg text-muted-foreground">
              {t("landingPage.description")}
            </p>
            <div className="flex flex-col items-center justify-center gap-4 sm:flex-row">
              <Button size="lg" asChild>
                <NavLink to="/register">{t("landingPage.cta.signUp")}</NavLink>
              </Button>
              <Button size="lg" variant="ghost" asChild>
                <NavLink to="/login">{t("landingPage.cta.login")}</NavLink>
              </Button>
            </div>
          </div>
        </div>
      </div>
    </Card>
  );
};

export default LandingPage;
