import {Container, Typography} from "@mui/material";
import React from "react";
import {useTranslation} from "react-i18next";
import { MapContainer, TileLayer, useMap, Marker, Popup } from 'react-leaflet';
import "./MapMain.css";
import 'leaflet/dist/leaflet.css';

function MapMain() {
    const coords = [52.427183696557591, 10.776275400214885];

    return (
        <>
        <MapContainer center={coords} zoom={12} maxZoom={19} scrollWheelZoom={false} >
            <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <Marker position={coords}>
                <Popup>
                    A pretty CSS3 popup. <br /> Easily customizable.
                </Popup>
            </Marker>
        </MapContainer>
            </>
    );
}

function CreateMap() {
    const {t} = useTranslation();

    return (
        <Container>
                <Typography variant={"h2"} gutterBottom>
                    {t("ein titel")}
                </Typography>
                {t("der inhalt")}

                <MapMain />

        </Container>
    );
}

export default CreateMap;