import React from "react";
import {Route} from "react-router-dom";
import TrafficRecorderOverview from "./TrafficRecorderOverview";
import TrafficRecorderDetail from "./TrafficRecorderDetail";

function TrafficRecorderMain() {
    return (
        <>
            <React.Fragment>
                <Route exact path="/trafficrecorder" component={TrafficRecorderOverview}/>
                <Route exact path="/trafficrecorder/create" component={TrafficRecorderDetail}/>
                <Route exact path="/trafficrecorder/update/:id" component={TrafficRecorderDetail}/>
            </React.Fragment>
        </>
    );
}

export default TrafficRecorderMain;
