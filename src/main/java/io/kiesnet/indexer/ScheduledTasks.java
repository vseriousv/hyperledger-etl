package io.kiesnet.indexer;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	//    @Scheduled(cron="* * * * *)
//	@Scheduled(fixedRate = 500000)
	@Autowired
	public void reportCurrentTime() throws InvalidArgumentException, TransactionException, ProposalException, ParseException, IOException {
		log.info("The time is now {}", dateFormat.format(new Date()));
		Scan.worker();
	}
}
