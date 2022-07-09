import React from "react";
import MainContentRouter from "./MainContentRouter";
import {CssBaseline} from "@mui/material";
import {ErrorHandler} from "@starwit/react-starwit";
import {useTranslation} from "react-i18next";
import AppConfig from "./AppConfig";
import Navigation from "./commons/navigation/Navigation";
import logo from "./assets/images/logo-white.png";
import {AdapterMoment} from '@mui/x-date-pickers/AdapterMoment';
import {LocalizationProvider} from "@mui/x-date-pickers";


function App() {
    const {t} = useTranslation();
    const {appItems} = AppConfig();

    return (
        <React.Fragment>
            <ErrorHandler>
                <LocalizationProvider dateAdapter={AdapterMoment}>
                    <Navigation menuItems={appItems} title={t("app.baseName")} logo={logo}>
                        <CssBaseline/>
                        <MainContentRouter/>
                    </Navigation>
                </LocalizationProvider>
            </ErrorHandler>
        </React.Fragment>
    );
}

export default App;
