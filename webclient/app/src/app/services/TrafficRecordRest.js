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
}
export default TrafficRecordRest;
