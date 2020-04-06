import * as React from "react";
import {useEffect, useState} from "react";
import {Button, Form, InputGroup} from "react-bootstrap";
import API from "../../utils/API";
import SearchingResultTable from "../searching/SearchingResultTable";
import SearchResultContainer from "../searching/SearchResultContainer";


const TicketSearching = () => {

    const [stations, setStations] = useState([]);
    const [from, setFrom] = useState(0);
    const [to, setTo] = useState(0);
    const [resultTable, setResultTable] = useState(null);


    useEffect(() => {
        loadStations();
    }, []);

    async function loadStations() {
        await API.get("/api/v1/station/active")
            .then(response => {
                setStations(response.data);
            });
    }


    const onFromStationChanged=(event) => {
        setFrom(parseInt(event.target.value));
        setResultTable(<></>);
    };
    const onToStationChanged=(event) => {
        setTo(parseInt(event.target.value));
        setResultTable(<></>);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (to != 0 && from != 0) {
            setResultTable(
                <SearchResultContainer
                    from={from}
                    to={to}
                />
            );
            console.log(from);
            console.log(to);
        }
    };

    const stationsOptions = [];
    stations.forEach(station => {

        stationsOptions.push(<option key={station.id} value={station.id}>{station.name}</option>);
    });


    return (
        <>
            <Form onSubmit={handleSubmit}>
                <InputGroup className="mb-3">
                    <InputGroup.Append>
                        <InputGroup.Text>From</InputGroup.Text>
                    </InputGroup.Append>
                    <Form.Control as="select" onChange={onFromStationChanged}>
                        <option value="0" selected></option>
                        {stationsOptions}
                    </Form.Control>
                    <InputGroup.Append>
                        <InputGroup.Text>To</InputGroup.Text>
                    </InputGroup.Append>
                    <Form.Control as="select" onChange={onToStationChanged}>
                        <option value="0" selected></option>
                        {stationsOptions}
                    </Form.Control>
                    <InputGroup.Append>
                        <Button variant="primary" type="submit">
                            Search
                        </Button>
                    </InputGroup.Append>
                </InputGroup>
            </Form>
            {resultTable}
        </>
    );
};

export default TicketSearching;