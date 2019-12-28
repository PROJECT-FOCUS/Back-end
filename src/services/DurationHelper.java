package services;

import java.util.regex.Pattern;

public class DurationHelper {
	public static int getSecondsFromFormattedDuration(String duration) {
        if (duration == null)
            return 0;
        try {

            Pattern patternDuration = Pattern.compile("\\d+(?::\\d+){0,2}");

            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            if (patternDuration.matcher(duration).matches()){
                String[] tokens = duration.split(":");
                if (tokens.length==1){
                    seconds = Integer.parseInt(tokens[0]);
                } else if (tokens.length == 2){
                    minutes = Integer.parseInt(tokens[0]);
                    seconds = Integer.parseInt(tokens[1]);
                } else {
                    hours = Integer.parseInt(tokens[0]);
                    minutes = Integer.parseInt(tokens[1]);
                    seconds = Integer.parseInt(tokens[2]);
                }
                return 3600 * hours + 60 * minutes + seconds;
            } else {
                return 0;
            }
        } catch (NumberFormatException ignored){
            return 0;
        }
	}
}
