import * as React from "react";
import {Modal} from "react-bootstrap";


const BuyTicketModal = () => {


    return (
        <Modal show={true}>
            <Modal.Header>
                Buy ticket on train TRAIN NUMBER HERE
            </Modal.Header>
            <Modal.Body>
                from
                to
                firstName
                lastName
                birthday
            </Modal.Body>
        </Modal>
    );
};

export default BuyTicketModal;