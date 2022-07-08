import React from "react";
import {Route} from "react-router-dom";
import TrafficRecordOverview from "./TrafficRecordOverview";
import TrafficRecordDetail from "./TrafficRecordDetail";

function TrafficRecordMain() {
    return (
        <>
            <React.Fragment>
                <Route exact path="/trafficrecord" component={TrafficRecordOverview}/>
                <Route exact path="/trafficrecord/create" component={TrafficRecordDetail}/>
                <Route exact path="/trafficrecord/update/:id" component={TrafficRecordDetail}/>
            </React.Fragment>
        </>
    );
}

export default TrafficRecordMain;
