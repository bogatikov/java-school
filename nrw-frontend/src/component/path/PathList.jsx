import * as React from "react";
import {useEffect, useState} from "react";
import {Button, Table} from "react-bootstrap";
import TrainCreateModal from "../train/TrainCreateModal";
import API from "../../utils/API";
import Train from "../train/Train";

const PathList = () => {
    const [isLoading, setIsLoading] = useState(true);
    const [paths, setPaths] = useState([]);
    const [isCreateModalOpen, setCreateModalOpen] = useState(false);

    useEffect(() => {
        dataLoad();
    }, []);

    const onPathListChanged = () => {
        dataLoad();
    };

    const onPathCreated = (path) => {
        setPaths(prevState => {
            prevState.push(path);
            return prevState;
        });
    };

    async function dataLoad() {
        await API.get('/api/v1/path/')
            .then(response => {
                setIsLoading(false);
                setPaths(response.data);
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
    paths.forEach(path => {
        rows.push(<Path
            train={path}
            key={path.id}
            onTrainListChanged={onPathListChanged}
        />);
    });
    if (isLoading) {
        return <div>Loading....</div>;
    }

    return (
        <Table responsive hover>
            <thead>
            <tr>
                <th>From Station</th>
                <th>To Station</th>
                <th>Length</th>
                <th>Reserved</th>
                <th>
                    <Button
                        onClick={openCreateEntryModal}
                    >
                        A
                    </Button>
                    <TrainCreateModal
                        isOpen={isCreateModalOpen}
                        onClose={handleClose}
                        onTrainCreated={onPathCreated}
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

export default PathList;