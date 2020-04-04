import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Container} from "react-bootstrap";
import StationList from "./component/station/StationList";
import TrainList from "./component/train/TrainList";
import ScheduleList from "./component/schedule/ScheduleList";

const App = () => {
    return (
        <Container>
            <StationList/>
            <TrainList/>
            <h4>Schedule</h4>
            <ScheduleList/>
        </Container>
    );
};

export default App;
