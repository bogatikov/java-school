import * as React from "react";
import API from "../../utils/API";
import Station from "./Station";
import {Button, Table} from "react-bootstrap";
import StationCreateModal from "./StationCreateModal";
import {useState} from "react";
import {useEffect} from "react";

const StationList = ({...props}) => {
    const [isLoading, setIsLoading] = useState(true);
    const [stations, setStations] = useState([]);
    const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);

    useEffect(() => {
        dataLoad()
    }, []);

    const onStationListChanged = (station) => {
        dataLoad();
    };
    async function dataLoad() {
        await API.get('/api/v1/station/')
            .then(response => {
                setIsLoading(false);
                setStations(response.data);
            })
            .catch(reason => {
                console.log(reason);
            });
    }

    const onStationCreated = (station) => {
        console.log(station);
        setStations(prevState => {
            prevState.push(station);
            return prevState;
        });
    };
    const handleClose = (event) => {
        setIsCreateModalOpen(false);
    };

    const openCreateEntryModal = () => {
        setIsCreateModalOpen(true);
    };

    const rows = [];
    stations.forEach(station => {
        rows.push(<Station
            station={station}
            key={station.id}
            onStationListChanged={onStationListChanged}
        />);
    });
    if (isLoading) {
        return <div>Loading....</div>;
    } else {
        return <Table responsive hover>
            <thead>
            <tr>
                <th>Station</th>
                <th>Longitude</th>
                <th>Latitude</th>
                <th>Capacity</th>
                <th>Waiting time</th>
                <th>
                    <Button
                        onClick={openCreateEntryModal}
                    >
                        A
                    </Button>
                    <StationCreateModal
                        isOpen={isCreateModalOpen}
                        onClose={handleClose}
                        onStationCreated={onStationCreated}
                    />
                </th>
            </tr>
            </thead>
            <tbody>
            {rows}
            </tbody>
        </Table>
    }
};

export default StationList;