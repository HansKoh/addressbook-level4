package com.t13g2.forum.testutil;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_COMMENT;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_INDEX;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_TAG;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_THREAD;

import java.util.Set;

import com.t13g2.forum.logic.commands.AddCommand;
import com.t13g2.forum.logic.commands.EditCommand.EditPersonDescriptor;
import com.t13g2.forum.model.person.Person;
import com.t13g2.forum.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE + person.getName().fullName + " ");
        sb.append(PREFIX_THREAD + person.getPhone().value + " ");
        sb.append(PREFIX_INDEX + person.getEmail().value + " ");
        sb.append(PREFIX_COMMENT + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_MODULE).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_THREAD).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_INDEX).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_COMMENT).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}