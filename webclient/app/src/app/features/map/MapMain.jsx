import {Container, Typography} from "@mui/material";
import React from "react";
import {useTranslation} from "react-i18next";
import { MapContainer, TileLayer, useMap, Marker, Map, Popup } from 'react-leaflet';
import "./MapMain.css";

function componentDidMount() {
    let _map = this.map = L.map(React.useRef(this)).setView([-41.2858, 174.78682], 14);
    L.tileLayer(
        'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="http://openstreetmap.org">OpenStreetMap</a> Contributors',
            maxZoom: 18,
        }).addTo(_map);
}

function MapMain() {
    const coords = [52.427183696557591, 10.776275400214885];

    return (
        <MapContainer center={coords} zoom={12} maxZoom={19} scrollWheelZoom={false}>
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