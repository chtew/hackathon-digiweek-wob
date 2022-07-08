import {Container, Typography} from "@mui/material";
import React from "react";
import {useTranslation} from "react-i18next";

function Home() {
    const {t} = useTranslation();

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("home.title")}
            </Typography>
            {t("home.welcome")}
        </Container>
    );
}

export default Home;
