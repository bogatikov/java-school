import * as React from "react";
import {useEffect, useState} from "react";
import {Button, Table} from "react-bootstrap";
import TrainCreateModal from "../train/TrainCreateModal";
import Train from "./Train";
import API from "../../utils/API";

//TODO time unit
const TrainList = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [trains, setTrains] = useState([]);
    const [isCreateModalOpen, setCreateModalOpen] = useState(false);

    useEffect(() => {
        dataLoad();
    }, []);

    const onTrainListChanged = () => {
        dataLoad();
    };

    const onTrainCreated = (train) => {
        setTrains(prevState => {
            prevState.push(train);
            return prevState;
        });
    };

    async function dataLoad() {
        await API.get('/api/v1/train/')
            .then(response => {
                setIsLoading(false);
                setTrains(response.data);
            })
            .catch(reason => {
                console.log(reason);
            });
    }

    const handleClose = () => {
        setCreateModalOpen(false);
    };

    const openCreateEntryModal = () => {
        setCreateModalOpen(true);
    };

    const rows = [];
    trains.forEach(train => {
        rows.push(<Train
            train={train}
            key={train.id}
            onTrainListChanged={onTrainListChanged}
        />);
    });
    if (isLoading) {
        return <div>Loading....</div>;
    }

    return (
        <Table responsive hover>
            <thead>
            <tr>
                <th>Number</th>
                <th>From</th>
                <th>To</th>
                <th>Train state</th>
                <th>Train direction</th>
                <th>
                    <Button
                        variant="success"
                        onClick={openCreateEntryModal}
                    >
                        A
                    </Button>
                    <TrainCreateModal
                        isOpen={isCreateModalOpen}
                        onClose={handleClose}
                        onTrainCreated={onTrainCreated}
                    />
                </th>
            </tr>
            </thead>
            <tbody>
            {rows}
            </tbody>
        </Table>
    );
};

export default TrainList;