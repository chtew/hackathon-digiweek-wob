import {useMap} from "react-leaflet";
import {latLngBounds} from "leaflet";
import {useEffect} from "react";

function MapCenterer(props) {
    const map = useMap()
    console.log(props)

    function setMarkerBounds(elements) {
        let markerBounds = latLngBounds([]);
        elements.forEach(element => {
            markerBounds.extend([element.latitude, element.longitude])
        });
        return markerBounds;
    }

    useEffect(() => {
        if (props.pointerData && props.pointerData.length >0) {
            map.fitBounds(setMarkerBounds(props.pointerData));
        }

    }, [props.pointerData])

    return null
}
export default MapCenterer;
