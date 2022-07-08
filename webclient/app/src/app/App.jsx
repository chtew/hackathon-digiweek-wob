import React from "react";
import MainContentRouter from "./MainContentRouter";
import {CssBaseline} from "@mui/material";
import {ErrorHandler} from "@starwit/react-starwit";
import AppHeader from "./commons/appHeader/AppHeader";
import {useTranslation} from "react-i18next";
import {appItems} from "./AppConfig";
import Navigation from "./commons/navigation/Navigation";
import logo from "./assets/images/logo-white.png";


function App() {
    const {t} = useTranslation();

    return (
        <React.Fragment>
            <ErrorHandler>
                <Navigation menuItems={appItems} title={t("app.baseName")} logo={logo}>
                    <CssBaseline/>
                    <MainContentRouter/>
                </Navigation>
            </ErrorHandler>
        </React.Fragment>
    );
}

export default App;
