package com.scaler.splitwise.strategies;

import com.scaler.splitwise.dtos.Transaction;
import com.scaler.splitwise.models.Expense;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MinMaxHeapSettleUpStrategy implements SettleUpStrategy {
    @Override
    public List<Transaction> settle(List<Expense> expenses) {
//        for(Expense expense: expenses) {
//            List<ExpenseUser> expenseUsers = expense.getExpenseUser();
//            for(ExpenseUser expenseUser: expenseUsers) {
//                extra_amount = 0;
//                if(expenseUser.getExpenseUserType() == ExpenseUserType.PAID) {
//                    amout_ex
//                }
//            }
//        }

        return null;
    }
}
