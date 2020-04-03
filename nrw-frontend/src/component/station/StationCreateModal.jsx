import * as React from "react";
import {useState} from "react";
import {Button, Form, Modal} from "react-bootstrap";
import {useInput} from "../utils/useInput";
import API from "../../utils/API";

const StationCreateModal = ({...props}) => {
    const {isOpen, onClose} = props;
    const [validationErrors, setValidationsErrors] = useState({});

    const handleClose = () => {
        onClose();
    };


    const {value: stationName, bind: bindStationName, reset: resetStationName} = useInput('');
    const {value: stationLongitude, bind: bindStationLongitude, reset: resetStationLongitude} = useInput('');
    const {value: stationVal, bind: bindStationVal, reset: resetStationVal} = useInput('');
    const {value: stationLatitude, bind: bindStationLatitude, reset: resetStationLatitude} = useInput('');
    const {value: stationCapacity, bind: bindStationCapacity, reset: resetStationCapacity} = useInput('');

    const handleSubmit = (evt) => {
        evt.preventDefault();
        try {
            API.post('/api/v1/station/', {
                "name": stationName,
                "longitude": stationLongitude,
                "latitude": stationLatitude,
                "capacity": stationCapacity,
                "val": stationVal
            })
                .then(response => JSON.stringify(response))
                .then(response => {
                    resetStationName();
                    resetStationLongitude();
                    resetStationLatitude();
                    resetStationCapacity();
                    resetStationVal();
                    setValidationsErrors({});
                    console.log("Success: ", response);
                }).catch((error) => {
                    // Error 😨
                    if (error.response) {
                        /*
                         * The request was made and the server responded with a
                         * status code that falls out of the range of 2xx
                         */
                        if (error.response.data.type !== undefined && error.response.data.type === 'validation_error') {
                            console.log("Not valid");
                            setValidationsErrors(error.response.data.errors);
                        } else if (error.response.data.type !== undefined && error.response.data.type === 'message_not_readable') {
                            alert(error.response.data.message);
                        }
                    } else if (error.request) {
                        /*
                         * The request was made but no response was received, `error.request`
                         * is an instance of XMLHttpRequest in the browser and an instance
                         * of http.ClientRequest in Node.js
                         */
                        console.log(error.request);
                    } else {
                        // Something happened in setting up the request and triggered an Error
                        console.log('Error', error.message);
                    }
                });

        } catch (e) {
            console.log(`😱 Axios request failed: ${e}`);
        }

    };
    return (
        <>
            <Modal show={isOpen} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Create a station</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="station_name">
                            <Form.Label>Name</Form.Label>
                            <Form.Control type="text" {...bindStationName}
                                          placeholder="Enter the station name"
                                          isInvalid={validationErrors.name !== undefined}
                                          required
                            />
                            <Form.Control.Feedback type="invalid">
                                {validationErrors.name !== undefined ? validationErrors.name : "Please, enter the station name"}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group controlId="station_longitude">
                            <Form.Label>Longitude</Form.Label>
                            <Form.Control type="text" {...bindStationLongitude}
                                          placeholder="Enter station longitude"
                                          isInvalid={validationErrors.longitude !== undefined}
                                          required
                            />
                            <Form.Control.Feedback type="invalid">
                                {validationErrors.longitude !== undefined ? validationErrors.longitude : "Please, enter the station`s longitude"}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group controlId="station_latitude">
                            <Form.Label>Latitude</Form.Label>
                            <Form.Control type="text" {...bindStationLatitude}
                                          placeholder="Enter station latitude"
                                          isInvalid={validationErrors.latitude !== undefined}
                                          required
                            />
                            <Form.Control.Feedback type="invalid">
                                {validationErrors.latitude !== undefined ? validationErrors.latitude : "Please, enter the station`s latitude"}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group controlId="station_capacity">
                            <Form.Label>Capacity</Form.Label>
                            <Form.Control type="text" {...bindStationCapacity}
                                          placeholder="Enter station capacity"
                                          isInvalid={validationErrors.capacity !== undefined}
                                          required
                            />
                            <Form.Control.Feedback type="invalid">
                                {validationErrors.capacity !== undefined ? validationErrors.capacity : "Please, enter the station`s capacity"}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group controlId="station_capacity">
                            <Form.Label>Value</Form.Label>
                            <Form.Control type="text" {...bindStationVal}
                                          placeholder="Enter station value"
                                          isInvalid={validationErrors.val !== undefined}
                                          required
                            />
                            <Form.Control.Feedback type="invalid">
                                {validationErrors.val !== undefined ? validationErrors.val : "Please, enter the station`s value"}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Save
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </>
    );
};

export default StationCreateModal;