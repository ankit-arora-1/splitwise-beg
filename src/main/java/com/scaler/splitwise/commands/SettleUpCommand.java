package com.scaler.splitwise.commands;

import com.scaler.splitwise.controllers.SettleUpController;
import com.scaler.splitwise.dtos.SettleUpResponseDto;

import java.util.List;

public class SettleUpCommand implements Command {

    private SettleUpController settleUpController;

    public SettleUpCommand(SettleUpController settleUpController) {
        this.settleUpController = settleUpController;
    }

    @Override
    public void execute(String input) {
        List<String> tokens = List.of(input.split(" "));
        Long userId = Long.valueOf(tokens.get(0));

        SettleUpResponseDto responseDto = settleUpController.settleUpUser();
        responseDto.setUserId(userId);

        System.out.println(responseDto);
    }

    @Override
    public boolean matches(String input) {
        // u1 settleUp
        List<String> tokens = List.of(input.split(" "));

        return tokens.size() == 2 && tokens.get(1)
                .equalsIgnoreCase(CommandKeyword.SETTLE_UP);
    }
}
