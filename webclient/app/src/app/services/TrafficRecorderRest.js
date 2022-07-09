import CrudRest from "./CrudRest";
import axios from "axios";

class TrafficRecorderRest extends CrudRest {
    constructor() {
        super(window.location.pathname + "api/trafficrecorder");
    }

    uploadJSON(values) {
        let fd = new FormData();
        const config = { headers: { 'Content-Type': 'multipart/form-data' } };

        fd.append('file',values[0]);

        return axios.post(window.location.pathname + 'api/trafficrecorder/trafficRecordersJSON', fd, config);
    }
}
export default TrafficRecorderRest;
