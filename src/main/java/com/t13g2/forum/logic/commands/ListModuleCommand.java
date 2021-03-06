package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_NOT_LOGIN;
import static java.util.Objects.requireNonNull;

import java.util.List;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.logic.util.DisplayFormatter;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Module;

//@@author HansKoh
/**
 * List out all the modules in the forum book.
 */
public class ListModuleCommand extends Command {
    public static final String COMMAND_WORD = "listModule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all the modules in the forum book.\n"
            + "Example: " + COMMAND_WORD + " ";

    public static final String MESSAGE_SUCCESS = "Listed all modules: \n"
            + "****************************************************************************\n"
            + "****************************************************************************\n"
            + "%s";
    private static String message;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            List<Module> moduleList = unitOfWork.getModuleRepository().getAllModule();
            message = DisplayFormatter.displayModuleList(moduleList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, message));
    }
}
