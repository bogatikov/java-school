import * as React from "react";
import {useState} from "react";
import {Button, ButtonGroup} from "react-bootstrap";
import StationEditModal from "./StationEditModal";
import API from "../../utils/API";


const Station = ({...props}) => {
    const {onStationListChanged} = props;
    const [station, setStation] = useState(props.station);
    const [isEditModalOpen, setEditModalOpen] = useState(false);
    const handleClose = () => {
        setEditModalOpen(false);
    };

    const onStationUpdated = (newStation) => {
        setStation(newStation);
    };

    const deleteStation = () => {
        API.delete('/api/v1/station/' + station.id)
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    onStationListChanged(station);
                }
            });
    };
    return (
        <tr>
            <td>{station.name}</td>
            <td>{station.longitude}</td>
            <td>{station.latitude}</td>
            <td>{station.capacity}</td>
            <td>{station.awaitTime}</td>
            <td>
                <StationEditModal
                    isOpen={isEditModalOpen}
                    station={station}
                    onClose={handleClose}
                    onStationUpdated={onStationUpdated}
                />
                <ButtonGroup>
                    <Button
                        onClick={() => setEditModalOpen(true)}
                    >
                        E
                    </Button>
                    <Button
                        variant="danger"
                        onClick={deleteStation}
                    >
                        D
                    </Button>
                </ButtonGroup>
            </td>
        </tr>
    );
};


export default Station;