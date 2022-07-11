import React from "react";
import {Route, Switch} from "react-router-dom";
import TrafficRecorderMain from "./features/trafficRecorder/TrafficRecorderMain";
import TrafficRecordMain from "./features/trafficRecord/TrafficRecordMain";
import Home from "./features/home/Home";
import Map from "./features/map/MapMain";

function MainContentRouter() {
    return (
        <>
            <Switch>
                <Route path={"/trafficrecorder"} component={TrafficRecorderMain}/>
                <Route path={"/trafficrecord"} component={TrafficRecordMain}/>
                <Route path={"/map"} component={Map}/>
            </Switch>
            <Route exact path={"/"} component={Home}/>
            {/*<Route path="/logout" component={() => {
                window.location.href = window.location.pathname + "api/user/logout";
                return null;
            }}/>*/}
        </>
    );
}

export default MainContentRouter;
