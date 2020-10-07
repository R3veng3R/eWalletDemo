import React, {useEffect, useState} from "react";
import {Api} from "../utils/Api";
import {Wallet} from "../types";
import {Button, ListGroup} from "react-bootstrap";
import styled from "styled-components";
import {WalletPreview} from "../components/WalletPreview";

const Container = styled.div`
    padding-top: 2rem;
    width: 50%;
    margin: 0 auto;
`;

const ButtonWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
    align-content: center;
    margin-bottom: 1.5rem;
`;

export const HomePage: React.FC = () => {
    const [walletList, setWalletList] = useState<Wallet[]>([]);

    useEffect(() => {
        getWallets();

    }, []);

    const getWallets = async () => {
        const walletList = await Api.get("/api/wallet");

        if (walletList.status == 200) {
            setWalletList(walletList.data);
        } else {
            throw new Error("Could not get wallet list");
        }
    }

    const createWallet = async () => {
        const result = await Api.post("/api/wallet");

        if (result.status == 200) {
            await getWallets();
        } else {
            throw new Error("Couldn't create new wallet");
        }
    }

    return (
        <Container>
            <ButtonWrapper>
                <Button onClick={createWallet}>Create new wallet</Button>
            </ButtonWrapper>

            <ListGroup>
                {
                    walletList.map( wallet =>
                        <ListGroup.Item key={wallet.id}>
                            <WalletPreview wallet={wallet} />
                        </ListGroup.Item>
                    )
                }
            </ListGroup>
        </Container>
    );
}