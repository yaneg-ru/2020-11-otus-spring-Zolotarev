package ru.otus.homework.shell;

import java.io.IOException;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.service.impl.RunnerQuestsImpl;

@SuppressWarnings("unused")
@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {

    private final RunnerQuestsImpl runnerQuests;

    @ShellMethod(value = "Meet command", key = {"m", "meet"})
    public String meet(@ShellOption(value = {"f"}, defaultValue = "AnyFirstUser") String firstName,
                       @ShellOption(value = {"s"}, defaultValue = "AnySecondUser") String secondName) {
        runnerQuests.setFirstName(firstName);
        runnerQuests.setSecondName(secondName);
        return String.format("Добро пожаловать: %s %s", firstName, secondName) + System.lineSeparator();

    }

    @ShellMethod(value = "Run quest command", key = {"r", "run"})
    @ShellMethodAvailability(value = "isRunQuestCommandAvailable")
    public void runQuest() throws IOException {
        runnerQuests.runQuest();
    }

    private Availability isRunQuestCommandAvailable() {
        return runnerQuests.getFirstName() == null && runnerQuests.getSecondName() == null
                ? Availability.unavailable("Сначала давайте познакомимся...")
                : Availability.available();
    }
}
