package com.woodythompson;

import com.google.common.collect.ImmutableList;
import lombok.Value;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Nael {

    private static final String INTRO_TEXT = "Nael Quotes Flashcards\n" +
                                             "\n" +
                                             "You will be given a Nael quote, and must input the correct sequence of mechanics for that quote, separated by spaces.\n" +
                                             "The valid mechanic names are:\n" +
                                             "\tdynamo, chariot, beam, dalamud, raven, stream\n" +
                                             "\n" +
                                             "To exit, type \"exit\".";

    private static final Clock CLOCK = Clock.systemUTC();

    private enum Mechanic {
        DYNAMO,
        CHARIOT,
        BEAM,
        DALAMUD,
        RAVEN,
        STREAM
    }

    @Value
    private static class QuoteResolution {
        String quote;
        List<Mechanic> resolution;
    }

    private static final List<QuoteResolution> QUOTES = ImmutableList.of(
            new QuoteResolution("1: O hallowed moon, take fire and scorch my foes!", ImmutableList.of(Mechanic.DYNAMO, Mechanic.BEAM)),
            new QuoteResolution("1: O hallowed moon, shine you the iron path!", ImmutableList.of(Mechanic.DYNAMO, Mechanic.CHARIOT)),
            new QuoteResolution("2: Blazing path, lead me to iron rule!", ImmutableList.of(Mechanic.BEAM, Mechanic.CHARIOT)),
            new QuoteResolution("2: Take fire, O hallowed moon!", ImmutableList.of(Mechanic.BEAM, Mechanic.DYNAMO)),
            new QuoteResolution("3: From on high I descend, the iron path to call!", ImmutableList.of(Mechanic.RAVEN, Mechanic.CHARIOT)),
            new QuoteResolution("3: From on high I descend, the iron path to walk!", ImmutableList.of(Mechanic.RAVEN, Mechanic.CHARIOT)),
            new QuoteResolution("3: From on high I descend, the hallowed moon to call!", ImmutableList.of(Mechanic.RAVEN, Mechanic.DYNAMO)),
            new QuoteResolution("4: Fleeting light! 'Neath the red moon, scorch you the earth!", ImmutableList.of(Mechanic.DALAMUD, Mechanic.BEAM)),
            new QuoteResolution("4: Fleeting light! Amid a rain of stars, exalt you the red moon!", ImmutableList.of(Mechanic.STREAM, Mechanic.DALAMUD)),
            new QuoteResolution("Fellruin: From on high I descend, the moon and stars to bring!", ImmutableList.of(Mechanic.RAVEN, Mechanic.DYNAMO, Mechanic.STREAM)),
            new QuoteResolution("Fellruin: From hallowed moon I descend, a rain of stars to bring!", ImmutableList.of(Mechanic.DYNAMO, Mechanic.RAVEN, Mechanic.STREAM)),
            new QuoteResolution("Adds: From hallowed moon I descend, upon burning earth to tread!", ImmutableList.of(Mechanic.DYNAMO, Mechanic.RAVEN, Mechanic.BEAM)),
            new QuoteResolution("Adds: From hallowed moon I bare iron, in my descent to wield!", ImmutableList.of(Mechanic.DYNAMO, Mechanic.CHARIOT, Mechanic.RAVEN)),
            new QuoteResolution("Adds: Unbending iron, take fire and descend!", ImmutableList.of(Mechanic.CHARIOT, Mechanic.BEAM, Mechanic.RAVEN)),
            new QuoteResolution("Adds: Unbending iron, descend with fiery edge!", ImmutableList.of(Mechanic.CHARIOT, Mechanic.RAVEN, Mechanic.BEAM))
    );

    public static void main(String[] args) {
        System.out.println(INTRO_TEXT);

        Random r = new Random();
        Scanner scanner = new Scanner(System.in);
        int streak = 0;
        while (true) {
            QuoteResolution selected = QUOTES.get(r.nextInt(QUOTES.size()));
            System.out.print(selected.getQuote() + ": ");
            System.out.flush();
            Instant start = Instant.now(CLOCK);

            String input;
            do {
                input = scanner.nextLine();
            } while (input.length() == 0);

            if (input.trim().equalsIgnoreCase("exit")) {
                break;
            }

            Duration duration = Duration.between(start, Instant.now());
            boolean isCorrect = validate(selected.getResolution(), input);
            streak = isCorrect ? streak + 1 : 0;
            StringBuilder result = new StringBuilder();
            result.append(isCorrect ? "Correct!\t" : "Incorrect!\t");
            result.append(String.format("Time taken: %d.%03d seconds\t", duration.getSeconds(), duration.getNano() / 1000_000));
            result.append(String.format("Current streak: %d", streak));
            result.append("\n");
            System.out.println(result);
        }
    }

    private static boolean validate(List<Mechanic> expected, String input) {
        try {
            List<Mechanic> transformedInput = Arrays.stream(input.split("\\s"))
                    .map(String::toUpperCase)
                    .map(Mechanic::valueOf)
                    .collect(Collectors.toList());

            if (expected.size() != transformedInput.size()) {
                return false;
            }

            for (int i = 0; i < expected.size(); i++) {
                if (!expected.get(i).equals(transformedInput.get(i))) {
                    return false;
                }
            }

            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }
}
