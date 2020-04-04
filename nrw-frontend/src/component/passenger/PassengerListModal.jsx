import * as React from "react";
import {useEffect, useState} from "react";
import {Modal, Table} from "react-bootstrap";
import API from "../../utils/API";

const PassengerListModal = ({...props}) => {

    const {train} = props;
    const [carriages, setCarriages] = useState([]);
    const {isOpen, onClose} = props;

    useEffect(() => {
        dataLoad();
    }, []);

    async function dataLoad() {
        await API.get('/api/v1/carriage/' + train.id)
            .then(response => {
                setCarriages(response.data);
            })
            .catch(reason => {
                console.log(reason);
            });
    }

    const rows = [];

    carriages.forEach(carriage => {
        const passengers = carriage.passengers;
        const passengersRows = [];
        passengers.forEach(passenger => {
            const date = new Date(passenger.birthday.epochSecond * 1000);
            const birthday = date.getFullYear() + '/' + date.getMonth() + '/' + date.getDay();
            passengersRows.push(
                <tr key={passenger.id}>
                    <td>{passenger.firstName}</td>
                    <td>{passenger.lastName}</td>
                    <td>{birthday}</td>
                </tr>
            );
        });
        rows.push(
            <>
                <h5>List of passengers from carriage number {carriage.id}</h5>

                <Table responsive>
                    <tr>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Birthday</th>
                    </tr>
                    {passengersRows}
                </Table>
                <hr/>
            </>
        );
    });

    return (
        <Modal show={isOpen} onHide={onClose}>
            <Modal.Header closeButton>
                <Modal.Title>The passenger list of <b>{train.number}</b> train</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {rows}

            </Modal.Body>
        </Modal>
    );
};


export default PassengerListModal;