package com.capgemini.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.time.DayOfWeek.MONDAY;

public class CalendarFormatting {

    private final static int weekNos = 6;

    /**
     * Get the calendar of a specific year.
     * @param year Year of which you'd want to obtain the days,months,weeks.
     * @param maxMonthPerRow How many rows are stacked behind one another.
     */
    public static String CalendarOfYear(int year, int maxMonthPerRow)
    {
        return CalendarOfYear(year, maxMonthPerRow, false);
    }

    /**
     * Get the calendar of a specific year.
     * @param year Year of which you'd want to obtain the days,months,weeks.
     * @param maxMonthPerRow How many rows are stacked behind one another.
     * @param isForeign NotYetImplemented
     */
    private static String CalendarOfYear(int year, int maxMonthPerRow, boolean isForeign)
    {
        int rowIndex = 0;
        List<String> rows = new ArrayList<>();
        int maxLenght = new String("    ").toCharArray().length * (weekNos);
        String defaultRow = "";
        for(int i = 0; i < maxLenght; i++)
        {
            defaultRow += " ";
        }
        for(int month = 0; month < 12; month++)
        {
            int monthNumber = month + 1;
            if(month % maxMonthPerRow == 0)
            {
                rowIndex = month / maxMonthPerRow;
                int startMonthOfList = monthNumber;
                int endMonthOfList = (startMonthOfList + maxMonthPerRow < 12)? startMonthOfList + maxMonthPerRow - 1 : 12;
                String monthNotation = "\t";
                for(int i = startMonthOfList; i <= endMonthOfList; i++)
                {
                    DateTimeFormatter shortMonth = DateTimeFormatter.ofPattern("MMM");
                    String monthName = "" + Month.of(i);
                    monthNotation += monthName + defaultRow.substring(monthName.length());
                }
                rows.add(monthNotation);
                rows.add("mon\t");
                rows.add("tue\t");
                rows.add("wed\t");
                rows.add("thu\t");
                rows.add("fri\t");
                rows.add("sat\t");
                rows.add("sun\t");
                rows.add("DoW\t");
            }
            List<String> formattedMonth = formatMonth(year, monthNumber);
            for(int i = 0; i < formattedMonth.size(); i++)
            {
                int selectIndex = rowIndex * 9 + 1 + i;
                rows.set(selectIndex, rows.get(selectIndex) + formattedMonth.get(i));
            }
        }

        String output = "";

        for(int i = 0; i < rows.size(); i++)
        {
            output += rows.get(i) + "\n";
        }
        return output;
    }

    /**
     * Get a month formatted to be used in a calendar.
     * @param year year of month, to get the right days of the week used.
     * @param month month of the year.
     * @return partially formatted to a usable calendar entry.
     */
    private static List<String> formatMonth(int year, int month)
    {
        return formatMonth(year, month, false);
    }

    /**
     * Get a month formatted to be used in a calendar.
     * @param year year of month, to get the right days of the week used.
     * @param month month of the year.
     * @param isForeign NotYetImplemented
     * @return partially formatted to a usable calendar entry.
     */
    private static List<String> formatMonth(int year, int month, boolean isForeign)
    {
        //x,y = 5 weeks,7 days.
        String[][] monthColumn = new String[weekNos][7];
        for(int x = 0; x < weekNos; x++)
        {
            for(int y = 0; y < 7; y++)
            {
                monthColumn[x][y] = "";
            }
        }
        YearMonth ym = YearMonth.of(year, month);
        LocalDate endOfMonth =  ym.atEndOfMonth();
        int day = 1;
        int weekNo = 0;
        List<Integer> weekNumbers = new ArrayList<>();
        do{
            LocalDate date = LocalDate.of(year, month, day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String s = String.format("%02d", day);
            monthColumn[weekNo][dayOfWeek.ordinal()] = s;

            //set first weeknumber
            if(weekNumbers.size() == 0 && dayOfWeek != MONDAY){
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
                weekNumbers.add(weekNumber);
            }

            switch (dayOfWeek)
            {
                case MONDAY:
                    monthColumn[weekNo][dayOfWeek.ordinal()] = s;
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
                    weekNumbers.add(weekNumber);
                    break;
                case TUESDAY:
                    monthColumn[weekNo][1] = s;
                    break;
                case WEDNESDAY:
                    monthColumn[weekNo][2] = s;
                    break;
                case THURSDAY:
                    monthColumn[weekNo][3] = s;
                    break;
                case FRIDAY:
                    monthColumn[weekNo][4] = s;
                    break;
                case SATURDAY:
                    monthColumn[weekNo][5] = s;
                    break;
                case SUNDAY:
                    monthColumn[weekNo][6] = s;
                    weekNo++;
            }
            day++;
        } while (day < endOfMonth.getDayOfMonth());

        List<String> dayOfWeekArray = new ArrayList<>();
        for(int y = 0; y < 7; y++)
        {
            String sRow = "";
            for(int x = 0; x < weekNos; x++)
            {
                String unpad = monthColumn[x][y];
                String pad = unpad + "    ".substring(unpad.length());
                sRow += pad;
            }
            dayOfWeekArray.add(sRow);
        }

        StringBuilder sbWeekNumbers = new StringBuilder();

        for(Integer i : weekNumbers)
        {
            sbWeekNumbers.append(String.format("#%02d ", i));
        }

        for(int i = 0; i < weekNos - weekNumbers.size(); i++)
        {
            int missingWeekNo = weekNumbers.get(weekNumbers.size() - 1) + i;
            missingWeekNo = missingWeekNo >= 53 ? missingWeekNo - 52:missingWeekNo;
            sbWeekNumbers.append(String.format("    ", missingWeekNo));
        }

        dayOfWeekArray.add(sbWeekNumbers.toString());

        return dayOfWeekArray;
    }
}
