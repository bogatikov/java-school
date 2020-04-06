import * as React from "react";
import {useState} from "react";
import {Modal, Table} from "react-bootstrap";

const TicketInfo = ({...props}) => {
    const [isOpen, setIsOpen] = useState(true);
    const {ticket} = props;
    console.log(ticket);
    return (
        <Modal show={isOpen} onHide={() => setIsOpen(false)}>
            <Modal.Header>
                Ticket information
            </Modal.Header>
            <Modal.Body>
                <h4>Train info</h4>
                <Table responsive>
                    <tr>
                        <td>Train:</td>
                        <td>
                            <b>
                                {ticket.train.number}
                            </b>
                        </td>
                    </tr>
                    <tr>
                        <td>From:</td>
                        <td>
                            <b>
                                {ticket.from.name}
                            </b>
                        </td>
                    </tr>
                    <tr>
                        <td>To:</td>
                        <td>
                            <b>
                                {ticket.to.name}
                            </b>
                        </td>
                    </tr>
                    <tr>
                        <td>Carriage number:</td>
                        <td>
                            <b>
                                {ticket.carriage.id}
                            </b>
                        </td>
                    </tr>
                </Table>
                <h4>Passenger info</h4>
                <Table responsive>
                    <tr>
                        <td>First Name</td>
                        <td>
                            <b>
                                {ticket.passenger.firstName}
                            </b>
                        </td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td>
                            <b>
                                {ticket.passenger.lastName}
                            </b>
                        </td>
                    </tr>
                    <tr>
                        <td>Birthday</td>
                        <td>
                            <b>
                                {ticket.passenger.birthday}
                            </b>
                        </td>
                    </tr>
                </Table>
            </Modal.Body>
        </Modal>
    );
};

export default TicketInfo;