import React from 'react';
import './App.css';
import 'typeface-roboto';
import Button from "@material-ui/core/Button";
import Container from "@material-ui/core/Container";

function App() {
    return (
        <Container maxWidth="sm">
            <Button variant="contained">Default</Button>
            <Button variant="contained" color="primary">
                Primary
            </Button>
            <Button variant="contained" color="secondary">
                Secondary
            </Button>
            <Button variant="contained" disabled>
                Disabled
            </Button>
            <Button variant="contained" color="primary" href="#contained-buttons">
                Link
            </Button>
        </Container>
    );
}

export default App;
