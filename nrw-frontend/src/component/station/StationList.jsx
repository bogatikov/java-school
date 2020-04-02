import * as React from "react";
import API from "../../utils/API";
import Station from "./Station";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import AddIcon from '@material-ui/icons/Add';
import Button from "@material-ui/core/Button";

export default class StationList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            stations: []
        };
    }

    componentDidMount() {
        this.dataLoad();
    }

    render() {
        const {isLoading, stations} = this.state;
        const rows = [];
        stations.forEach(station => {
            rows.push(<Station station={station} key={station.id}/>);
        });
        if (isLoading) {
            return <div>Loading....</div>;
        } else {
            return <TableContainer component={Paper}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Station</TableCell>
                            <TableCell align="right">Longitude</TableCell>
                            <TableCell align="right">Latitude</TableCell>
                            <TableCell align="right">Capacity</TableCell>
                            <TableCell align="right"><Button>
                                <AddIcon/>
                            </Button></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {rows}
                    </TableBody>
                </Table>
            </TableContainer>;
        }
    }

    async dataLoad() {
        const response = await API.get('/api/v1/station/')
            .then(response => {
                this.setState({isLoading: false, stations: response.data});
            })
            .catch(reason => {
                console.log(reason);
            });
    }
}