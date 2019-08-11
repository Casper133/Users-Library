package com.casper.userslibrary.controller.command.wrapper;

import com.casper.userslibrary.controller.command.Command;
import com.casper.userslibrary.view.MessageWriter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HelpCommand implements Command {
    private final MessageWriter messageWriter;


    @Override
    public void execute() {
        messageWriter.writeMessage("Список доступных команд:");
        messageWriter.writeMessage("/new_user — создать пользователя");
        messageWriter.writeMessage("/edit_user — редактировать пользователя");
        messageWriter.writeMessage("/delete_user — удалить пользователя");
        messageWriter.writeMessage("/all_users — список всех пользователей");
        messageWriter.writeMessage("/help — справка");
        messageWriter.writeMessage("/exit — выход");
    }
}
