import React from "react";

function AppConfig(props) {

    const appItems = [
        {title: "trafficRecorder.title", link: "/trafficrecorder/"},
        {title: "trafficRecord.title", link: "/trafficrecord/"},
        {title: "cityMap.title", link: "/map"}
    ];
    return {appItems}
}

export default AppConfig;
