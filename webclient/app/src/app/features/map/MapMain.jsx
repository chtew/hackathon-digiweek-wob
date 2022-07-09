import React, {useState, useMemo, useEffect} from "react";
import {Button, Container, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import {MapContainer, TileLayer, CircleMarker, Popup} from "react-leaflet";
import "./MapMain.css";
import "leaflet/dist/leaflet.css";
import TrafficRecorderRest from "../../services/TrafficRecorderRest";
import axios from "axios";
import trafficRecorderRest from "../../services/TrafficRecorderRest";
import Dropzone from "react-dropzone";
import {t} from "i18next";
import TrafficRecordRest from "../../services/TrafficRecordRest";

function MapMain() {
    const trafficrecorderRest = useMemo(() => new TrafficRecorderRest(), []);
    const [trafficRecorderAll, setTrafficRecorderAll] = useState();
    const [center, setCenter] = useState([52.427183696557591, 10.776275400214885]);
    const {t} = useTranslation();

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
                                <CircleMarker key={"point" + element.id} center={[element.latitude, element.longitude]} radius={9} color="black" fillOpacity={1} opacity={0} onClick={() => alert("hallooooo")}/>
                                <CircleMarker key={"data" + element.id} center={[element.latitude, element.longitude]} radius={element.trafficRecord.length} color="green" opacity={0} fillOpacity={.5}   eventHandlers={{
                                    click: (e) => {
                                        console.log('marker clicked', e)
                                    },
                                }}>
                                </CircleMarker>
                            </>
                        );
                    })
                }
            </MapContainer>
        </>
    );
}

function UploadCsv() {
    // POST localhost:8081/hackathon/api/trafficrecorder/trafficRecordersJSON
    // POST localhost:8081/hackathon/api/trafficrecord/inductionLoopCsv


/*
    values
        .then((response) => {
            callback(response);
        })
        .catch(error => {
            errorResponse(error);
    });
*/
    const trafficRecordRest = useMemo(() => new TrafficRecordRest(), []);

    return (
        <>
            <Dropzone
                onDrop={accepted => {
                    console.log(accepted);
                    trafficRecordRest.uploadCsv(accepted).then(console.log);
                }}
            >
                {({getRootProps, getInputProps}) => (
                    <section>
                        <div {...getRootProps()}>
                            <input {...getInputProps()} />
                            <p>{t("dropCsv.text")}</p>
                        </div>
                    </section>
                )}
            </Dropzone>
        </>
    );
}

function UploadJSON() {
    // POST localhost:8081/hackathon/api/trafficrecorder/trafficRecordersJSON
    // POST localhost:8081/hackathon/api/trafficrecord/inductionLoopCsv
    const trafficrecorderRest = useMemo(() => new TrafficRecorderRest(), []);


/*
    values
        .then((response) => {
            callback(response);
        })
        .catch(error => {
            errorResponse(error);
        });*/

    return (
        <>
            <Dropzone
                accept="application/json"
                onDrop={accepted => {
                    trafficrecorderRest.uploadJSON(accepted).then(r => console.log(r));
                }}
                maxSize={200000}
                multiple={false}
            >
                {({getRootProps, getInputProps}) => (
                    <section>
                        <div {...getRootProps()}>
                            <input {...getInputProps()} />
                            <p>{t("dropJSON.text")}</p>
                        </div>
                    </section>
                )}
            </Dropzone>
        </>
    );
}

function CreateMap() {
    const {t} = useTranslation();

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("cityMap.title")}
            </Typography>
            <UploadJSON/>
            <UploadCsv/>
            <MapMain/>
        </Container>
    );
}

export default CreateMap;
