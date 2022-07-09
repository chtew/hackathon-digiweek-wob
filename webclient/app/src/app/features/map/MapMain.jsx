import React, {useState, useMemo, useEffect} from "react";
import {Container, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import {MapContainer, TileLayer, CircleMarker, Popup} from "react-leaflet";
import "./MapMain.css";
import "leaflet/dist/leaflet.css";
import TrafficRecorderRest from "../../services/TrafficRecorderRest";

function MapMain() {
    const trafficrecorderRest = useMemo(() => new TrafficRecorderRest(), []);
    const [trafficRecorderAll, setTrafficRecorderAll] = useState();
    const [center, setCenter] = useState([52.427183696557591, 10.776275400214885]);

    useEffect(() => {
        reload();
    }, []);

    function reload() {
        trafficrecorderRest.findAll().then(response => {
            setTrafficRecorderAll(response.data);
            if (!!response.data && !!response.data[0].latitude && !!response.data[0].longitude) {
                setCenter([response.data[0].latitude, response.data[0].longitude]);
            }
        });
    }

    return (
        <>
            <MapContainer center={center} zoom={12} maxZoom={19} scrollWheelZoom={true} >
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {
                    trafficRecorderAll?.map(element => {
                        return (
                            <>
                                <CircleMarker key={"point" + element.id} center={[element.latitude, element.longitude]} radius={9} color="black" fillOpacity={1} opacity={0}/>
                                <CircleMarker key={element.id} center={[element.latitude, element.longitude]} radius={element.trafficRecord.length} color="green" opacity={0} fillOpacity={.5}>
                                    <Popup>
                                    Latitude: {element.latitude} , Longitude: {element.longitude}
                                    </Popup>
                                </CircleMarker>
                            </>
                        );
                    })
                }
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
