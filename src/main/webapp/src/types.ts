export interface User {
    id: string;
    name: string
}

export interface Wallet {
    id: string;
    balance: Number;
    createdAt: string;
}

export interface BalanceRequest {
    walletId: string;
    sum: string;
    type: BalanceRequestType;
}

export enum BalanceRequestType {
    Add = "add",
    Withdraw = "withdraw"
}