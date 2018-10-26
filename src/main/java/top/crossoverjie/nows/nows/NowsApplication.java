package top.crossoverjie.nows.nows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.crossoverjie.nows.nows.filter.FilterProcessManager;
import top.crossoverjie.nows.nows.impl.TotalWords;
import top.crossoverjie.nows.nows.scan.ScannerFile;
import top.crossoverjie.nows.nows.thread.ScanNumTask;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class NowsApplication implements CommandLineRunner{


	private static Logger logger = LoggerFactory.getLogger(NowsApplication.class);


	@Autowired
	private FilterProcessManager filterProcessManager ;

	@Autowired
	private ScannerFile scannerFile ;

	@Autowired
	private TotalWords totalWords;

	@Autowired
	private ExecutorService executorService ;


	public static void main(String[] args) throws IOException {
		SpringApplication.run(NowsApplication.class, args);

	}

	@Override
	public void run(String... strings) throws Exception {

		long start = System.currentTimeMillis();

		List<String> allFile = scannerFile.getAllFile(strings[0]);
		logger.info("allFile size=[{}]",allFile.size());
		for (String msg : allFile) {
			executorService.execute(new ScanNumTask(msg,filterProcessManager));


			/*Stream<String> stringStream = null;
			try {
				stringStream = Files.lines(Paths.get(msg), StandardCharsets.UTF_8);
			} catch (IOException e) {
				logger.error("IOException", e);
			}

			TimeUnit.MILLISECONDS.sleep(100);
			List<String> collect = stringStream.collect(Collectors.toList());
			for (String s : collect) {
				filterProcessManager.process(s);
			}*/
		}

		executorService.shutdown();
		while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
			logger.info("worker running");
		}
		long total = totalWords.total();
		long end = System.currentTimeMillis();
		logger.info("total sum=[{}],[{}] ms",total,end-start);

	}
}
