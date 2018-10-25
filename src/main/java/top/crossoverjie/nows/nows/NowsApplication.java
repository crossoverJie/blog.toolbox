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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class NowsApplication implements CommandLineRunner{


	private static Logger logger = LoggerFactory.getLogger(NowsApplication.class);


	@Autowired
	private FilterProcessManager filterProcessManager ;

	@Autowired
	private ScannerFile scannerFile ;


	@Autowired
	private TotalWords totalWords;


	public static void main(String[] args) throws IOException {
		SpringApplication.run(NowsApplication.class, args);

	}

	@Override
	public void run(String... strings) throws Exception {

		long start = System.currentTimeMillis();

		Map<String, String> allFile = scannerFile.getAllFile(strings[0]);
		logger.info("allFile size=[{}]",allFile.size());
		for (Map.Entry<String, String> entry : allFile.entrySet()) {
			logger.info("key=[{}]  value=[{}]",entry.getKey(),entry.getValue());


			Stream<String> stringStream = Files.lines(Paths.get(entry.getValue()), StandardCharsets.UTF_8);

			List<String> collect = stringStream.collect(Collectors.toList());
			for (String msg : collect) {
				filterProcessManager.process(msg);
			}

		}

		long total = totalWords.total();


		long end = System.currentTimeMillis();
		logger.info("total sum=[{}],[{}] ms",total,end-start);

	}
}
