import * as React from "react";

const Track = ({...props}) => {
    const track = props.track;


    if (!track) {
        return <div></div>
    }
    const stations = [];
    track.forEach(path => {
        const fNode = path.f_node;
        const sNode = path.s_node;
        if (stations.indexOf(fNode) === -1) {
            stations.push(fNode.name);
        }
        if (stations.indexOf(sNode) === -1) {
            stations.push(sNode.name);
        }
    });

    return (
        <div>
            {stations.join(" <-> ")}
        </div>
    );
};

export default Track;