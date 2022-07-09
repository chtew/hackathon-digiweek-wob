import CrudRest from "./CrudRest";
import axios from "axios";

class TrafficRecordRest extends CrudRest {
    constructor() {
        super(window.location.pathname + "api/trafficrecord");
    }

    findAllWithoutTrafficRecorder(selected) {
        if (isNaN(selected)) {
            return axios.get(this.baseUrl + "/find-without-trafficRecorder/");
        } else {
            return axios.get(this.baseUrl + "/find-without-other-trafficRecorder/" + selected);
        }
    }

    uploadCsv(values) {
        let fd = new FormData();
        const config = { headers: { 'Content-Type': 'multipart/form-data' } };

        fd.append('file',values[0]);

        return axios.post(this.baseUrl+'/inductionLoopCsv', fd, config);
    }
}
export default TrafficRecordRest;
