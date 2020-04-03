import * as React from "react";
import {useState} from "react";
import {Button} from "react-bootstrap";
import TrainEditModal from "./TrainEditModal";
import API from "../../utils/API";
import PassengerListModal from "../passenger/PassengerListModal";


const Train = ({...props}) => {
    const {onTrainListChanged} = props;
    const [train, setTrain] = useState(props.train);
    const [isEditModalOpen, setEditModalOpen] = useState(false);
    const [isPassengerModalOpen, setPassengerModalOpen] = useState(false);

    const handleClose = () => {
        setEditModalOpen(false);
    };

    const handlePassengerModalClose = () => {
        setPassengerModalOpen(false);
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
                    <Button
                        onClick={() => setEditModalOpen(true)}
                    >
                        E
                    </Button>
                    <TrainEditModal
                        isOpen={isEditModalOpen}
                        train={train}
                        onClose={handleClose}
                        onTrainUpdated={onTrainUpdate}
                    />
                    <Button
                        onClick={deleteTrain}
                    >
                        D
                    </Button>
                    <Button
                        onClick={onPassengerListOpen}
                    >
                        P
                    </Button>
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