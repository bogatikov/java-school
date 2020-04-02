import React from 'react';
import './App.css';
import 'typeface-roboto';
import Container from "@material-ui/core/Container";
import StationList from "./component/station/StationList";

function App() {
    return (
        <Container maxWidth="sm">
            <StationList />
            {/*<Button variant="contained">Default</Button>*/}
            {/*<Button variant="contained" color="primary">*/}
            {/*    Primary*/}
            {/*</Button>*/}
            {/*<Button variant="contained" color="secondary">*/}
            {/*    Secondary*/}
            {/*</Button>*/}
            {/*<Button variant="contained" disabled>*/}
            {/*    Disabled*/}
            {/*</Button>*/}
            {/*<Button variant="contained" color="primary" href="#contained-buttons">*/}
            {/*    Link*/}
            {/*</Button>*/}
        </Container>
    );
}

export default App;
