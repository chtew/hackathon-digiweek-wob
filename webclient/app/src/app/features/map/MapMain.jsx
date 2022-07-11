import React from "react";
import {Box, Stack, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import "./MapMain.css";
import "leaflet/dist/leaflet.css";
import UploadJSON from "./UploadJson/UploadJson";
import UploadCsv from "./UploadCsv/UploadCsv";
import MapView from "./Map/MapView";


function MapMain() {
    const {t} = useTranslation();

    return (
        <>
            <Typography variant={"h2"} gutterBottom>
                {t("cityMap.title")}
            </Typography>
            <Stack direction={"row"}>
                <Box>
                    <UploadJSON/>
                </Box>
                <Box>
                    <UploadCsv/>
                </Box>
            </Stack>
            <MapView/>
        </>
    );
}

export default MapMain;
