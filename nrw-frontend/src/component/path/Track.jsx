import * as React from "react";

const Track = ({...props}) => {
    const track = props.track;


    if (!track || track.length === 0) {
        return <div></div>
    }
    const stations = [];
    track.forEach(path => {
        stations.push(<> {path.f_node.name} <br />{path.s_node.name}</>);
    });

    return (
        <div>
            {stations}
        </div>
    );
};

export default Track;