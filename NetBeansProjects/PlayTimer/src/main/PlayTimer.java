/*
 * Copyright (C) 2021 Dundun <dominikmesserschmidt@googlemail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main;

/**
 *
 * @author Dundun <dominikmesserschmidt@googlemail.com>
 * Created 28.05.2021
 */
public class PlayTimer {

    private double seconds_per_tick = 1;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int MINUTES_PER_HOUR = 60;
    public static final int HOURS_PER_DAY = 24;

    double s;
    int h, m;

    public PlayTimer() {
        h = 0;
        m = 0;
        s = 0;
    }

    public PlayTimer(int hours, int minuts, int secs) {
        h = hours;
        m = minuts;
        s = secs;
    }

    public final void tick() {
        addTime(0, 0, seconds_per_tick);
    }

    public void setSecondsPerTick(double s) {
        seconds_per_tick = s;
    }

    public double getSecondsPerTick() {
        return seconds_per_tick;
    }

    private void addTime(int hours, int minuts, double seconds) {
        s += seconds;
        while (s >= SECONDS_PER_MINUTE) {
            m++;
            s -= SECONDS_PER_MINUTE;
        }
        while (m >= MINUTES_PER_HOUR) {
            h++;
            m -= MINUTES_PER_HOUR;
        }
        while (h >= HOURS_PER_DAY) {
            //TODO count days
            h = 0;
        }
    }

    public int getHours() {
        return h;
    }

    public int getMinutes() {
        return m;
    }

    public double getSeconds() {
        return s;
    }
}
