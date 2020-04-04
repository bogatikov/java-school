import * as React from "react";
import {useEffect, useState} from "react";
import API from "../../utils/API";
import Schedule from "./Schedule";

const ScheduleList = () => {
    const [schedule, setSchedule] = useState({});
    useEffect(() => {
        dataLoad();
    }, []);

    async function dataLoad() {
        await API.get('/api/v1/schedule/')
            .then(response => {
                console.log(response.data);
                setSchedule(response.data);
            })
            .catch(reason => {
                console.log(reason);
            });
    }

    const rows = [];
    Object.entries(schedule).forEach(entry => {
        let stationId = parseInt(entry[0]);
        rows.push(<>
            <Schedule
                ket={stationId}
                stationId={stationId}
                schedule={entry[1]}
            />
            <hr/>
        </>);
    });

    return (
        <div>
            {rows}
        </div>
    );
};

export default ScheduleList;