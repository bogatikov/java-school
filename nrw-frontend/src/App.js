import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Container} from "react-bootstrap";
import StationList from "./component/station/StationList";

function App() {
    return (
        <Container>
            <StationList />
        </Container>
    );
}

export default App;
