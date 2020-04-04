import * as React from "react";
import {Button, ButtonGroup} from "react-bootstrap";
import {useState} from "react";
import API from "../../utils/API";
import TrainEditModal from "../train/TrainEditModal";
import PassengerListModal from "../passenger/PassengerListModal";

const Path = ({...props}) => {
    const {onPathListChanged} = props;
    const [path, setPath] = useState(props.path);
    const [isEditModalOpen, setEditModalOpen] = useState(false);

    const handleClose = () => {
        setEditModalOpen(false);
    };

    const onPathUpdate = (newPath) => {
        setPath(newPath);
    };

    const deletePath = () => {
        API.delete('/api/v1/path/' + path.id)
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    onPathListChanged(path);
                }
            });
    };

    return (
        <>
            <tr>
                <td>{path.from ? path.from.name : "NOPE"}</td>
                <td>{path.to ? path.to.name : "NOPE"}</td>
                <td>{path.length}</td>
                <td>{path.reserved}</td>
                <td>
                    <ButtonGroup>
                        <Button
                            onClick={() => setEditModalOpen(true)}
                        >
                            E
                        </Button>
                        <PathEditModal
                            isOpen={isEditModalOpen}
                            path={path}
                            onClose={handleClose}
                            onPathUpdate={onPathUpdate}
                        />
                        <Button
                            onClick={deletePath}
                        >
                            D
                        </Button>
                    </ButtonGroup>
                </td>
            </tr>
        </>
    );

};

export default Path;