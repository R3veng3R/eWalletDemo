import React, {useEffect, useState} from "react";
import {Api} from "../utils/Api";
import {BalanceRequest, Wallet} from "../types";
import {Button, ListGroup} from "react-bootstrap";
import styled from "styled-components";
import {WalletPreview} from "../components/WalletPreview";
import {toast, ToastContainer} from "react-toastify";
import {Loader} from "../components/loader";

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
    const [isLoading, setLoading] = useState<boolean>(false);

    useEffect(() => {
        getWallets();
    }, []);

    const getWallets = async () => {
        setLoading(true);

        try {
            const walletList = await Api.get("/api/wallet");
            setWalletList(walletList.data);
        } catch (exception) {
            toast.error(exception.response.data);
        }

        setLoading(false);
    }

    const createWallet = async () => {
        setLoading(true);

        try {
            await Api.post("/api/wallet");
            await getWallets();
            toast.success("New wallet created");

        } catch (exception) {
            toast.error(exception.response.data);
        }

        setLoading(false);
    }

    const changeBalanceRequest = async (request: BalanceRequest) => {
        if(Number(request.sum) === 0 ) {
            toast.info("You need to add amount into the input before withdraw or adding balance.");
            return;
        }

        setLoading(true);

        try {
            await Api.put("/api/wallet", request);
            await getWallets();
            toast.success("Balance has been updated");

        } catch (exception) {
            toast.error(exception.response.data);
        }

        setLoading(false);
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
                            <WalletPreview wallet={wallet} onButtonClick={changeBalanceRequest}/>
                        </ListGroup.Item>
                    )
                }
            </ListGroup>

            <ToastContainer
                position="bottom-right"
                autoClose={3000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                pauseOnHover
            />

            <Loader isHidden={!isLoading}/>
        </Container>
    );
}