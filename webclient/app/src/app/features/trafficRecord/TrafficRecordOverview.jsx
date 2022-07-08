import {Container, Typography, Button} from "@mui/material";
import React, {useState, useMemo, useEffect} from "react";
import {useTranslation} from "react-i18next";
import {OverviewTable} from "@starwit/react-starwit";
import TrafficRecordRest from "../../services/TrafficRecordRest";
import {useHistory} from "react-router";
import {trafficRecordOverviewFields} from "../../modifiers/TrafficRecordModifier";

function UserOverview() {
    const [selected, setSelected] = useState(undefined);
    const {t} = useTranslation();
    const trafficrecordRest = useMemo(() => new TrafficRecordRest(), []);
    const history = useHistory();
    const [trafficRecordAll, setTrafficRecordAll] = useState();

    useEffect(() => {
        reload();
    }, []);

    function reload() {
        trafficrecordRest.findAll().then(response => {
            setTrafficRecordAll(response.data);
        });
    }

    function goToCreate() {
        history.push("/trafficrecord/create");
    }

    function goToUpdate() {
        if (!!selected) {
            history.push("/trafficrecord/update/" + selected.id);
            setSelected(undefined);
        }
    }

    function handleDelete() {
        if (!!selected) {
            trafficrecordRest.delete(selected.id).then(reload);
            setSelected(undefined);
        }
    }

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>{t("trafficRecord.title")}</Typography>
            <Button onClick={goToCreate} variant="contained" color="secondary">{t("button.create")}</Button>
            <Button onClick={goToUpdate} variant="contained" color="primary" disabled={!selected?.id} >
                {t("button.update")}
            </Button>
            <Button onClick={handleDelete} variant="contained" color="primary" disabled={!selected?.id}>
                {t("button.delete")}
            </Button>
            <OverviewTable
                entities={trafficRecordAll}
                prefix={"trafficRecord"}
                selected={selected}
                onSelect={setSelected}
                fields={trafficRecordOverviewFields}/>
        </Container>
    );
}

export default UserOverview;
