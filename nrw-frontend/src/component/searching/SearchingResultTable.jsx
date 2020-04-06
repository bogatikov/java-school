import * as React from "react";
import {Table} from "react-bootstrap";
import SearchingResultRow from "./SearchingResultRow";

const SearchingResultTable = ({...props}) => {

    const {paths, trains} = props;


    const pathsOrder = [];
    const pathHeaders = [];
    paths.forEach(path => {
        pathsOrder.push(path.id);
        pathHeaders.push(<th key={path.id}>{path.f_node.name} {"<->"} {path.s_node.name}</th>);
    });

    function pathCountMatchForTrain(train) {
        let counter = 0;
        const trainTrack = train.track;
        trainTrack.forEach(trk => {
            if (pathsOrder.indexOf(trk.id) !== -1) {
                counter++;
            }
        });
        return counter;
    }

    trains.sort((a, b) => {
        return pathCountMatchForTrain(b) - pathCountMatchForTrain(a);
    });

    const searchingResultRows = [];
    trains.forEach(train => {

        searchingResultRows.push(
            <SearchingResultRow
                key={train.id}
                train={train}
                pathsOrder={pathsOrder}
                paths={paths}
            />
        );
    });

    return (
        <Table>
            <tr>
                <th></th>
                {pathHeaders}
                <th></th>
            </tr>
            {searchingResultRows}
        </Table>
    );
};
export default SearchingResultTable;