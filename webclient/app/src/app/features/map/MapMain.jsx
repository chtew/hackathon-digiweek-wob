import React, {useEffect, useMemo, useState} from "react";
import {AppBar, Button, CircularProgress, Dialog, IconButton, Slide, Toolbar, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import {CircleMarker, MapContainer, TileLayer} from "react-leaflet";
import "./MapMain.css";
import "leaflet/dist/leaflet.css";
import TrafficRecorderRest from "../../services/TrafficRecorderRest";
import {Close} from "@mui/icons-material";
import {TransitionProps} from '@mui/material/transitions';


const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement;
    },
    ref: React.Ref<unknown>,
) {
    return <Slide direction="up" ref={ref} {...props} />;
});

function MapMain() {
    const trafficrecorderRest = useMemo(() => new TrafficRecorderRest(), []);
    const [trafficRecorderAll, setTrafficRecorderAll] = useState();
    const [center, setCenter] = useState([52.427183696557591, 10.776275400214885]);
    const [dialogEntity, setDialogEntity] = useState(null);
    const [loadedImage, setLoadedImage] = useState(false);
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


    function handleOpenDialog(dialog) {
        setDialogEntity(dialog);
        setLoadedImage(false);
    }

    function handleClose() {
        setDialogEntity(null);
        setLoadedImage(false);
    }

    function renderLoadedImage() {
        let content = [<img key={"image"}
                            src={"http://localhost:3001/render/d-solo/RY0Euae7z/miv-pro-rekorder?orgId=1&from=1588284000000&to=1590962399000&theme=light&panelId=3&width=1000&height=500&tz=Europe%2FBerlin&var-externalId=" + dialogEntity?.externalId}
                            onLoad={() => setLoadedImage(true)}/>];

        if (!loadedImage) {
            content.push(<div style={{display: "flex", justifyContent: "center"}}><CircularProgress/></div>)
        }
        return content;
    }

    function renderDialog() {
        return (
            <Dialog
                fullScreen
                open={dialogEntity}
                onClose={handleClose}
                TransitionComponent={Transition}
            >
                <AppBar sx={{position: 'relative'}}>
                    <Toolbar>
                        <IconButton
                            edge="start"
                            color="inherit"
                            onClick={handleClose}
                            aria-label="close"
                        >
                            <Close/>
                        </IconButton>
                        <Typography sx={{ml: 2, flex: 1}} variant="h6" component="div">
                            {dialogEntity?.externalId}
                        </Typography>
                    </Toolbar>
                </AppBar>
                {renderLoadedImage()}
            </Dialog>
        )
    }


    return (
        <>
            <MapContainer center={center} zoom={12} maxZoom={19} scrollWheelZoom={true}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {
                    trafficRecorderAll?.map(element => {
                        return (
                            <>
                                <CircleMarker key={"point" + element.id} center={[element.latitude, element.longitude]}
                                              radius={9} color="black" fillOpacity={1} opacity={0}
                                              onClick={() => alert("hallooooo")}/>
                                <CircleMarker key={"data" + element.id} center={[element.latitude, element.longitude]}
                                              radius={element.trafficRecord.length} color="green" opacity={0}
                                              fillOpacity={.5} eventHandlers={{
                                    click: (e) => {
                                        handleOpenDialog(element)
                                    },
                                }}>
                                </CircleMarker>
                            </>
                        );
                    })
                }
            </MapContainer>
            {renderDialog()}

        </>
    );
}

function UploadCsv() {
    return (
        <Button href="#text-buttons">IMPORT CSV</Button>
    );
}

function CreateMap() {
    const {t} = useTranslation();

    return (
        <>
            <Typography variant={"h2"} gutterBottom>
                {t("cityMap.title")}
            </Typography>
            <UploadCsv/>
            <MapMain/>
        </>
    );
}

export default CreateMap;
