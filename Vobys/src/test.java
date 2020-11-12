import java.sql.SQLException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class test {
	public static void main(String[] args) throws SQLException {
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
		ZonedDateTime nextRun = now.withHour(2).withMinute(30).withSecond(0);
		if(now.compareTo(nextRun) > 0)
		    nextRun = nextRun.plusDays(1);

		Duration duration = Duration.between(now, nextRun);
		long initalDelay = duration.getSeconds();

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);            
		scheduler.scheduleAtFixedRate(Mysql.reset(),
		    initalDelay,
		    TimeUnit.DAYS.toSeconds(1),
		    TimeUnit.SECONDS);
	}
}
