import React, {useEffect, useMemo, useState} from "react";
import {
    AppBar, Box,
    Button,
    CircularProgress,
    Container,
    Dialog,
    IconButton,
    Slide,
    Stack,
    TextField,
    Toolbar,
    Typography
} from "@mui/material";
import {useTranslation} from "react-i18next";
import {CircleMarker, MapContainer, TileLayer} from "react-leaflet";
import "./MapMain.css";
import "leaflet/dist/leaflet.css";
import TrafficRecorderRest from "../../services/TrafficRecorderRest";
import Dropzone from "react-dropzone";
import {t} from "i18next";
import TrafficRecordRest from "../../services/TrafficRecordRest";
import {Close} from "@mui/icons-material";
import {TransitionProps} from '@mui/material/transitions';
import moment from "moment";
import {HeatmapLayer} from "react-leaflet-heatmap-layer-v3";


const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement;
    },
    ref: React.Ref<unknown>,
) {
    return <Slide direction="up" ref={ref} {...props} />;
});


function MapMain() {
    const trafficRecorderRest = useMemo(() => new TrafficRecorderRest(), []);
    const [trafficRecorderAll, setTrafficRecorderAll] = useState();
    const [center, setCenter] = useState([52.427183696557591, 10.776275400214885]);
    const [dialogEntity, setDialogEntity] = useState(null);
    const [loadedImage, setLoadedImage] = useState(false);
    const [spanStart, setSpanStart] = useState("2020-05-01");
    const [spanEnd, setSpanEnd] = useState("2020-06-01");
    const {t} = useTranslation();

    useEffect(() => {
        reload();
    }, []);

    useEffect(() => {
        setLoadedImage(false);
    }, [spanStart, spanEnd])

    function reload() {
        trafficRecorderRest.findAll().then(response => {
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
        if (!dialogEntity){
            return null;
        }
        let content = [<img key={"image"}
                            src={"http://localhost:3001/render/d-solo/RY0Euae7z/miv-pro-rekorder?orgId=1&from=" + moment(spanStart).unix() * 1000 + "&to=" + moment(spanEnd).unix() * 1000 + "&theme=light&panelId=9&width=1000&height=500&tz=Europe%2FBerlin&var-externalId=" + dialogEntity?.externalId}
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
                open={Boolean(dialogEntity)}
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
                <Container>
                    <p id="datePicker"></p>
                    <Stack direction={"row"} spacing={2}>
                        <TextField
                            id="start"
                            label="Start"
                            type="date"
                            value={spanStart}
                            sx={{width: 220}}
                            InputLabelProps={{
                                shrink: true,
                            }}
                            onChange={(evnt) => {
                                setSpanStart(evnt.target.value)
                            }}
                        />
                        <TextField
                            id="end"
                            label="Ende"
                            type="date"
                            value={spanEnd}
                            sx={{width: 220}}
                            InputLabelProps={{
                                shrink: true,
                            }}
                            onChange={(evnt) => {
                                setSpanEnd(evnt.target.value)
                            }}

                        />
                    </Stack>

                    {renderLoadedImage()}
                </Container>
            </Dialog>
        )
    }

    function calculatePoints() {
        const data = [];
        trafficRecorderAll?.forEach(element => {
            data.push([element?.latitude, element?.longitude, element?.trafficRecord[0]?.carCount])
        })
        return data;
    }


    return (
        <>
            <MapContainer center={center} zoom={12} maxZoom={19} scrollWheelZoom={true}>
                <HeatmapLayer
                    fitBoundsOnLoad
                    fitBoundsOnUpdate
                    points={calculatePoints()}
                    fillOpacity={1}
                    longitudeExtractor={m => m[1]}
                    latitudeExtractor={m => m[0]}
                    intensityExtractor={m => parseFloat(m[2])}/>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {
                    trafficRecorderAll?.map(element => {
                        return (
                            <>
                                <CircleMarker key={"data" + element.id} center={[element.latitude, element.longitude]}
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
            </MapContainer>
            {renderDialog()}

        </>
    );
}

function UploadCsv() {
    // POST localhost:8081/hackathon/api/trafficrecorder/trafficRecordersJSON
    // POST localhost:8081/hackathon/api/trafficrecord/inductionLoopCsv

    const trafficRecordRest = useMemo(() => new TrafficRecordRest(), []);

    return (
        <>
            <Dropzone
                accept={{'text/csv': ['.csv']}}
                onDrop={accepted => {
                    trafficRecordRest.uploadCsv(accepted).then(console.log);
                }}
            >
                {({getRootProps, getInputProps}) => (
                    <section>
                        <div {...getRootProps()}>
                            <input {...getInputProps()} />
                            <p className="uploadDialog">{t("dropCsv.text")}</p>
                        </div>
                    </section>
                )}
            </Dropzone>
        </>
    );
}

function UploadJSON() {
    const trafficrecorderRest = useMemo(() => new TrafficRecorderRest(), []);

    return (
        <>
            <Dropzone
                accept={{'application/json': ['.json']}}
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
                            <p className="uploadDialog">{t("dropJSON.text")}</p>
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
            <MapMain/>
        </>
    );

    function uploadCsv() {
        return (
            <Button href="#text-buttons">IMPORT CSV</Button>
        );
    }
}

export default CreateMap;
