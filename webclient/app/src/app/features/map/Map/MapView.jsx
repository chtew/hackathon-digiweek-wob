import React, {useEffect, useMemo, useState} from "react";
import {Box, Slider, Typography} from "@mui/material";
import TrafficRecorderRest from "../../../services/TrafficRecorderRest";
import {useTranslation} from "react-i18next";
import moment from "moment/moment";
import {CircleMarker, MapContainer, TileLayer} from "react-leaflet";
import {HeatmapLayer} from "react-leaflet-heatmap-layer-v3";
import MapCenterer from "../MapCenterer/MapCenterer";
import TrafficCounterDialog from "./TrafficCounterDialog/TrafficCounterDialog";


function MapView() {
    const trafficRecorderRest = useMemo(() => new TrafficRecorderRest(), []);
    const [trafficRecorderAll, setTrafficRecorderAll] = useState();
    const [center, setCenter] = useState({
        lat: 52.427183696557591,
        lng: 10.776275400214885,
        zoom: 13
    });
    const [dialogEntity, setDialogEntity] = useState(null);
    const [steps, setSteps] = useState(null);
    const [sliderValue, setSliderValue] = useState(null);
    const [trafficRecorderMap, setTrafficRecorderMap] = useState(null);

    const {t} = useTranslation();

    useEffect(() => {
        reload();
    }, []);


    function calculateSteps(records) {
        const steps = [];
        records?.forEach(element => {
            element.trafficRecord?.forEach(traffic => {
                if (!steps.find(trafficFind => trafficFind.value === traffic.recordDate)) {
                    steps.push({value: traffic.recordDate, label: moment(traffic.recordDate).toISOString()});
                }
            })
        })
        steps.sort((step1, step2) => {
            if (step1.value < step2.value) {
                return -1;
            }
            if (step1.value > step2.value) {
                return 1;
            }
        });
        setSliderValue(steps[0].value);
        return steps
    }

    function reload() {
        trafficRecorderRest.findAll().then(response => {
            setTrafficRecorderAll(response.data);
            if (!!response.data && !!response.data[0].latitude && !!response.data[0].longitude) {
                setCenter([response.data[0].latitude, response.data[0].longitude]);
            }
            let dataBlubb = JSON.parse(JSON.stringify(response.data))
            dataBlubb.map(trafficRecord1 => {
                let traMap = new Map();
                trafficRecord1.trafficRecord.forEach((tr) => {
                    traMap.set(tr.recordDate, tr.carCount);
                })
                trafficRecord1.trafficRecord = traMap;
                return trafficRecord1
            })

            setTrafficRecorderMap(dataBlubb)
            setSteps(calculateSteps(response.data));
        });
    }


    function handleOpenDialog(dialog) {
        console.log("openedDialog", dialog)
        setDialogEntity(dialog);
    }

    function handleClose() {
        setDialogEntity(null);
    }


    function calculatePoints() {
        const data = [];
        trafficRecorderMap?.forEach(element => {
            if (element.trafficRecord.has(sliderValue)) {
                data.push([element?.latitude, element?.longitude, element.trafficRecord.get(sliderValue)])
            }
        })
        return data;
    }


    function valueText(value) {
        return value;
    }

    function valueLabelFormat(value) {
        return moment(value).format("DD.MM.YYYY");
    }

    function renderSlider() {
        if (steps) {

            return (
                <Box pt={5}>
                    <Slider
                        defaultValue={steps[0].value}
                        min={steps[0].value}
                        getAriaValueText={valueText}
                        valueLabelFormat={valueLabelFormat}
                        max={steps[steps.length - 1].value}
                        valueLabelDisplay="auto"
                        value={sliderValue}
                        step={null}
                        marks={steps}
                        onChange={(value) => {
                            setSliderValue(value.target.value)
                        }}
                    />
                </Box>
            )
        }

        return <Typography variant={"body1"}>Loading</Typography>
    }

    return (
        <>
            <MapContainer center={center} zoom={15} maxZoom={18} scrollWheelZoom={true}>
                <MapCenterer pointerData={trafficRecorderAll}/>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {
                    trafficRecorderAll?.map(element => {
                        if (element.cityDirection === "inVW" || element.cityDirection === "outVW") {
                            return;
                        }
                        return (
                            <>
                                <CircleMarker key={"data" + element.id}
                                              center={[element.latitude, element.longitude]}
                                              radius={12} color="black" opacity={0}
                                              fillOpacity={.2} eventHandlers={{
                                    click: (e) => {
                                        handleOpenDialog(element)
                                    },
                                }}>
                                </CircleMarker>

                            </>
                        );
                    })
                }

                <HeatmapLayer
                    maxZoom={18}
                    zoom={15}
                    fitBoundsOnLoad
                    fitBoundsOnUpdate
                    fillOpacity={1}
                    points={calculatePoints()}
                    longitudeExtractor={m => m[1]}
                    latitudeExtractor={m => m[0]}
                    intensityExtractor={m => parseFloat(m[2])}
                    radius={40}
                    blur={50}
                    local
                />
            </MapContainer>
            {renderSlider()}

            <TrafficCounterDialog
                dialogEntity={dialogEntity}
                handleOpenDialog={handleOpenDialog}
                handleClose={handleClose}
                trafficRecorderAll={trafficRecorderAll}
            />
        </>
    );
}

export default MapView;
