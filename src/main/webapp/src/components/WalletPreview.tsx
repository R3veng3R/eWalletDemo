import React from "react";
import {Wallet} from "../types";

interface Props {
    wallet: Wallet
}

export const WalletPreview: React.FC<Props> = ({ wallet}) => (
    <div>
        <p><strong>{wallet.id}</strong></p>
        <p>Balance: {wallet.balance.toFixed(2)} €</p>
    </div>
)