package com.scaler.splitwise.services;

import com.scaler.splitwise.dtos.Transaction;
import com.scaler.splitwise.models.Expense;
import com.scaler.splitwise.models.ExpenseUser;
import com.scaler.splitwise.models.Group;
import com.scaler.splitwise.models.User;
import com.scaler.splitwise.repositories.ExpenseRepository;
import com.scaler.splitwise.repositories.ExpenseUserRepository;
import com.scaler.splitwise.repositories.GroupRepository;
import com.scaler.splitwise.repositories.UserRepository;
import com.scaler.splitwise.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SettleUpService {

    private SettleUpStrategy settleUpStrategy;
    private UserRepository userRepository;
    private ExpenseUserRepository expenseUserRepository;
    private GroupRepository groupRepository;
    private ExpenseRepository expenseRepository;

    public SettleUpService(SettleUpStrategy settleUpStrategy,
                           UserRepository userRepository,
                           ExpenseUserRepository expenseUserRepository,
                           GroupRepository groupRepository,
                           ExpenseRepository expenseRepository) {
        this.settleUpStrategy = settleUpStrategy;
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.groupRepository = groupRepository;
        this.expenseRepository = expenseRepository;
    }

    public List<Transaction> settleUpUser(Long userId) {
        /*
        * 1. Fetch user details
        * 2. Get the list of all the expenses that user is involved in
        * 3. Iterate through all the expenses to get know who owes what and who will recieve what
        * 4. Use min and max heap algo to figure out the list of final transactions
        * 5. Return the list of transactions
        * */

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new RuntimeException();
        }

        User user = userOptional.get();

        List<ExpenseUser> expenseUsers = expenseUserRepository.findAllByUser(user);

        List<Expense> expenses = new ArrayList<>();
        for(ExpenseUser expenseUser: expenseUsers) {
            expenses.add(expenseUser.getExpense());
        }

        List<Transaction> transactions = settleUpStrategy.settle(expenses);

        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction: transactions) {
            if(transaction.getFrom().equals(user) ||
            transaction.getTo().equals(user)) {
                filteredTransactions.add(transaction);
            }
        }


        return filteredTransactions;
    }

    public List<Transaction> settleUpGroup(Long groupId) {
        /*
        * 1. Get the group from database
        * 2. Get all the expenses that are part of that group
        * Everything else same as above
        * */
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()) {
            throw new RuntimeException();
        }

        Group group = groupOptional.get();
        List<Expense> expenses = expenseRepository.findAllByGroup(group);

        List<Transaction> transactions = settleUpStrategy.settle(expenses);

        return transactions;
    }
}
