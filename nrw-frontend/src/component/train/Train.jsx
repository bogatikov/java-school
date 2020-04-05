import * as React from "react";
import {useState} from "react";
import {Button, ButtonGroup} from "react-bootstrap";
import TrainEditModal from "./TrainEditModal";
import API from "../../utils/API";
import PassengerListModal from "../passenger/PassengerListModal";
import BuyTicketModal from "../ticket/BuyTicketModal";


const Train = ({...props}) => {
    const {onTrainListChanged} = props;
    const [train, setTrain] = useState(props.train);
    const [isEditModalOpen, setEditModalOpen] = useState(false);
    const [isPassengerModalOpen, setPassengerModalOpen] = useState(false);
    const [isTicketPurchaseModalOpen, setTicketPurchaseModalOpen] = useState(false);

    const handleEditModalClose = () => {
        setEditModalOpen(false);
    };
    const handleTicketPurchaseClose = () => {
        setTicketPurchaseModalOpen(false);
    };

    const handlePassengerModalClose = () => {
        setPassengerModalOpen(false);
    };

    const onTicketPurchaseModalOpen = () => {
        setTicketPurchaseModalOpen(true);
    };

    const onTrainUpdate = (newTrain) => {
        setTrain(newTrain);
    };

    const deleteTrain = () => {
        API.delete('/api/v1/train/' + train.id)
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    onTrainListChanged(train);
                }
            });
    };

    const onPassengerListOpen = () => {
        setPassengerModalOpen(true);
    };

    return (
        <>
            <tr>
                <td>{train.number}</td>
                <td>{train.from ? train.from.name : "NOPE"}</td>
                <td>{train.to ? train.to.name : "NOPE"}</td>
                <td>{train.trainState}</td>
                <td>{train.direction}</td>
                <td>
                    <ButtonGroup>
                        <Button
                            onClick={() => setEditModalOpen(true)}
                        >
                            E
                        </Button>
                        <Button
                            variant={"danger"}
                            onClick={deleteTrain}
                        >
                            D
                        </Button>
                        <Button
                            variant={"secondary"}
                            onClick={onPassengerListOpen}
                        >
                            P
                        </Button>
                        <Button
                            variant={"warning"}
                            onClick={onTicketPurchaseModalOpen}
                        >
                            T
                        </Button>
                    </ButtonGroup>
                    {isTicketPurchaseModalOpen ? <BuyTicketModal
                        trainId={train.id}
                        onClose={handleTicketPurchaseClose}
                    /> : null}
                    <TrainEditModal
                        isOpen={isEditModalOpen}
                        train={train}
                        onClose={handleEditModalClose}
                        onTrainUpdated={onTrainUpdate}
                    />
                </td>
            </tr>
            <PassengerListModal
                isOpen={isPassengerModalOpen}
                train={train}
                onClose={handlePassengerModalClose}
            />
        </>
    );
};


export default Train;