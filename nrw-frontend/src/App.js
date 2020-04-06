import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Container, Nav, Navbar} from "react-bootstrap";
import StationList from "./component/station/StationList";
import TrainList from "./component/train/TrainList";
import ScheduleList from "./component/schedule/ScheduleList";

import {BrowserRouter as Router, Route, Link} from "react-router-dom";
import TicketSearching from "./component/ticket/TicketSearching";

const App = () => {
    return (
        <Router>
            <Navbar bg="light">
                <Navbar.Brand>
                    <Link to="/">
                        <img
                            src="/logo.png"
                            width="140"
                            height="70"
                            className="d-inline-block align-top"
                            alt="React Bootstrap logo"
                        />
                    </Link>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link>
                            <Link to="/client">Client</Link>
                        </Nav.Link>
                        <Nav.Link>
                            <Link to="/employee">Employee</Link>
                        </Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
            <Container>
                <Route exact path="/">
                    описание
                </Route>
                <Route path="/client">
                    <h4>Ticket searching</h4>
                    <TicketSearching />
                    <hr/>
                    <h4>Schedule</h4>
                    <ScheduleList/>
                </Route>
                <Route path="/employee">
                    <StationList/>
                    <TrainList/>
                </Route>
            </Container>
        </Router>
    );
};

export default App;
