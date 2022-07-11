import {
    AppBar,
    Box,
    Button, CircularProgress,
    Container,
    Dialog,
    Grid,
    IconButton, Slide,
    Stack,
    TextField,
    Toolbar,
    Typography
} from "@mui/material";
import {ArrowRight, Close, KeyboardArrowLeft, KeyboardArrowRight, LocationCity, NextPlan} from "@mui/icons-material";
import React, {useEffect, useMemo, useState} from "react";
import TrafficRecorderRest from "../../../../services/TrafficRecorderRest";
import {useTranslation} from "react-i18next";
import {TransitionProps} from "@mui/material/transitions";
import moment from "moment";


const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement;
    },
    ref: React.Ref<unknown>,
) {
    return <Slide direction="up" ref={ref} {...props} />;
});

function TrafficCounterDialog(props) {

    const [neighbor ,setNeighbor] = useState(null);
    const [spanStart, setSpanStart] = useState("2020-05-01");
    const [spanEnd, setSpanEnd] = useState("2020-06-01");
    const [loadedImage, setLoadedImage] = useState(false);
    const trafficRecorderRest = useMemo(() => new TrafficRecorderRest(), []);

    const {dialogEntity, handleOpenDialog, handleClose, trafficRecorderAll} = props;
    const {t} = useTranslation();

    useEffect(() => {
        setLoadedImage(false);
    }, [spanStart, spanEnd])

    function findTrafficData(id) {
        return trafficRecorderAll.find(trafficRecorder => {
            return trafficRecorder.externalId === id
        })
    }

    function renderLoadedImage() {
        if (!dialogEntity) {
            return null;
        }
        let content = [<img key={"image"}
                            src={"http://localhost:3001/render/d-solo/RY0Euae7z/miv-pro-rekorder?orgId=1&from=" + moment(spanStart).unix() * 1000 + "&to=" + moment(spanEnd).unix() * 1000 + "&theme=light&panelId=9&width=1000&height=500&tz=Europe%2FBerlin&var-externalId=" + dialogEntity?.externalId}
                            onLoad={() => setLoadedImage(true)} onError={() => setLoadedImage(true)}/>];

        if (!loadedImage) {
            content.push(<div style={{display: "flex", justifyContent: "center"}}><CircularProgress/></div>)
        }
        return content;
    }

    useEffect(() => {
        let neighbor;
        if (dialogEntity?.neighbor) {
            neighbor = findTrafficData(dialogEntity?.neighbor);
        }
        setNeighbor(neighbor);
        setLoadedImage(false);
    },[dialogEntity])


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
                    <Typography sx={{ml: 2, flex: 1}} variant="h6" component="div" noWrap>
                        {dialogEntity?.location} ({dialogEntity?.externalId})
                    </Typography>
                </Toolbar>
            </AppBar>
            <Container>
                <Stack direction={"row"} spacing={2} p={5}
                       style={{display: "flex", alignItems: "center", justifyContent: "center"}}>
                    <TextField
                        id="start"
                        label="Start"
                        type="date"
                        value={spanStart}
                        sx={{width: 220}}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        fullWidth
                        onChange={(evnt) => {
                            setSpanStart(evnt.target.value)
                        }}
                    />
                    <ArrowRight/>
                    <TextField
                        fullWidth
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
                <Box style={{
                    display: "flex",
                    justifyContent: "center",
                    flexDirection: "column",
                    alignItems: "center"
                }}>
                    {renderLoadedImage()}
                </Box>
                <Grid container>
                    <Grid item xs={6} style={{display: "flex", justifyContent: "center"}}>
                        <Stack direction={"row"} spacing={5} style={{display: "flex", alignItems: "center"}}>
                            <Box style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                                <LocationCity fontSize={"large"}/>
                                <Typography variant={"body1"}>{t("travelDirection")}</Typography>
                            </Box>
                            {dialogEntity?.cityDirection === "in" ? <KeyboardArrowLeft fontSize={"large"}/> :
                                <KeyboardArrowRight fontSize={"large"}/>}

                        </Stack>
                    </Grid>
                    <Grid item xs={6} style={{display: "flex", justifyContent: "center"}}>
                        <Stack direction={"row"} spacing={5} style={{display: "flex", alignItems: "center"}}>

                            <Box style={{display: "flex", flexDirection: "column", alignItems: "center"}} mr={5}>
                                <NextPlan fontSize={"large"}/>
                                <Typography variant={"body1"}>{t("neighbor")}</Typography>
                            </Box>
                            <Button
                                onClick={() => {
                                    handleOpenDialog(neighbor)
                                }}
                                style={{width: "100% !important"}}
                                disabled={!dialogEntity?.neighbor}
                                variant={"contained"}
                                fullWidth
                            >
                                {t("button.open")}
                            </Button>
                        </Stack>
                    </Grid>
                </Grid>
            </Container>
        </Dialog>
    )
}

export default TrafficCounterDialog;
