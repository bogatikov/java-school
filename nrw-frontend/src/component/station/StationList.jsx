import * as React from "react";
import API from "../../utils/API";
import Station from "./Station";
import {Button, Table} from "react-bootstrap";
import StationCreateModal from "./StationCreateModal";

export default class StationList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            stations: [],
            isCreateModalOpen: false
        };
    }

    componentDidMount() {
        this.dataLoad();
    }

    onStationListChanged = (station) => {
        this.dataLoad();
    };

    render() {
        const {isLoading, stations, isCreateModalOpen} = this.state;
        const rows = [];
        stations.forEach(station => {
            rows.push(<Station
                station={station}
                key={station.id}
                onStationListChanged={this.onStationListChanged}
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
                    <th>Value</th>
                    <th>
                        <Button
                            onClick={this.openCreateEntryModal}
                        >
                            A
                        </Button>
                        <StationCreateModal
                            isOpen={isCreateModalOpen}
                            onClose={this.handleClose}
                        />
                    </th>
                </tr>
                </thead>
                <tbody>
                {rows}
                </tbody>
            </Table>
        }
    }


    handleClose = (event) => {
        this.setState({isCreateModalOpen: false})
    };

    openCreateEntryModal = () => {
        this.setState({isCreateModalOpen: true});
    };

    async dataLoad() {
        const {createEntryModalOpen} = this.state;
        await API.get('/api/v1/station/')
            .then(response => {
                this.setState({isLoading: false, stations: response.data, createEntryModalOpen: createEntryModalOpen});
                console.log(response.data);
            })
            .catch(reason => {
                console.log(reason);
            });
    }
}