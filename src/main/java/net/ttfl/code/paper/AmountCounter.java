package net.ttfl.code.paper;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AmountCounter {
    private TiaCustomOnline plugin;

    public AmountCounter(TiaCustomOnline plugin){
        this.plugin = plugin;
    }

    public int getOnline(int online, int max){
        if(!plugin.getConfig().getBoolean("online.enable")){
            return online;
        }

        String mode = plugin.getConfig().getString("online.mode");

        switch (mode){
            case "custom":
                String custom = plugin.getConfig().getString("online.modes.custom.amount");
                return count(pattern(custom, online, max));

            case "random":
                int randomMin = count(pattern(plugin.getConfig().getString("online.modes.random.min"), online, max));
                int randomMax = count(pattern(plugin.getConfig().getString("online.modes.random.max"), online, max));
                return randomMin + (int)(Math.random() * (randomMax - randomMin));

            case "timed":
                int minNum = count(pattern(plugin.getConfig().getString("online.modes.timed.min"), online, max));
                int maxNum = count(pattern(plugin.getConfig().getString("online.modes.timed.max"), online, max));
                LocalTime minTime = LocalTime.of(plugin.getConfig().getInt("online.modes.timed.min-time"), 0);
                LocalTime maxTime = LocalTime.of(plugin.getConfig().getInt("online.modes.timed.max-time"), 0);
                return getNumByTime(minTime, maxTime, minNum, maxNum, 0, 0);

            case "random-timed":
                int minNum1 = count(pattern(plugin.getConfig().getString("online.modes.random-timed.min"), online, max));
                int maxNum1 = count(pattern(plugin.getConfig().getString("online.modes.random-timed.max"), online, max));
                int minRandom = count(pattern(plugin.getConfig().getString("online.modes.random-timed.min-random"), online, max));
                int maxRandom = count(pattern(plugin.getConfig().getString("online.modes.random-timed.max-random"), online, max));
                LocalTime minTime1 = LocalTime.of(plugin.getConfig().getInt("online.modes.timed.min-time"), 0);
                LocalTime maxTime1 = LocalTime.of(plugin.getConfig().getInt("online.modes.timed.max-time"), 0);
                return getNumByTime(minTime1, maxTime1, minNum1, maxNum1, minRandom, maxRandom);

            default:
                return online;
        }
    }

    public int getMax(int online, int max){
        if(!plugin.getConfig().getBoolean("max.enable")){
            return max;
        }

        return plugin.getConfig().getInt("max.amount");
    }

    public String pattern(String pattern, int online, int max){
        pattern = pattern.replaceAll("%online%", String.valueOf(online));
        pattern = pattern.replaceAll("%max%", String.valueOf(getMax(online, max)));

        return pattern;
    }

    public int count(String expression){
        Expression exp = new Expression(expression);
        try {
            EvaluationValue result = exp.evaluate();
            return result.getNumberValue().intValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumByTime(LocalTime minTime, LocalTime maxTime, int minNum, int maxNum, int minRandom, int maxRandom){
        LocalTime now = LocalTime.now();

        long totalDuration = ChronoUnit.MINUTES.between(minTime, maxTime);
        if(totalDuration < 0){
            totalDuration += 24 * 60;
        }

        boolean isAscending;
        long elapsedMinutes;

        if(isTimeInRange(now, minTime, maxTime)){
            isAscending = true;
            elapsedMinutes = ChronoUnit.MINUTES.between(minTime, now);
            if(elapsedMinutes < 0){
                elapsedMinutes += 24 * 60;
            }
        }
        else{
            isAscending = false;
            elapsedMinutes = ChronoUnit.MINUTES.between(maxTime, now);
            if(elapsedMinutes < 0){
                elapsedMinutes += 24 * 60;
            }
        }

        int random = (int) (Math.random() * (maxRandom + minRandom + 1)) - minRandom;

        if(isAscending){
            int result =  (int)(minNum + (maxNum - minNum) * (double)elapsedMinutes / totalDuration) + random;
            return result > 0 ? result : 1;
        }
        else{
            int result = (int)(maxNum - (maxNum - minNum) * (double)elapsedMinutes / totalDuration) + random;
            return result > 0 ? result : 1;
        }
    }

    public boolean isTimeInRange(LocalTime now, LocalTime minTime, LocalTime maxTime){
        if(minTime.isBefore(maxTime)){
            return !now.isBefore(minTime) && !now.isAfter(maxTime);
        }
        else{
            return !now.isBefore(minTime) || !now.isAfter(maxTime);
        }
    }
}
