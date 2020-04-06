import * as React from "react";
import {useState} from "react";
import {Button} from "react-bootstrap";
import BuyTicketModal from "../ticket/BuyTicketModal";

const SearchingResultRow = ({...props}) => {
    const {train, pathsOrder} = props;
    const [isTicketPurchaseModalOpen, setTicketPurchaseModalOpen] = useState(false);
    const row = [];

    function trainContainPath(path) {
        return train.track.filter(pth => pth.id === path).length !== 0;
    }
    let count = 0;
    pathsOrder.forEach(path =>  {
        if (trainContainPath(path)) {
            count++;
        }
    });
    if (count === 0) {
        return <></>;
    }

    pathsOrder.forEach(path => {
        if (!trainContainPath(path)) {
            row.push(<td key={path} className="bg-danger"></td>);
        } else {
            row.push(<td key={path} className="bg-success"></td>);
        }
    });
    const handleTicketPurchaseClose = () => {
        setTicketPurchaseModalOpen(false);
    };
    const onTicketPurchaseModalOpen = () => {
        setTicketPurchaseModalOpen(true);
    };
    return (
        <tr>
            <td>{train.number}</td>
            {row}
            <td>

                <Button
                    variant={"warning"}
                    onClick={onTicketPurchaseModalOpen}
                >
                    Buy Ticket
                </Button>
                {isTicketPurchaseModalOpen ? <BuyTicketModal
                    trainId={train.id}
                    onClose={handleTicketPurchaseClose}
                /> : null}
            </td>
        </tr>
    );
};

export default SearchingResultRow;