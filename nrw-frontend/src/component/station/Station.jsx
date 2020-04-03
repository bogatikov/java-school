import * as React from "react";
import {useRef, useState} from "react";
import {Button, Form} from "react-bootstrap";
import Editable from "../utils/Editable";
import StationEditModal from "./StationEditModal";
import API from "../../utils/API";


const Station = ({...props}) => {
    const inputRef = useRef();
    const {onStationListChanged} = props;
    const [station, setStation] = useState(props.station);
    const [name, setName] = useState(station.name);
    const [isEditModalOpen, setEditModalOpen] = useState(false);
    const handleClose = (event) => {
        setEditModalOpen(false);
    };

    const updateStation = (newStation) => {
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
            <td className="col-xs-2">
                <Editable
                    text={name}
                    placeholder="Station name"
                    type="input"
                    childRef={inputRef}
                >
                    <Form.Control
                        size="sm"
                        className="col-xs-2"
                        type="text"
                        name="station_name"
                        ref={inputRef}
                        value={name}
                        onChange={e => setName(e.target.value)}
                    />
                </Editable>
            </td>
            <td>{station.longitude}</td>
            <td>{station.latitude}</td>
            <td>{station.capacity}</td>
            <td>{station.val}</td>
            <td>
                <Button
                    onClick={(evt) => setEditModalOpen(true)}
                >
                    E
                </Button>
                <StationEditModal
                    isOpen={isEditModalOpen}
                    station={station}
                    onClose={handleClose}
                    onStationUpdated={updateStation}
                />
                <Button
                    onClick={deleteStation}
                >
                    D
                </Button>
            </td>
        </tr>
    );
};


export default Station;