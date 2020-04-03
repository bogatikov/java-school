import {Button, Form, Modal} from "react-bootstrap";
import * as React from "react";
import {useState} from "react";
import {useInput} from "../utils/useInput";
import API from "../../utils/API";

const TrainEditModal = ({...props}) => {

    const {isOpen, onClose, train, onTrainUpdated} = props;
    const [validationErrors, setValidationsErrors] = useState({});

    const handleClose = () => {
        onClose();
    };


    const {value: trainNumber, bind: bindTrainNumber} = useInput(train.number);


    const handleSubmit = (evt) => {
        evt.preventDefault();
        try {
            API.post('/api/v1/train/' + train.id, {
                "number": trainNumber,
            })
                .then(response => {
                    handleClose();
                    onTrainUpdated(response.data);
                    setValidationsErrors({});
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
                    <Modal.Title>Create a train</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="station_name">
                            <Form.Label>Number</Form.Label>
                            <Form.Control type="text" {...bindTrainNumber}
                                          placeholder="Enter the station number"
                                          isInvalid={validationErrors.number !== undefined}
                                          required
                            />
                            <Form.Control.Feedback type="invalid">
                                {validationErrors.number !== undefined ? validationErrors.number : "Please, enter the station number"}
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

export default TrainEditModal;