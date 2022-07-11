import React, {useMemo} from "react";
import TrafficRecorderRest from "../../../services/TrafficRecorderRest";
import {useTranslation} from "react-i18next";
import Dropzone from "react-dropzone";

function UploadJSON() {
    const trafficRecorderRest = useMemo(() => new TrafficRecorderRest(), []);
    const {t} = useTranslation();


    return (
        <>
            <Dropzone
                accept={{'application/json': ['.json']}}
                onDrop={accepted => {
                    trafficRecorderRest.uploadJSON(accepted).then(r => console.log(r));
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
export default UploadJSON;
