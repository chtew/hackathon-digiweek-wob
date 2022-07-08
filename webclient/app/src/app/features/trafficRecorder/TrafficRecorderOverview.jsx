import {Container, Typography, Button} from "@mui/material";
import React, {useState, useMemo, useEffect} from "react";
import {useTranslation} from "react-i18next";
import {OverviewTable} from "@starwit/react-starwit";
import TrafficRecorderRest from "../../services/TrafficRecorderRest";
import {useHistory} from "react-router";
import {trafficRecorderOverviewFields} from "../../modifiers/TrafficRecorderModifier";

function UserOverview() {
    const [selected, setSelected] = useState(undefined);
    const {t} = useTranslation();
    const trafficrecorderRest = useMemo(() => new TrafficRecorderRest(), []);
    const history = useHistory();
    const [trafficRecorderAll, setTrafficRecorderAll] = useState();

    useEffect(() => {
        reload();
    }, []);

    function reload() {
        trafficrecorderRest.findAll().then(response => {
            setTrafficRecorderAll(response.data);
        });
    }

    function goToCreate() {
        history.push("/trafficrecorder/create");
    }

    function goToUpdate() {
        if (!!selected) {
            history.push("/trafficrecorder/update/" + selected.id);
            setSelected(undefined);
        }
    }

    function handleDelete() {
        if (!!selected) {
            trafficrecorderRest.delete(selected.id).then(reload);
            setSelected(undefined);
        }
    }

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>{t("trafficRecorder.title")}</Typography>
            <Button onClick={goToCreate} variant="contained" color="secondary">{t("button.create")}</Button>
            <Button onClick={goToUpdate} variant="contained" color="primary" disabled={!selected?.id} >
                {t("button.update")}
            </Button>
            <Button onClick={handleDelete} variant="contained" color="primary" disabled={!selected?.id}>
                {t("button.delete")}
            </Button>
            <OverviewTable
                entities={trafficRecorderAll}
                prefix={"trafficRecorder"}
                selected={selected}
                onSelect={setSelected}
                fields={trafficRecorderOverviewFields}/>
        </Container>
    );
}

export default UserOverview;
