package com.scaler.splitwise.strategies;

import com.scaler.splitwise.dtos.Transaction;
import com.scaler.splitwise.models.Expense;

import java.util.List;

public interface SettleUpStrategy {
    public List<Transaction> settle(List<Expense> expenses);
}
