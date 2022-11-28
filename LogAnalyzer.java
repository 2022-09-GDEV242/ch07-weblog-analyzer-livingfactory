/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    
    private int[] dayCounts;
    private int[] monthCounts;
    
    /**
     * Check the current day's data.
     */    
    public void checkThisDay()
    {
        hourCounts = new int[24];
        reader.reset();
        while(reader.hasNext()){
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    
    /**
     * Check the current month's data.
     */
    public void checkThisMonth()
    {
        dayCounts = new int[29];
        reader.reset();
        while(reader.hasNext()){
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
    }
    
    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String name)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(name);
    }
    
    /**
     * Find number of accesses for the log fle.
     * @returns total number of accesses.
     */
    public int numberOfAccesses(){
        int thisTotal = 0;
        for(int t=0; t<hourCounts.length; t++){
            thisTotal += hourCounts[t];
        }
        return thisTotal;
    }
    
    /**
     * Find the number of accesses in the month.
     */
    public void totalAccessesPerMonth()
    {
        checkThisMonth();
        System.out.println("Month Access Count:");
        int month = 1;
        while(month < monthCounts.length){
            System.out.println((month) + ": " + monthCounts[month]);
            ++month;
        }
    }
    
    /**
     * Find and return the quietest month.
     * @returns the quietest month.
     */
    public int quietestMonth()
    {
        checkThisMonth();
        int month = 1;
        int quietest = monthCounts[1];
        for(int k=1; k<monthCounts.length; k++){
            if(monthCounts[k] < quietest){
                quietest = monthCounts[k];
                month = k;
            }
        }
        return month;
    }
    
    /**
     * Find and return the busiest month.
     * @returns the busiest month.
     */
    public int busiestMonth()
    {
        analyzeHourlyData();
        int month = 1;
        int busiest = monthCounts[1];
        for(int k=1; k<monthCounts.length; k++){
            if(monthCounts[k] > busiest){
                busiest = monthCounts[k];
                month = k;
            }
        }
        return month;
    }

    /**
     * Find the busiest hour
     * @returns busiest hour calculated
     */
    public int busiestHour(){
        int max = 0;
        int busiestHour = 0;
        for(int t=0; t<hourCounts.length; t++){
            if(hourCounts[t]>max){
                busiestHour = t;
                max = hourCounts[t];
            }
        }
        return busiestHour;
    }
    
    /**
     * Find the quietest hour
     * @returns quietest hour calculated
     */
    public int quietestHour(){
        int min = numberOfAccesses();
        int quietest = 0;
        for(int t=0; t<hourCounts.length; t++){
            if (hourCounts[t]<min){
                quietest = t;
                min = hourCounts[t];
            }
        }
        return quietest;
    }
    
    /**
     * Find busiest two hours
     * @returns one of two busy hours
     */
    public int busiestTwoHour(){
        int max = 0;
        int busyFirst = 0;
        for(int t=0; t<hourCounts.length/2; t++){
            int hourPair = hourCounts[t*2]+hourCounts[t*2+1];
            if (hourPair > max){
                busyFirst = t;
            }
        }
        return busyFirst;
    }
    
    /**
     * Return the busiest day calculated.
     */
    public int busiestDay()
    {
        checkThisDay();
        int day = 1;
        int busiest = dayCounts[1];
        for(int k=1; k<hourCounts.length; k++){
            if(dayCounts[k] > busiest){
                busiest = dayCounts[k];
                day = k;
            }
        }
        return day;
    }
    
    /**
     * Return the quietest day calculated.
     */
    public int quietestDay()
    {
        analyzeHourlyData();
        int day = 1;
        int quietest = dayCounts[1];
        for(int k=1; k<hourCounts.length; k++){
            if(dayCounts[k] < quietest){
                quietest = dayCounts[k];
                day = k;
            }
        }
        return day;
    }    
    
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
