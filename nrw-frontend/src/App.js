import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Container} from "react-bootstrap";
import StationList from "./component/station/StationList";
import TrainList from "./component/train/TrainList";

const App = () => {
    return (
        <Container>
            <StationList/>
            <TrainList/>
        </Container>
    );
};

export default App;
