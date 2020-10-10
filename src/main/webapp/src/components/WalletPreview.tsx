import React, {ChangeEvent, useState} from "react";
import {BalanceRequest, BalanceRequestType, Wallet} from "../types";
import styled from "styled-components";
import PropTypes from "prop-types";
import {Button, Form} from "react-bootstrap";

const Container = styled.div`
    display: flex;
    align-content: center;
    justify-content: space-between;
`;

interface Props {
    wallet: Wallet,
    onButtonClick: (requestDTO: BalanceRequest) => void;
}

export const WalletPreview: React.FC<Props> = ({ wallet, onButtonClick }) => {
    const [value, setValue] = useState("0.00");

    const change = (event: ChangeEvent<HTMLInputElement>) => {
        const val = Number(event.target.value);

        if (val <= 0) {
            setValue("0.00");
            return;
        }
        setValue(event.target.value)
    }

    const onClick = (type: BalanceRequestType) => {
        const requestDTO: BalanceRequest = {
            sum: value,
            type: type,
            walletId: wallet.id
        }

        onButtonClick(requestDTO);
    }

    return (
        <Container>
            <div>
                <p>WALLET ID: <strong>{wallet.id}</strong></p>
                <p>Balance: <strong>{wallet.balance.toFixed(2)} €</strong></p>
            </div>

            <div>
                <Form.Group controlId="formLastName" >
                    <Form.Control
                        className="mb-3"
                        type="number"
                        name="name"
                        value={value}
                        onChange={ change }
                    />

                    <Button type='submit' className="mr-1" onClick={ () => onClick(BalanceRequestType.Add) }> Add to balance </Button>
                    <Button type='submit' onClick={ () => onClick(BalanceRequestType.Withdraw) }> Withdraw </Button>
                </Form.Group>
            </div>
        </Container>
    )
}

WalletPreview.propTypes = {
    wallet: PropTypes.shape({
        id: PropTypes.string.isRequired,
        balance: PropTypes.number.isRequired,
        createdAt: PropTypes.string.isRequired,
    }).isRequired,
    onButtonClick: PropTypes.func.isRequired
}
