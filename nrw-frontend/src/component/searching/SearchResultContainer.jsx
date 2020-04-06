import * as React from "react";
import {useEffect, useState} from "react";
import API from "../../utils/API";
import Track from "../path/Track";
import SearchingResultTable from "./SearchingResultTable";

const SearchResultContainer = ({...props}) => {
    const {from, to} = props;

    const [trains, setTrains] = useState([]);
    const [tracks, setTracks] = useState([[]]);


    useEffect(() => {
        loadTracks();
        loadTrains();
    }, []);

    async function loadTracks() {
        await API.get('/api/v1/path/find/from/' + from + '/to/' + to)
            .then(response => {
                setTracks(response.data);
            });
    }

    async function loadTrains() {
        await API.get('/api/v1/train/')
            .then(response => {
                setTrains(response.data);
            })
            .catch(reason => {
                console.log(reason);
            });
    }

    const rows = [];
    tracks.forEach(track => {
        console.log(track);
        rows.push(
            <>
                <SearchingResultTable
                    paths={track}
                    trains={trains}
                />
            </>
        );
    });


    return (
        <>
            {rows}
        </>
    );

};
export default SearchResultContainer;