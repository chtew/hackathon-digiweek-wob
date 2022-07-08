import React, {useMemo, useEffect} from "react";
import {useParams} from "react-router";
import {useImmer} from "use-immer";
import TrafficRecorderRest from "../../services/TrafficRecorderRest";
import TrafficRecordRest from "../../services/TrafficRecordRest";
import {
    entityDefault,
    entityFields
} from "../../modifiers/TrafficRecorderModifier";
import {EntityDetail, addSelectLists} from "@starwit/react-starwit";

function TrafficRecorderDetail() {
    const [entity, setEntity] = useImmer(entityDefault);
    const [fields, setFields] = useImmer(entityFields);
    const entityRest = useMemo(() => new TrafficRecorderRest(), []);
    const trafficrecordRest = useMemo(() => new TrafficRecordRest(), []);
    const {id} = useParams();

    useEffect(() => {
        reloadSelectLists();
    }, [id]);

    function reloadSelectLists() {
        const selectLists = [];
        const functions = [
            trafficrecordRest.findAllWithoutTrafficRecorder(id)
        ];
        Promise.all(functions).then(values => {
            selectLists.push({name: "trafficRecord", data: values[0].data});
            if (id) {
                entityRest.findById(id).then(response => {
                    setEntity(response.data);
                    addSelectLists(response.data, fields, setFields, selectLists);
                });
            } else {
                addSelectLists(entity, fields, setFields, selectLists);
            }
        });
    }

    return (
        <>
            <EntityDetail
                id={id}
                entity={entity}
                setEntity={setEntity}
                fields={fields}
                setFields={setFields}
                entityRest={entityRest}
                prefix="trafficRecorder"
            />
        </>

    );
}

export default TrafficRecorderDetail;
