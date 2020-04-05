import * as React from "react";
import {useEffect, useState} from "react";
import {Button, Form, Modal} from "react-bootstrap";
import {useInput} from "../utils/useInput";
import API from "../../utils/API";
import Track from "../path/Track";

const BuyTicketModal = ({...props}) => {

    const {trainId, onClose} = props;
    const [train, setTrain] = useState({});
    const [validationErrors, setValidationsErrors] = useState({});
    const [fromId, setFromId] = useState(0);
    const [toId, setToId] = useState(0);

    const {value: firstName, bind: bindFirstName} = useInput("");
    const {value: lastName, bind: bindLastName} = useInput("");
    const {value: birthday, bind: bindBirthday} = useInput("");

    const handleSubmit = (evt) => {
        evt.preventDefault();
        try {
            console.log("trainID" + train.id);
            console.log("fromStationID" + fromId);
            console.log("toStationID" + toId);
            console.log("firstName" + firstName);
            console.log("lastName" + lastName);
            console.log("birthday " + birthday);
            API.post('/api/v1/ticket/', {
                "trainID": train.id,
                "fromStationID": fromId,
                "toStationID": toId,
                "firstName": firstName,
                "lastName": lastName,
                "birthday": birthday
            })
                .then(response => {
                    setValidationsErrors({});
                    console.log(response.data);
                }).catch((error) => {
                // Error 😨
                console.log(error);
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
    const handleClose = () => {
        onClose();
    };

    useEffect(() => {
        API.get("/api/v1/train/" + trainId)
            .then(response => {
                setTrain(response.data);
            });
    }, []);


    if (!train.track) {
        return <div></div>;
    }


    const stationsIds = new Set();
    train.track.forEach(path => {
        stationsIds.add(path.f_node.id);
        stationsIds.add(path.s_node.id);
    });


    const getStationById = (id) => {
        let res = {};
        train.track.forEach(path => {
            if (path.f_node.id === id) {
                res = path.f_node;
                return res;
            }
            if (path.s_node.id === id) {
                res = path.s_node;
                return res;
            }
        });
        return res;
    };
    const stations = [];
    stationsIds.forEach(id => {
        const station = getStationById(id);
        stations.push(<option key={id} value={id}>{station.name}</option>);
    });


    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header>
                Buy ticket on train {train.number}
            </Modal.Header>
            <Modal.Body>
                <Track
                    track={train.track}
                />
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="exampleForm.ControlSelect1">
                        <Form.Label>From</Form.Label>
                        <Form.Control as="select" onChange={v => setFromId(v.target.value)}>
                            {stations}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group controlId="exampleForm.ControlSelect1">
                        <Form.Label>To</Form.Label>
                        <Form.Control as="select" onChange={v => setToId(v.target.value)}>
                            {stations}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Control type="text" {...bindFirstName}
                                      placeholder="Enter last name"
                                      isInvalid={validationErrors.firstName !== undefined}
                                      required
                        />
                        <Form.Control.Feedback type="invalid">
                            {validationErrors.firstName !== undefined ? validationErrors.firstName : "Please, enter the first name"}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                        <Form.Control type="text" {...bindLastName}
                                      placeholder="Enter last name"
                                      isInvalid={validationErrors.lastName !== undefined}
                                      required
                        />
                        <Form.Control.Feedback type="invalid">
                            {validationErrors.lastName !== undefined ? validationErrors.lastName : "Please, enter the last name"}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group>
                        <Form.Control type="text" {...bindBirthday}
                                      placeholder="30/12/1990"
                                      isInvalid={validationErrors.birthday !== undefined}
                                      required
                        />
                        <Form.Control.Feedback type="invalid">
                            {validationErrors.birthday !== undefined ? validationErrors.birthday : "Please, enter the birthday"}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Button variant="success" type="submit">
                        Buy
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default BuyTicketModal;