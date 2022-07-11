import React, {useMemo} from "react";
import TrafficRecordRest from "../../../services/TrafficRecordRest";
import {useTranslation} from "react-i18next";
import Dropzone from "react-dropzone";

function UploadCsv() {
    // POST localhost:8081/hackathon/api/trafficrecorder/trafficRecordersJSON
    // POST localhost:8081/hackathon/api/trafficrecord/inductionLoopCsv

    const trafficRecordRest = useMemo(() => new TrafficRecordRest(), []);
    const {t} = useTranslation();

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
export default UploadCsv;
