import React, {useEffect, useState} from "react";
import API from "../../utils/API";
import {Table} from "react-bootstrap";


const Schedule = ({...props}) => {

    const {stationId, schedule} = props;
    const [station, setStation] = useState({});

    useEffect(() => {
        loadStation();
    }, []);

    async function loadStation() {
        await API.get('/api/v1/station/' + stationId)
            .then(response => {
                setStation(response.data);
            });
    }

    const rows = [];
    schedule.forEach(sch => {
        const date = new Date(sch.arrivalTime.epochSecond * 1000);
        const arrival = date.getFullYear() + '/' + date.getMonth() + '/' + date.getDay() + ' ' + date.getHours() + ':' + date.getMinutes();
        rows.push(<tr key={sch.train.id}>
            <td>{sch.train.number}</td>
            <td>{arrival}</td>
        </tr>);
    });
    return (
        <>
            <h5>{station.name}</h5>
            <Table responsive>
                <tr>
                    <th>Train</th>
                    <th>Arrival time</th>
                </tr>
                {rows}
            </Table>
        </>
    );
};

export default Schedule;