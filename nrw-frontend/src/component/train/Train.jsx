import * as React from "react";
import {useRef, useState} from "react";
import {Button, Form} from "react-bootstrap";
import Editable from "../utils/Editable";
import TrainEditModal from "./TrainEditModal";
import API from "../../utils/API";


const Train = ({...props}) => {
    const inputRef = useRef();
    const {onTrainListChanged} = props;
    const [train, setTrain] = useState(props.train);
    const [isEditModalOpen, setEditModalOpen] = useState(false);
    const handleClose = () => {
        setEditModalOpen(false);
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
    return (
        <tr>
            <td className="col-xs-2">
                <Editable
                    text={train.number}
                    placeholder="Train name"
                    type="input"
                    childRef={inputRef}
                >
                    <Form.Control
                        size="sm"
                        className="col-xs-2"
                        type="text"
                        name="train_number"
                        ref={inputRef}
                        value={train.number}
                        onChange={e => setTrain(e.target.value)}
                    />
                </Editable>
            </td>
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
            </td>
        </tr>
    );
};


export default Train;