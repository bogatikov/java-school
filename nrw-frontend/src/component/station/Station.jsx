import * as React from "react";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import Button from "@material-ui/core/Button";
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import Divider from "@material-ui/core/Divider";

export default class Station extends React.Component {
    render() {
        const {station} = this.props;
        return (
            <TableRow key={station.name}>
                <TableCell component="th" scope="row">
                    {station.name}
                </TableCell>
                <TableCell align="right">{station.longitude}</TableCell>
                <TableCell align="right">{station.latitude}</TableCell>
                <TableCell align="right">{station.capacity}</TableCell>
                <TableCell>
                    <Button>
                        <EditIcon fontSize="small"/>
                    </Button>
                    <Button>
                        <DeleteIcon fontSize="small"/>
                    </Button>
                </TableCell>
            </TableRow>
        );
    }
}