import CrudRest from "./CrudRest";

class TrafficRecorderRest extends CrudRest {
    constructor() {
        super(window.location.pathname + "api/trafficrecorder");
    }
}
export default TrafficRecorderRest;
